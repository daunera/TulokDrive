package hu.tobias.controllers.troop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Patrol;
import hu.tobias.services.dao.PatrolDao;
import hu.tobias.services.dao.TroopDao;

@Named(value = "troopInfo")
@ViewScoped
public class TroopInfoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TroopDao troopService;
	@EJB
	private PatrolDao patrolService;

	@Inject
	private TroopController troopController;

	private Patrol selectedPatrol;
	private List<Patrol> patrols = new ArrayList<Patrol>();

	public TroopInfoController() {
	}

	@PostConstruct
	public void init() {
	}

	public TroopController getTroopController() {
		return troopController;
	}

	public void setTroopController(TroopController troopController) {
		this.troopController = troopController;
	}

	public Patrol getSelectedPatrol() {
		return selectedPatrol;
	}

	public void setSelectedPatrol(Patrol selectedPatrol) {
		this.selectedPatrol = selectedPatrol;
	}

	public List<Patrol> getPatrols() {
		return patrols;
	}

	public void setPatrols(List<Patrol> patrols) {
		this.patrols = patrols;
	}

	public void saveEdit() {
		troopService.update(troopController.getTroop());
		troopController.getUserController().reloadTroop();
		troopController.getUserController().changeEdit();
	}

	public boolean setForNewPatrolModal() {
		patrols = patrolService.findAllWithoutTroop();

		if (!patrols.isEmpty()) {
			selectedPatrol = patrols.get(0);
		}

		return true;
	}

	public boolean setForDeletePatrolModal(Patrol p) {
		selectedPatrol = p;
		return true;
	}

	public void deletePatrol(Patrol p) {
		p.setTroop(null);
		patrolService.update(p);
		troopController.loadData();
	}

	public void savePatrol(Patrol p) {
		p.setTroop(troopController.getTroop());
		patrolService.update(p);
		troopController.loadData();
	}

}
