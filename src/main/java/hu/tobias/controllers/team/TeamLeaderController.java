package hu.tobias.controllers.team;

import java.io.IOException;
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

import hu.tobias.controllers.Authentication;
import hu.tobias.controllers.EmailSessionBean;
import hu.tobias.entities.Leader;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Scout;
import hu.tobias.entities.Userroles;
import hu.tobias.services.comparator.LeaderNameComparator;
import hu.tobias.services.comparator.PatrolNameComparator;
import hu.tobias.services.comparator.ScoutNameComparator;
import hu.tobias.services.dao.LeaderDao;
import hu.tobias.services.dao.PatrolDao;
import hu.tobias.services.dao.ScoutDao;
import hu.tobias.services.dao.UserrolesDao;
import hu.tobias.services.utils.Utils;

@Named(value = "teamLeader")
@ViewScoped
public class TeamLeaderController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private LeaderDao leaderService;
	@EJB
	private UserrolesDao roleService;
	@EJB
	private ScoutDao scoutService;
	@EJB
	private PatrolDao patrolService;

	@Inject
	private TeamController teamController;

	@Inject
	private Authentication authentication;

	@EJB
	private EmailSessionBean emailBean;

	private List<Leader> leaders = new ArrayList<Leader>();
	private Leader newLeader = new Leader();
	private List<Scout> scouts = new ArrayList<Scout>();

	private Leader editedLeader = new Leader();
	private List<Patrol> choosablePatrol = new ArrayList<Patrol>();
	private Patrol patrolToAdd = new Patrol();
	private Patrol patrolToDelete = new Patrol();

	private String generatedPassword;

	public TeamLeaderController() {
	}

	@PostConstruct
	public void init() {
		loadData();
	}

	public void loadData() {
		leaders = leaderService.findAll();
		Collections.sort(leaders, new LeaderNameComparator());
	}

	public TeamController getTeamController() {
		return teamController;
	}

	public void setTeamController(TeamController teamController) {
		this.teamController = teamController;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public List<Leader> getLeaders() {
		return leaders;
	}

	public void setLeaders(List<Leader> leaders) {
		this.leaders = leaders;
	}

	public Leader getNewLeader() {
		return newLeader;
	}

	public void setNewLeader(Leader newLeader) {
		this.newLeader = newLeader;
	}

	public List<Scout> getScouts() {
		return scouts;
	}

	public void setScouts(List<Scout> scouts) {
		this.scouts = scouts;
	}

	public Leader getEditedLeader() {
		return editedLeader;
	}

	public void setEditedLeader(Leader editedLeader) {
		this.editedLeader = editedLeader;
	}

	public List<Patrol> getChoosablePatrol() {
		return choosablePatrol;
	}

	public void setChoosablePatrol(List<Patrol> choosablePatrol) {
		this.choosablePatrol = choosablePatrol;
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

	public String getGeneratedPassword() {
		return generatedPassword;
	}

	public void setGeneratedPassword(String generatedPassword) {
		this.generatedPassword = generatedPassword;
	}

	// TODO: lehetne törölni, mert csak egy private helyen van használva
	public List<Patrol> orderSet(Set<Patrol> set) {
		List<Patrol> result = new ArrayList<Patrol>(set);
		Collections.sort(result, new PatrolNameComparator());
		return result;
	}

	public void saveEdit() {
		if (leaders.size() > 0) {
			for (Leader l : leaders) {
				leaderService.update(l);
			}
		}
		teamController.getUserController().changeEdit();
	}

	public boolean setForNewLeaderModal() {
		scouts = scoutService.findAll();
		for (Leader l : leaders) {
			scouts.remove(l.getScout());
		}

		Collections.sort(scouts, new ScoutNameComparator());

		newLeader = new Leader();
		generatedPassword = Utils.generatePassword();
		// TODO generate username to
		// newLeader.setUsername(newLeader.getScout().getPerson().getEmail());
		newLeader.setPassword(generatedPassword);
		return true;
	}

	public void saveLeader(Leader l) {
		if (l.getId() == null) {
			leaderService.create(l);
			roleService.create(new Userroles(l));
		} else {
			leaderService.update(l);
			roleService.update(l.getRole());
		}
		teamController.getUserController().changeEdit();
		teamController.getUserController().redirectRelative("team/leader");
	}

	public boolean setForLeaderModal(Leader l) {
		editedLeader = l;
		return true;
	}

	public void saveExtraPermissions(Leader l) {
		leaderService.update(l);
		loadData();
	}

	public void deleteLeader(Leader l) {
		if (!l.getPatrols().isEmpty()) {
			for (Patrol p : l.getPatrols()) {
				p.getLeaders().remove(l);
				patrolService.update(p);
			}
		}
		roleService.delete(l.getRole());
		leaderService.delete(l);
		if (l.equals(teamController.getUserController().getLeader())) {
			try {
				authentication.logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			loadData();
	}

	public boolean setForSetPatrolModal(Leader l) {
		editedLeader = l;
		choosablePatrol = patrolService.findAll();
		for (Patrol p : l.getPatrols()) {
			choosablePatrol.remove(p);
		}
		Collections.sort(choosablePatrol, new PatrolNameComparator());

		if (!choosablePatrol.isEmpty())
			patrolToAdd = choosablePatrol.get(0);
		if (!editedLeader.getPatrols().isEmpty())
			patrolToDelete = orderSet(editedLeader.getPatrols()).get(0);

		return true;
	}

	public void addPatrol(Leader l, Patrol p) {
		p.getLeaders().add(l);
		patrolService.update(p);

		if (p.getLeaders().contains(teamController.getUserController().getLeader())) {
			teamController.getUserController().reloadPatrolName();
		}
		loadData();
	}

	public void deletePatrol(Leader l, Patrol p) {
		p.getLeaders().remove(l);
		patrolService.update(p);

		if (teamController.getUserController().getLeader().equals(l)
				|| p.getLeaders().contains(teamController.getUserController().getLeader())) {
			teamController.getUserController().reloadPatrolName();
		}
		loadData();
	}

	public boolean setForNewPasswordModal(Leader l) {
		editedLeader = l;
		generatedPassword = Utils.generatePassword();
		return true;
	}

	public void saveNewGeneratedPassword(Leader l, String pw) {
		l.setPassword(pw);
		leaderService.update(l);

		emailBean.sendPwChangedMailFromAdmin(l.getScout().getPerson().getEmail(),
				l.getScout().getPerson().getPersonalName(), pw);
	}

}
