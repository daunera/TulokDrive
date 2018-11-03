package hu.tobias.controllers.uniform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.controllers.Permission;
import hu.tobias.controllers.UserController;
import hu.tobias.entities.Scout;
import hu.tobias.entities.UniformRent;
import hu.tobias.services.comparator.ScoutNameComparator;
import hu.tobias.services.dao.ScoutDao;
import hu.tobias.services.dao.UniformRentDao;

@Named(value = "uniformController")
@ViewScoped
public class UniformController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UniformRentDao uniformService;
	@EJB
	private ScoutDao scoutService;

	@Inject
	private UserController userController;

	@Inject
	private Permission permission;

	private List<UniformRent> rents = new ArrayList<UniformRent>();
	private List<UniformRent> activeRents = new ArrayList<UniformRent>();
	private List<UniformRent> returnedRents = new ArrayList<UniformRent>();

	private UniformRent newRent = new UniformRent();
	private UniformRent selectedRent = new UniformRent();
	private List<Scout> scouts = new ArrayList<Scout>();

	public UniformController() {
	}

	@PostConstruct
	public void init() {
	}

	public void loadData() {
		permission.checkUniformPermission();
		rents = uniformService.findAll();

		activeRents.clear();
		returnedRents.clear();
		for (UniformRent u : rents) {
			if (u.getReturned() == null)
				activeRents.add(u);
			else if (u.getReturned())
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

	public UniformRent getNewRent() {
		return newRent;
	}

	public void setNewRent(UniformRent newRent) {
		this.newRent = newRent;
	}

	public UniformRent getSelectedRent() {
		return selectedRent;
	}

	public void setSelectedRent(UniformRent selectedRent) {
		this.selectedRent = selectedRent;
	}

	public List<Scout> getScouts() {
		return scouts;
	}

	public void setScouts(List<Scout> scouts) {
		this.scouts = scouts;
	}

	public boolean setForNewRentModal() {
		scouts = scoutService.findAll();
		Collections.sort(scouts, new ScoutNameComparator());

		newRent = new UniformRent();
		newRent.setPrice(4000);
		return true;
	}

	public void saveRent(UniformRent r) {
		if (r.getId() == null) {
			uniformService.create(r);
		} else {
			uniformService.update(r);
		}
		loadData();
		userController.changeEdit();
	}

	public boolean setForRentDeleteModal(UniformRent r) {
		selectedRent = r;
		return true;
	}

	public void deleteRent(UniformRent r) {
		uniformService.delete(r);

		loadData();
		userController.changeEdit();
	}
	
	public boolean setForRentBackModal(UniformRent r) {
		selectedRent = r;
		selectedRent.setReturndate(new Date());
		return true;
	}

	public void backingRent(UniformRent r) {
		r.setReturned(true);
		if(r.getReturndate() == null) {
			r.setReturndate(new Date());
		}
		uniformService.update(r);

		loadData();
		userController.changeEdit();
	}

}
