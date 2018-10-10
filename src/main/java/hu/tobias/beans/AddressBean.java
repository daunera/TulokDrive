package hu.tobias.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import hu.tobias.entities.Address;
import hu.tobias.entities.Scout;
import hu.tobias.services.comparator.AddressComparator;
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
	
	private List<Address> addresses = new ArrayList<Address>();
	private Address newAddress = new Address();
	private Address selectedAddress = new Address();
	
	public AddressBean() {}
	
	@PostConstruct
	public void init() {
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

		return true;
	}
	
	public void saveAddress(Scout s, Address a) {
		if (a.getId() == null) {
			addressService.create(a);
		} else
			addressService.update(a);

		s.getPerson().setAddress(a);
		personService.update(s.getPerson());
	}

	public void deleteAddress(Scout s) {
		if (s.getPerson().hasAddress()) {
			s.getPerson().setAddress(null);
		}
		personService.update(s.getPerson());
	}

}
