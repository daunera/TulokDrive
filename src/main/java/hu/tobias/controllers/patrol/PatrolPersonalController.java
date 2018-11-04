package hu.tobias.controllers.patrol;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.beans.AddressBean;
import hu.tobias.entities.Leader;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.PersonDao;

@Named(value = "patrolPersonal")
@ViewScoped
public class PatrolPersonalController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PersonDao personService;

	@Inject
	private PatrolController patrolController;
	@Inject
	private AddressBean addressBean;

	public PatrolPersonalController() {
	}

	@PostConstruct
	public void init() {
	}

	public PatrolController getPatrolController() {
		return patrolController;
	}

	public void setPatrolController(PatrolController patrolController) {
		this.patrolController = patrolController;
	}

	public AddressBean getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}

	public void saveEdit() {
		for (Scout s : patrolController.getPatrol().getScouts()) {
			personService.update(s.getPerson());
		}
		for (Leader l : patrolController.getPatrol().getLeaders()) {
			personService.update(l.getScout().getPerson());
		}
		patrolController.getUserController().reloadUser();
		patrolController.getUserController().changeEdit();
	}

	public boolean setForModal(Scout s) {
		addressBean.setForModal(s);
		patrolController.setModdedScout(s);

		return true;
	}

}
