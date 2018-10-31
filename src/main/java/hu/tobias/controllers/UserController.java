package hu.tobias.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import hu.tobias.entities.Leader;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Troop;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.comparator.PatrolNameComparator;
import hu.tobias.services.dao.LeaderDao;

@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private LeaderDao leaderService;

	private Leader leader = new Leader();
	private List<Patrol> patrols;
	private List<Troop> troops;
	private boolean edit;

	public UserController() {
	}

	@PostConstruct
	public void init() {
		leader = getLeaderFromUsername();
		patrols = new ArrayList<Patrol>(leader.getPatrols());
		troops = new ArrayList<Troop>(leader.getTroops());
		Collections.sort(patrols, new PatrolNameComparator());
	}

	public Leader getLeader() {
		return leader;
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
	}

	public List<Patrol> getPatrols() {
		return patrols;
	}

	public void setPatrols(List<Patrol> patrols) {
		this.patrols = patrols;
	}

	public List<Troop> getTroops() {
		return troops;
	}

	public void setTroops(List<Troop> troops) {
		this.troops = troops;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public void changeEdit() {
		edit = !edit;
	}

	public Leader getLeaderFromUsername() {
		Leader user = (Leader) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		if (user != null)
			return user;
		else
			return null;
	}

	public void reloadUser() {
		try {
			leader = leaderService.findById(leader.getId());
		} catch (NotFoundEntityException e) {
			redirectDbError();
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", leader);
	}

	public void reloadPatrol() {
		reloadUser();
		patrols = new ArrayList<Patrol>(leader.getPatrols());
		Collections.sort(patrols, new PatrolNameComparator());
	}

	public String getRootUrl() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/";
	}

	public void redirectRelative(String path) {
		redirectFull(getRootUrl() + path);
	}

	public void redirectFull(String path) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void redirectToUser() {
		redirectFull(leader.getScout().getScoutUrl());
	}

	public void redirectDbError() {
		redirectRelative("error/db");
	}
}
