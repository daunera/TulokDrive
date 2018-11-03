package hu.tobias.controllers.patrol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.services.comparator.ScoutNameComparator;
import hu.tobias.services.dao.PatrolDao;
import hu.tobias.services.dao.PersonDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "patrolInfo")
@ViewScoped
public class PatrolInfoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PatrolDao patrolService;
	@EJB
	private PersonDao personService;
	@EJB
	private ScoutDao scoutService;

	@Inject
	private PatrolController patrolController;

	private Scout newScout;
	private Scout selectedScout;
	private List<Scout> allScouts;

	public PatrolInfoController() {
	}

	@PostConstruct
	public void init() {
		allScouts = new ArrayList<Scout>(scoutService.findAllWithoutPatrol());
		Collections.sort(allScouts, new ScoutNameComparator());
		newScout = createNewScout();
	}

	public PatrolController getPatrolController() {
		return patrolController;
	}

	public void setPatrolController(PatrolController patrolController) {
		this.patrolController = patrolController;
	}

	public Scout getNewScout() {
		return newScout;
	}

	public void setNewScout(Scout newScout) {
		this.newScout = newScout;
	}

	public Scout getSelectedScout() {
		return selectedScout;
	}

	public void setSelectedScout(Scout selectedScout) {
		this.selectedScout = selectedScout;
	}

	public List<Scout> getAllScouts() {
		return allScouts;
	}

	public void setAllScouts(List<Scout> allScouts) {
		this.allScouts = allScouts;
	}

	public void saveEdit() {
		patrolService.update(patrolController.getPatrol());
		patrolController.getUserController().reloadPatrol();
		patrolController.getUserController().changeEdit();
	}

	public void deleteScout(Scout s) {
		patrolController.getPatrol().getScouts().remove(s);
		patrolService.update(patrolController.getPatrol());

		allScouts.add(s);
		Collections.sort(allScouts, new ScoutNameComparator());
	}

	private Scout createNewScout() {
		Scout tmpScout = new Scout();
		tmpScout.setPerson(new Person());
		tmpScout.getPerson().setGender(patrolController.getPatrol().getGender());

		return tmpScout;
	}

	public void saveNewScout() {
		personService.create(newScout.getPerson());
		scoutService.create(newScout);

		saveScoutToPatrol(newScout);
		newScout = createNewScout();
	}

	public void saveSelectedScout(Scout s) {
		saveScoutToPatrol(s);
		allScouts.remove(s);
	}

	private void saveScoutToPatrol(Scout s) {
		patrolController.getPatrol().getScouts().add(s);
		patrolService.update(patrolController.getPatrol());
	}

}
