package hu.tobias.controllers.team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Leader;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Troop;
import hu.tobias.services.comparator.TroopNameComparator;
import hu.tobias.services.dao.LeaderDao;
import hu.tobias.services.dao.PatrolDao;
import hu.tobias.services.dao.TroopDao;
import hu.tobias.services.utils.Utils;

@Named(value = "teamTroop")
@ViewScoped
public class TeamTroopController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TroopDao troopService;
	@EJB
	private LeaderDao leaderService;
	@EJB
	private PatrolDao patrolService;

	@Inject
	private TeamController teamController;

	@Inject
	private TeamLeaderController teamLeader;

	private List<Troop> troops = new ArrayList<Troop>();
	private List<Leader> choosableLeaders = new ArrayList<Leader>();
	private Troop newTroop = new Troop();

	private Troop editedTroop = new Troop();
	private Leader leaderToAdd = new Leader();
	private Leader leaderToDelete = new Leader();

	private List<Patrol> choosablePatrols = new ArrayList<Patrol>();
	private Patrol patrolToAdd = new Patrol();
	private Patrol patrolToDelete = new Patrol();

	public TeamTroopController() {
	}

	@PostConstruct
	public void init() {
		loadData();
	}

	public void loadData() {
		troops = troopService.findAll();
		Collections.sort(troops, new TroopNameComparator());
		newTroop = new Troop();
	}

	public TeamController getTeamController() {
		return teamController;
	}

	public void setTeamController(TeamController teamController) {
		this.teamController = teamController;
	}

	public TeamLeaderController getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(TeamLeaderController teamLeader) {
		this.teamLeader = teamLeader;
	}

	public List<Troop> getTroops() {
		return troops;
	}

	public void setTroops(List<Troop> troops) {
		this.troops = troops;
	}

	public List<Leader> getChoosableLeaders() {
		return choosableLeaders;
	}

	public void setChoosableLeaders(List<Leader> choosableLeaders) {
		this.choosableLeaders = choosableLeaders;
	}

	public Troop getNewTroop() {
		return newTroop;
	}

	public void setNewTroop(Troop newTroop) {
		this.newTroop = newTroop;
	}

	public Troop getEditedTroop() {
		return editedTroop;
	}

	public void setEditedTroop(Troop editedTroop) {
		this.editedTroop = editedTroop;
	}

	public Leader getLeaderToAdd() {
		return leaderToAdd;
	}

	public void setLeaderToAdd(Leader leaderToAdd) {
		this.leaderToAdd = leaderToAdd;
	}

	public Leader getLeaderToDelete() {
		return leaderToDelete;
	}

	public void setLeaderToDelete(Leader leaderToDelete) {
		this.leaderToDelete = leaderToDelete;
	}

	public List<Patrol> getChoosablePatrols() {
		return choosablePatrols;
	}

	public void setChoosablePatrols(List<Patrol> choosablePatrols) {
		this.choosablePatrols = choosablePatrols;
	}

	public Patrol getPatrolToAdd() {
		return patrolToAdd;
	}

	public void setPatrolToAdd(Patrol patrolToAdd) {
		this.patrolToAdd = patrolToAdd;
	}

	public Patrol getPatrolToDelete() {
		return patrolToDelete;
	}

	public void setPatrolToDelete(Patrol patrolToDelete) {
		this.patrolToDelete = patrolToDelete;
	}

	public void saveEdit() {
		if (!troops.isEmpty()) {
			for (Troop t : troops) {
				troopService.update(t);
			}
		}
		teamController.getUserController().changeEdit();
		teamController.getUserController().reloadTroop();
		loadData();
	}

	public boolean setForNewTroopModal() {
		newTroop = new Troop();
		return true;
	}

	public void saveTroop(Troop t) {
		if (t.getId() == null)
			troopService.create(t);
		else
			troopService.update(t);
		teamController.getUserController().changeEdit();
		loadData();
	}

	public void deleteTroop(Troop t) {
		boolean refresh = false;
		if (!t.getPatrols().isEmpty()) {
			for (Patrol p : t.getPatrols()) {
				p.setTroop(null);
				patrolService.update(p);
			}
		}
		if (!t.getLeaders().isEmpty()) {
			for (Leader l : t.getLeaders())
				if (teamController.getUserController().getLeader().equals(l)) {
					refresh = true;
					break;
				}
			t.getLeaders().clear();
			troopService.update(t);
		}
		troopService.delete(t);
		if (refresh)
			teamController.getUserController().reloadTroop();
		loadData();
	}

	public boolean setForSetLeaderModal(Troop t) {
		editedTroop = t;
		choosableLeaders = leaderService.findAll();
		for (Leader l : t.getLeaders()) {
			choosableLeaders.remove(l);
		}

		if (!choosableLeaders.isEmpty())
			leaderToAdd = choosableLeaders.get(0);
		if (!editedTroop.getLeaders().isEmpty())
			leaderToDelete = Utils.orderLeaderSet(editedTroop.getLeaders()).get(0);

		return true;
	}

	public void addLeader(Troop t, Leader l) {
		t.getLeaders().add(l);
		troopService.update(t);

		if (teamController.getUserController().getLeader().equals(l)) {
			teamController.getUserController().reloadTroop();
		}
		loadData();
	}

	public void deleteLeader(Troop t, Leader l) {
		t.getLeaders().remove(l);
		troopService.update(t);

		if (teamController.getUserController().getLeader().equals(l)) {
			teamController.getUserController().reloadTroop();
		}
		loadData();
	}

	public boolean setForTroopDeleteModal(Troop t) {
		editedTroop = t;
		return true;
	}

	public boolean setForSetPatrolModal(Troop t) {
		editedTroop = t;
		choosablePatrols = patrolService.findAllWithoutTroop();

		if (!choosablePatrols.isEmpty())
			patrolToAdd = choosablePatrols.get(0);
		if (!editedTroop.getPatrols().isEmpty())
			patrolToDelete = Utils.orderPatrolSet(editedTroop.getPatrols()).get(0);

		return true;
	}

	public void addPatrol(Troop t, Patrol p) {
		p.setTroop(t);
		patrolService.update(p);

		loadData();
	}

	public void deletePatrol(Troop t, Patrol p) {
		p.setTroop(null);
		patrolService.update(p);

		loadData();
	}
}
