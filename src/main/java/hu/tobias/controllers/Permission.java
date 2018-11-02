package hu.tobias.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Leader;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Scout;
import hu.tobias.entities.Troop;

@Named(value = "permission")
@SessionScoped
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UserController userController;

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public boolean checkScoutPermission(Integer scoutid) {
		userController.reloadPatrol();
		if (userController.getLeader().isAGod())
			return true;
		if (userController.getLeader().getScout().getId().equals(scoutid))
			return true;

		for (Patrol p : userController.getPatrols()) {
			for (Scout s : p.getScouts()) {
				if (s.getId().equals(scoutid))
					return true;
			}
			for (Leader l : p.getLeaders()) {
				if (l.getScout().getId().equals(scoutid))
					return true;
			}
		}

		for (Troop t : userController.getTroops()) {
			for (Patrol p : t.getPatrols()) {
				for (Scout s : p.getScouts()) {
					if (s.getId().equals(scoutid))
						return true;
				}
				for (Leader l : p.getLeaders()) {
					if (l.getScout().getId().equals(scoutid))
						return true;
				}
			}
			for (Leader l : t.getLeaders()) {
				if (l.getScout().getId().equals(scoutid))
					return true;
			}
		}

		userController.redirectRelative("error/permission");
		return false;
	}

	public boolean checkPatrolPermission(Integer patrolid) {
		userController.reloadPatrol();
		if (userController.getLeader().isAGod())
			return true;

		for (Patrol p : userController.getPatrols())
			if (p.getId().equals(patrolid))
				return true;

		for (Troop t : userController.getTroops()) {
			for (Patrol p : t.getPatrols())
				if (p.getId().equals(patrolid))
					return true;
		}

		for (Patrol p : userController.getLeader().getScout().getPatrols())
			if (p.getId().equals(patrolid))
				return true;

		userController.redirectRelative("error/permission");
		return false;
	}

	public boolean checkTroopPermission(Integer troopid) {
		userController.reloadPatrol();
		if (userController.getLeader().isAGod())
			return true;

		for (Troop t : userController.getTroops())
			if (t.getId().equals(troopid))
				return true;

		for (Patrol p : userController.getLeader().getScout().getPatrols())
			for (Troop t : p.getTroops())
				if (t.getId().equals(troopid))
					return true;

		userController.redirectRelative("error/permission");
		return false;
	}

	public boolean checkTeamPermission() {
		userController.reloadUser();
		if (userController.getLeader().isAGod())
			return true;

		userController.redirectRelative("error/permission");
		return false;
	}

	public boolean checkUniformPermission() {
		userController.reloadUser();
		if (userController.getLeader().isAUniformer())
			return true;
		userController.redirectRelative("error/permission");
		return false;
	}

}
