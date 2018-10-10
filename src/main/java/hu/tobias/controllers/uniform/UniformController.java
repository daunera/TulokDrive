package hu.tobias.controllers.uniform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.controllers.Permission;
import hu.tobias.controllers.UserController;
import hu.tobias.entities.UniformRent;
import hu.tobias.services.dao.UniformRentDao;

@Named(value = "uniformController")
@ViewScoped
public class UniformController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UniformRentDao uniformService;

	@Inject
	private UserController userController;

	@Inject
	private Permission permission;
	
	private List<UniformRent> rents = new ArrayList<UniformRent>();
	private List<UniformRent> activeRents = new ArrayList<UniformRent>();
	private List<UniformRent> returnedRents = new ArrayList<UniformRent>();

	public UniformController() {
	}

	@PostConstruct
	public void init() {
	}

	public void loadData() {
		permission.checkUniformPermission();
		rents = uniformService.findAll();
		
		for(UniformRent u : rents) {
			if(u.getReturned() == null)
				activeRents.add(u);
			else if(u.getReturned())
				returnedRents.add(u);
			else
				activeRents.add(u);
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

	public List<UniformRent> getRents() {
		return rents;
	}

	public void setRents(List<UniformRent> rents) {
		this.rents = rents;
	}

	public List<UniformRent> getActiveRents() {
		return activeRents;
	}

	public void setActiveRents(List<UniformRent> activeRents) {
		this.activeRents = activeRents;
	}

	public List<UniformRent> getReturnedRents() {
		return returnedRents;
	}

	public void setReturnedRents(List<UniformRent> returnedRents) {
		this.returnedRents = returnedRents;
	}

}
