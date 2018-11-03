package hu.tobias.controllers.team;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import hu.tobias.entities.Troop;
import hu.tobias.entities.Userroles;
import hu.tobias.services.comparator.LeaderNameComparator;
import hu.tobias.services.dao.LeaderDao;
import hu.tobias.services.dao.PatrolDao;
import hu.tobias.services.dao.PersonDao;
import hu.tobias.services.dao.ScoutDao;
import hu.tobias.services.dao.TroopDao;
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
	@EJB
	private TroopDao troopService;
	@EJB
	private PersonDao personService;

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

	private List<Troop> choosableTroop = new ArrayList<Troop>();
	private Troop troopToAdd = new Troop();
	private Troop troopToDelete = new Troop();

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

	public List<Troop> getChoosableTroop() {
		return choosableTroop;
	}

	public void setChoosableTroop(List<Troop> choosableTroop) {
		this.choosableTroop = choosableTroop;
	}

	public Troop getTroopToAdd() {
		return troopToAdd;
	}

	public void setTroopToAdd(Troop troopToAdd) {
		this.troopToAdd = troopToAdd;
	}

	public Troop getTroopToDelete() {
		return troopToDelete;
	}

	public void setTroopToDelete(Troop troopToDelete) {
		this.troopToDelete = troopToDelete;
	}

	public String getGeneratedPassword() {
		return generatedPassword;
	}

	public void setGeneratedPassword(String generatedPassword) {
		this.generatedPassword = generatedPassword;
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
		scouts = scoutService.findAllWithoutLeader();

		newLeader = new Leader();
		if (!scouts.isEmpty()) {
			newLeader.setScout(scouts.get(0));
			if (newLeader.getScout().getPerson().getEmail() != null) {
				newLeader.setUsername(newLeader.getScout().getPerson().getEmail().split("@")[0]);
			}
			generatedPassword = Utils.generatePassword();
			newLeader.setPassword(generatedPassword);
		}
		return true;
	}

	public void updateNewLeader() {
		if (newLeader.getScout().getPerson().getEmail() != null) {
			newLeader.setUsername(newLeader.getScout().getPerson().getEmail().split("@")[0]);
		} else {
			newLeader.setUsername(null);
			newLeader.getScout().getPerson().setEmail(null);
		}
	}

	public void saveLeader(Leader l, String pw) {
		if (l.getId() == null) {
			leaderService.create(l);
			roleService.create(new Userroles(l));
		} else {
			leaderService.update(l);
			roleService.update(l.getRole());
		}
		if (l.getScout().getPerson().getEmail() != null) {
			personService.update(l.getScout().getPerson());
		}
		emailBean.sendLeaderCreatedFromAdmin(l.getEmail(), l.getScout().getPerson().getPersonalName(), pw,
				l.getUsername());
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
		teamController.getUserController().changeEdit();
	}

	public void deleteLeader(Leader l) {
		if (!l.getPatrols().isEmpty()) {
			for (Patrol p : l.getPatrols()) {
				if (p.getLeaders().contains(l)) {
					p.getLeaders().remove(l);
					patrolService.update(p);
				}
			}
		}
		if (!l.getTroops().isEmpty()) {
			for (Troop t : l.getTroops()) {
				if (t.getLeaders().contains(l)) {
					t.getLeaders().remove(l);
					troopService.update(t);
				}
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
		teamController.getUserController().changeEdit();
	}

	public boolean setForSetPatrolModal(Leader l) {
		editedLeader = l;
		choosablePatrol = patrolService.findAll();
		for (Patrol p : l.getPatrols()) {
			choosablePatrol.remove(p);
		}

		if (!choosablePatrol.isEmpty())
			patrolToAdd = choosablePatrol.get(0);
		if (!editedLeader.getPatrols().isEmpty())
			patrolToDelete = Utils.orderPatrolSet(editedLeader.getPatrols()).get(0);

		return true;
	}

	public void addPatrol(Leader l, Patrol p) {
		p.getLeaders().add(l);
		patrolService.update(p);

		if (teamController.getUserController().getLeader().equals(l)) {
			teamController.getUserController().reloadPatrol();
		}
		loadData();
		teamController.getUserController().changeEdit();
	}

	public void deletePatrol(Leader l, Patrol p) {
		p.getLeaders().remove(l);
		patrolService.update(p);

		if (teamController.getUserController().getLeader().equals(l)) {
			teamController.getUserController().reloadPatrol();
		}
		loadData();
		teamController.getUserController().changeEdit();
	}

	public boolean setForSetTroopModal(Leader l) {
		editedLeader = l;
		choosableTroop = troopService.findAll();
		for (Troop t : l.getTroops()) {
			choosableTroop.remove(t);
		}

		if (!choosableTroop.isEmpty())
			troopToAdd = choosableTroop.get(0);
		if (!editedLeader.getTroops().isEmpty())
			troopToDelete = Utils.orderTroopSet(editedLeader.getTroops()).get(0);

		return true;
	}

	public void addTroop(Leader l, Troop t) {
		t.getLeaders().add(l);
		troopService.update(t);

		if (teamController.getUserController().getLeader().equals(l)) {
			teamController.getUserController().reloadTroop();
		}
		loadData();
		teamController.getUserController().changeEdit();
	}

	public void deleteTroop(Leader l, Troop t) {
		t.getLeaders().remove(l);
		troopService.update(t);

		if (teamController.getUserController().getLeader().equals(l)) {
			teamController.getUserController().reloadTroop();
		}
		loadData();
		teamController.getUserController().changeEdit();
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
