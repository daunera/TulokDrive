package hu.tobias.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.enums.Religion;
import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "person")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	private String lastname;
	private String firstname;
	private String nickname;

	private Date birthdate;
	private String birthplace;
	private String nameday;

	private String email;
	private String phone;

	private Religion religion = Religion.NEMISMERT;
	private Gender gender = Gender.NOTDEFINED;

	private String personcardid;
	private String tajid;
	private String omid;
	private String studentcardid;

	private String foodsensitivity;
	private String disease;
	private String other;

	@OneToOne(mappedBy = "person")
	private Scout scout;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@ManyToOne
	@JoinColumn(name = "father_id")
	private Person father;

	@ManyToOne
	@JoinColumn(name = "mother_id")
	private Person mother;

	public Person() {
		super();
	}

	public Person(Gender g) {
		super();
		this.gender = g;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getNameday() {
		return nameday;
	}

	public void setNameday(String nameday) {
		this.nameday = nameday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Religion getReligion() {
		return religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPersoncardid() {
		return personcardid;
	}

	public void setPersoncardid(String personcardid) {
		this.personcardid = personcardid;
	}

	public String getTajid() {
		return tajid;
	}

	public void setTajid(String tajid) {
		this.tajid = tajid;
	}

	public String getOmid() {
		return omid;
	}

	public void setOmid(String omid) {
		this.omid = omid;
	}

	public String getStudentcardid() {
		return studentcardid;
	}

	public void setStudentcardid(String studentcardid) {
		this.studentcardid = studentcardid;
	}

	public String getFoodsensitivity() {
		return foodsensitivity;
	}

	public void setFoodsensitivity(String foodsensitivity) {
		this.foodsensitivity = foodsensitivity;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Scout getScout() {
		return scout;
	}

	public void setScout(Scout scout) {
		this.scout = scout;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

	public String getFullName() {
		if (lastname == null && firstname == null)
			return "Nincs név";
		else if (lastname == null)
			return firstname;
		else if (firstname == null)
			return lastname;
		else
			return lastname + " " + firstname;
	}

	public String getPersonalName() {
		if (!Utils.isEmpty(nickname))
			return nickname;
		else if (firstname == null)
			return "Nincs név";
		else
			return firstname;
	}

	public boolean hasAddress() {
		if (address == null)
			return false;
		return true;
	}

	public boolean hasFather() {
		if (father == null)
			return false;
		return true;
	}

	public boolean hasMother() {
		if (mother == null)
			return false;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Person) {
			Person o = (Person) obj;
			if (this.id == null)
				return false;
			return this.id.equals(o.id);
		}
		return false;
	}

	public int getAge() {
		return Utils.ageInYear(birthdate);
	}

}
