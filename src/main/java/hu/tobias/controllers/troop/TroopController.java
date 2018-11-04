package hu.tobias.controllers.troop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

import hu.tobias.controllers.Permission;
import hu.tobias.controllers.UserController;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Scout;
import hu.tobias.entities.Troop;
import hu.tobias.entities.enums.TabName;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.comparator.PatrolNameComparator;
import hu.tobias.services.comparator.ScoutNameComparator;
import hu.tobias.services.dao.PatrolDao;
import hu.tobias.services.dao.ScoutDao;
import hu.tobias.services.dao.TroopDao;

@Named(value = "troopController")
@ViewScoped
public class TroopController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TroopDao troopService;
	@EJB
	private PatrolDao patrolService;
	@EJB
	private ScoutDao scoutService;

	@Inject
	private UserController userController;

	@Inject
	private Permission permission;

	private Troop troop = new Troop();
	private int troopid;

	private List<Patrol> patrolList;
	private List<Scout> scoutList;

	public TroopController() {
	}

	@PostConstruct
	public void init() {
	}

	public void loadData() {
		try {
			permission.checkTroopPermission(troopid);
			troop = troopService.findById(troopid);
		} catch (NotFoundEntityException e) {
			userController.redirectDbError();
		}

		patrolList = new ArrayList<Patrol>(troop.getPatrols());
		Collections.sort(patrolList, new PatrolNameComparator());

		scoutList = new ArrayList<Scout>();
		for (Patrol p : patrolList) {
			scoutList.addAll(p.getScouts());
		}
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

	public Troop getTroop() {
		return troop;
	}

	public void setTroop(Troop troop) {
		this.troop = troop;
	}

	public int getTroopid() {
		return troopid;
	}

	public void setTroopid(int troopid) {
		this.troopid = troopid;
	}

	public List<Patrol> getPatrolList() {
		return patrolList;
	}

	public void setPatrolList(List<Patrol> patrolList) {
		this.patrolList = patrolList;
	}

	public List<Scout> getScoutList() {
		return scoutList;
	}

	public void setScoutList(List<Scout> scoutList) {
		this.scoutList = scoutList;
	}

	public void undoEdit() {
		userController.changeEdit();
		loadData();
	}

	public boolean isThisActiveTab(String tabName) {
		TabName tabParam = TabName.valueOf(tabName);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String path = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
		List<String> pathArray = Arrays.asList((path.split("/")));

		if (pathArray.contains(tabParam.getLabel())) {
			return true;
		} else if (pathArray.get(pathArray.size() - 2).equals("troop") && tabParam.equals(TabName.INFO))
			return true;

		return false;
	}

}
