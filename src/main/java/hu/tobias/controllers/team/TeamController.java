package hu.tobias.controllers.team;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;

import hu.tobias.controllers.Permission;
import hu.tobias.controllers.UserController;
import hu.tobias.entities.enums.TabName;

@Named(value = "teamController")
@ViewScoped
public class TeamController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private UserController userController;

	@Inject
	private Permission permission;

	public TeamController() {
	}

	@PostConstruct
	public void init() {
	}

	public void loadData() {
		permission.checkTeamPermission();
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

	public boolean isThisActiveTab(String tabName) {
		TabName tabParam = TabName.valueOf(tabName);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String path = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
		String[] pathArray = path.split("/");
		
		if (tabParam.getLabel().equals(pathArray[pathArray.length-1])) {
			return true;
		}
		return false;
	}

}
