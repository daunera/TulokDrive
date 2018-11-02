package hu.tobias.controllers.team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Leader;
import hu.tobias.entities.Patrol;
import hu.tobias.services.comparator.LeaderNameComparator;
import hu.tobias.services.comparator.PatrolNameComparator;
import hu.tobias.services.dao.LeaderDao;
import hu.tobias.services.dao.PatrolDao;

@Named(value = "teamPatrol")
@ViewScoped
public class TeamPatrolController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PatrolDao patrolService;
	@EJB
	private LeaderDao leaderService;

	@Inject
	private TeamController teamController;

	@Inject
	private TeamLeaderController teamLeader;

	private List<Patrol> patrols = new ArrayList<Patrol>();
	private List<Leader> choosableLeaders = new ArrayList<Leader>();
	private Patrol newPatrol = new Patrol();

	private Patrol editedPatrol = new Patrol();
	private Leader leaderToAdd = new Leader();
	private Leader leaderToDelete = new Leader();

	public TeamPatrolController() {
	}

	@PostConstruct
	public void init() {
		loadData();
	}

	public void loadData() {
		patrols = patrolService.findAll();
		Collections.sort(patrols, new PatrolNameComparator());
		newPatrol = new Patrol();
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

	public List<Patrol> getPatrols() {
		return patrols;
	}

	public void setPatrols(List<Patrol> patrols) {
		this.patrols = patrols;
	}

	public List<Leader> getChoosableLeaders() {
		return choosableLeaders;
	}

	public void setChoosableLeaders(List<Leader> choosableLeaders) {
		this.choosableLeaders = choosableLeaders;
	}

	public Patrol getNewPatrol() {
		return newPatrol;
	}

	public void setNewPatrol(Patrol newPatrol) {
		this.newPatrol = newPatrol;
	}

	public Patrol getEditedPatrol() {
		return editedPatrol;
	}

	public void setEditedPatrol(Patrol editedPatrol) {
		this.editedPatrol = editedPatrol;
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

	public void saveEdit() {
		if (!patrols.isEmpty()) {
			for (Patrol p : patrols) {
				patrolService.update(p);
			}
		}
		teamController.getUserController().changeEdit();
		teamController.getUserController().reloadPatrol();
		loadData();
	}

	public boolean setForNewPatrolModal() {
		newPatrol = new Patrol();
		return true;
	}

	public void savePatrol(Patrol p) {
		if (p.getId() == null)
			patrolService.create(p);
		else
			patrolService.update(p);
		teamController.getUserController().changeEdit();
		loadData();
	}

	public void deletePatrol(Patrol p) {
		boolean modded = false;
		boolean refresh = false;
		if (!p.getScouts().isEmpty()) {
			p.getScouts().clear();
			modded = true;
		}
		if (!p.getLeaders().isEmpty()) {
			if (p.getLeaders().contains(teamController.getUserController().getLeader())) {
				refresh = true;
			}
			p.getLeaders().clear();
			modded = true;
		}
		if (modded)
			patrolService.update(p);
		patrolService.delete(p);
		if (refresh)
			teamController.getUserController().reloadPatrol();
		loadData();
	}

	// TODO: lehetne törölni, mert csak egy private helyen van használva
	public List<Leader> orderSet(Set<Leader> set) {
		List<Leader> result = new ArrayList<Leader>(set);
		Collections.sort(result, new LeaderNameComparator());
		return result;
	}

	public boolean setForSetLeaderModal(Patrol p) {
		editedPatrol = p;
		choosableLeaders = leaderService.findAll();
		for (Leader l : p.getLeaders()) {
			choosableLeaders.remove(l);
		}
		Collections.sort(choosableLeaders, new LeaderNameComparator());

		if (!choosableLeaders.isEmpty())
			leaderToAdd = choosableLeaders.get(0);
		if (!editedPatrol.getLeaders().isEmpty())
			leaderToDelete = orderSet(editedPatrol.getLeaders()).get(0);

		return true;
	}

	public void addLeader(Patrol p, Leader l) {
		p.getLeaders().add(l);
		patrolService.update(p);

		if (p.getLeaders().contains(teamController.getUserController().getLeader())) {
			teamController.getUserController().reloadPatrol();
		}
		loadData();
	}

	public void deleteLeader(Patrol p, Leader l) {
		p.getLeaders().remove(l);
		patrolService.update(p);

		if (teamController.getUserController().getLeader().equals(l)
				|| p.getLeaders().contains(teamController.getUserController().getLeader())) {
			teamController.getUserController().reloadPatrol();
		}
		loadData();
	}
	
	public boolean setForPatrolDeleteModal(Patrol p) {
		editedPatrol = p;
		return true;
	}

}
