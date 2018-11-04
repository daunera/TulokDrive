package hu.tobias.controllers.patrol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Leader;
import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Status;
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

	private Scout newScout = new Scout();
	private Scout selectedScout;
	private List<Scout> scouts = new ArrayList<Scout>();

	public PatrolInfoController() {
	}

	@PostConstruct
	public void init() {
		newScout = new Scout();
		newScout.setPerson(new Person());
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

	public List<Scout> getScouts() {
		return scouts;
	}

	public void setScouts(List<Scout> scouts) {
		this.scouts = scouts;
	}

	public void saveEdit() {
		patrolService.update(patrolController.getPatrol());
		patrolController.getUserController().reloadPatrol();
		patrolController.getUserController().changeEdit();
	}

	public boolean setForNewScoutModal() {
		scouts = scoutService.findAllWithoutPatrol();
		for (Leader l : patrolController.getPatrol().getLeaders()) {
			scouts.remove(l.getScout());
		}
		newScout = new Scout();
		newScout.setPerson(new Person());
		newScout.getPerson().setGender(patrolController.getPatrol().getGender());

		if (!scouts.isEmpty()) {
			selectedScout = scouts.get(0);
		}

		return true;
	}

	public boolean setForDeleteScoutModal(Scout s) {
		selectedScout = s;
		return true;
	}

	public void deleteScout(Scout s) {
		s.setPatrol(null);
		scoutService.update(s);
		patrolController.loadData();
	}

	public void saveScout(Scout s) {
		s.setStatus(Status.ACTIVE);
		if (s.getId() == null) {
			personService.create(s.getPerson());
			s.setPatrol(patrolController.getPatrol());
			scoutService.create(s);
		} else {
			s.setPatrol(patrolController.getPatrol());
			scoutService.update(s);
		}
		patrolController.loadData();
	}

}
