package hu.tobias.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.controllers.UserController;
import hu.tobias.entities.Address;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.AddressDao;
import hu.tobias.services.dao.PersonDao;

@Named(value = "addressBean")
@ViewScoped
public class AddressBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PersonDao personService;
	@EJB
	private AddressDao addressService;
	
	@Inject
	private UserController userController;
	
	private List<Address> addresses = new ArrayList<Address>();
	private Address newAddress = new Address();
	private Address selectedAddress = new Address();
	
	private Scout editedScout;
	
	public AddressBean() {}
	
	@PostConstruct
	public void init() {
	}

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
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
	
	public Scout getEditedScout() {
		return editedScout;
	}

	public void setEditedScout(Scout editedScout) {
		this.editedScout = editedScout;
	}

	public boolean setForModal(Scout s) {
		editedScout = s;
		
		addresses = addressService.findAll();

		if (s.getPerson().hasAddress())
			selectedAddress = s.getPerson().getAddress();
		else if (addresses.size() > 0)
			selectedAddress = addresses.get(0);
		else
			selectedAddress = new Address();

		newAddress = new Address();

		return true;
	}
	
	public void saveAddress(Scout s, Address a, Runnable function) {
		if (a.getId() == null) {
			addressService.create(a);
		} else
			addressService.update(a);

		s.getPerson().setAddress(a);
		personService.update(s.getPerson());
		function.run();
	}

	public void deleteAddress(Scout s, Runnable function) {
		if (s.getPerson().hasAddress()) {
			s.getPerson().setAddress(null);
		}
		personService.update(s.getPerson());
		function.run();
	}

}
