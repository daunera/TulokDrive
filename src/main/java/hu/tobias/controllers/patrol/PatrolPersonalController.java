package hu.tobias.controllers.patrol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Address;
import hu.tobias.entities.Scout;
import hu.tobias.services.comparator.AddressComparator;
import hu.tobias.services.dao.AddressDao;
import hu.tobias.services.dao.PersonDao;

@Named(value = "patrolPersonal")
@ViewScoped
public class PatrolPersonalController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PersonDao personService;
	@EJB
	private AddressDao addressService;

	@Inject
	private PatrolController patrolController;

	// TODO: addressModal not working
	private List<Address> addresses = new ArrayList<Address>();
	private Address newAddress = new Address();
	private Address selectedAddress = new Address();

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

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(Address newAddress) {
		this.newAddress = newAddress;
	}

	public Address getSelectedAddress() {
		return selectedAddress;
	}

	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}

	public void saveEdit() {
		for (Scout s : patrolController.getPatrol().getScouts()) {
			personService.update(s.getPerson());
		}
		patrolController.getUserController().reloadUser();
		patrolController.getUserController().changeEdit();
	}

	public void saveAddress(Scout s, Address a) {
		if (a.getId() == null) {
			addressService.create(a);
		} else
			addressService.update(a);

		s.getPerson().setAddress(a);
		personService.update(s.getPerson());
		patrolController.loadData();
	}

	public void deleteAddress(Scout s) {
		if (s.getPerson().hasAddress()) {
			s.getPerson().setAddress(null);
		}
		personService.update(s.getPerson());
		patrolController.loadData();
	}

	public boolean setForModal(Scout s) {
		addresses = addressService.findAll();
		Collections.sort(addresses, new AddressComparator());

		if (s.getPerson().hasAddress())
			selectedAddress = s.getPerson().getAddress();
		else if (addresses.size() > 0)
			selectedAddress = addresses.get(0);
		else
			selectedAddress = new Address();

		newAddress = new Address();

		patrolController.setModdedScout(s);

		return true;
	}

}
