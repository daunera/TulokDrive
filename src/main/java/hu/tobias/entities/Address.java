package hu.tobias.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "address")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	private String country;
	private String city;
	private String postcode;
	private String street;

	private String other;

	private String phone;

	@OneToMany(mappedBy = "address")
	private List<Person> persons;

	public Address() {
		super();
		this.country = "Magyarország";
		this.city = "Budapest";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public String getFullAddress() {
		String address = country + ", " + postcode + " " + city + ", " + street;
		if (!Utils.isEmpty(other)) {
			address += ", " + other;
		}
		return address;
	}

	public String getFullAddressWithPhone() {
		if (id == null)
			return "Új cím hozzáadása";
		String address = country + ", " + postcode + " " + city + ", " + street;
		if (!Utils.isEmpty(other)) {
			address += ", " + other;
		}
		if (!Utils.isEmpty(phone)) {
			address += ", " + phone;
		}
		return address;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Address) {
			Address o = (Address) obj;
			return this.id.equals(o.id);
		}
		return false;
	}

}
