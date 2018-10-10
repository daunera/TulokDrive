package hu.tobias.controllers.patrol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;

import org.apache.commons.lang3.StringUtils;

import hu.tobias.controllers.Permission;
import hu.tobias.controllers.UserController;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.TabName;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.comparator.ScoutNameComparator;
import hu.tobias.services.dao.PatrolDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "patrolController")
@ViewScoped
public class PatrolController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PatrolDao patrolService;
	@EJB
	private ScoutDao scoutService;
	
	@Inject
	private UserController userController;
	
	@Inject
	private Permission permission;

	private Patrol patrol = new Patrol();
	private int patrolid;

	private List<Scout> scoutList;
	private Scout moddedScout = new Scout();

	public PatrolController() {
	}

	@PostConstruct
	public void init() {
	}

	public void loadData() {
		try {
			permission.checkPatrolPermission(patrolid);
			patrol = patrolService.findById(patrolid);
		} catch (NotFoundEntityException e) {
			userController.redirectDbError();
		}

		scoutList = new ArrayList<Scout>(patrol.getScouts());
		Collections.sort(scoutList, new ScoutNameComparator());
	}

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Patrol getPatrol() {
		return patrol;
	}

	public void setPatrol(Patrol patrol) {
		this.patrol = patrol;
	}

	public int getPatrolid() {
		return patrolid;
	}

	public void setPatrolid(int patrolid) {
		this.patrolid = patrolid;
	}

	public List<Scout> getScoutList() {
		return scoutList;
	}

	public void setScoutList(List<Scout> scoutList) {
		this.scoutList = scoutList;
	}
	
	public Scout getModdedScout() {
		return moddedScout;
	}

	public void setModdedScout(Scout moddedScout) {
		this.moddedScout = moddedScout;
	}
	
	public void undoEdit() {
		userController.changeEdit();
		loadData();
	}
	
	public void saveScoutEdit() {
		for(Scout s : scoutList) {
			scoutService.update(s);
		}
		userController.changeEdit();
	}

	public boolean isThisActiveTab(String tabName) {
		TabName tabParam = TabName.valueOf(tabName);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String path = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
		String[] pathArray = path.split("/");

		if (StringUtils.isNumeric(pathArray[pathArray.length-1]) && tabParam == TabName.INFO) {
			return true;
		} else if (tabParam.getLabel().equals(pathArray[pathArray.length-1]))
			return true;

		return false;
	}

}