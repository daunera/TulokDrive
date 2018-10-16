package hu.tobias.controllers.profile;

import java.io.Serializable;

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
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.TabName;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.dao.PersonDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "profileController")
@ViewScoped
public class ProfileController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PersonDao personService;
	@EJB
	private ScoutDao scoutService;
	
	@Inject
	private UserController userController;
	
	@Inject
	private Permission permission;
	
	private Scout scout = new Scout();
	private int scoutid;
		
	public ProfileController() {	
	}
	
	@PostConstruct
	public void init() {
	}

	public void loadData() {
		try {
			permission.checkScoutPermission(scoutid);
			scout = scoutService.findById(scoutid);
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

	public Scout getScout() {
		return scout;
	}

	public void setScout(Scout scout) {
		this.scout = scout;
	}

	public int getScoutid() {
		return scoutid;
	}

	public void setScoutid(int scoutid) {
		this.scoutid = scoutid;
	}
	
	
	public void undoEdit() {
		userController.changeEdit();
		loadData();
	}

	public void cancelEdit() {
	}
	
	public void saveEdit() {
		personService.update(scout.getPerson());
		userController.reloadUser();
		userController.changeEdit();
	}
	
	public boolean isThisActiveTab(String tabName) {
		TabName tabParam = TabName.valueOf(tabName);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String path = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
		String[] pathArray = path.split("/");

		if (StringUtils.isNumeric(pathArray[pathArray.length-1]) && tabParam == TabName.PERSONAL) {
			return true;
		} else if (tabParam.getLabel().equals(pathArray[pathArray.length-1]))
			return true;

		return false;
	}

}
