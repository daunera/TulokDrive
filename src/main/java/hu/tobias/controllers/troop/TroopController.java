package hu.tobias.controllers.troop;

import java.io.Serializable;
import java.util.Arrays;
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
import hu.tobias.entities.Leader;
import hu.tobias.entities.Scout;
import hu.tobias.entities.Troop;
import hu.tobias.entities.enums.TabName;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.dao.PersonDao;
import hu.tobias.services.dao.ScoutDao;
import hu.tobias.services.dao.TroopDao;

@Named(value = "troopController")
@ViewScoped
public class TroopController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TroopDao troopService;
	@EJB
	private ScoutDao scoutService;
	@EJB
	private PersonDao personService;

	@Inject
	private UserController userController;

	@Inject
	private Permission permission;
	
	private Runnable loader = new Runnable() {

		@Override
		public void run() {
			loadData();
		}
	};

	private Troop troop = new Troop();
	private int troopid;

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

	public Runnable getLoader() {
		return loader;
	}

	public void setLoader(Runnable loader) {
		this.loader = loader;
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

	public void undoEdit() {
		userController.changeEdit();
		loadData();
	}
	
	public void saveScoutEdit() {
		for (Scout s : troop.getScouts()) {
			scoutService.update(s);
		}
		for (Leader l : troop.getAllLeader()) {
			scoutService.update(l.getScout());
		}
		userController.changeEdit();
	}
	
	public void savePersonalEdit() {
		for (Scout s : troop.getScouts()) {
			personService.update(s.getPerson());
		}
		for (Leader l : troop.getAllLeader()) {
			personService.update(l.getScout().getPerson());
		}
		userController.reloadUser();
		userController.changeEdit();
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
