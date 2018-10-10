package hu.tobias.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.services.comparator.PersonNameComparator;
import hu.tobias.services.dao.PersonDao;

@Named(value = "parentBean")
@ViewScoped
public class ParentBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PersonDao personService;

	private List<Person> parents = new ArrayList<Person>();
	private Person newParent = new Person();
	private Person selectedParent = new Person();

	private Gender editedGender;

	public ParentBean() {
	}

	@PostConstruct
	public void init() {
	}

	public PersonDao getPersonService() {
		return personService;
	}

	public void setPersonService(PersonDao personService) {
		this.personService = personService;
	}

	public List<Person> getParents() {
		return parents;
	}

	public void setParents(List<Person> parents) {
		this.parents = parents;
	}

	public Person getNewParent() {
		return newParent;
	}

	public void setNewParent(Person newParent) {
		this.newParent = newParent;
	}

	public Person getSelectedParent() {
		return selectedParent;
	}

	public void setSelectedParent(Person selectedParent) {
		this.selectedParent = selectedParent;
	}

	public Gender getEditedGender() {
		return editedGender;
	}

	public void setEditedGender(Gender editedGender) {
		this.editedGender = editedGender;
	}

	public boolean setForModal(Scout s, String gender) {
		editedGender = Gender.valueOf(gender);

		parents = personService.findAllInOneGender(editedGender);
		parents.remove(s.getPerson());
		Collections.sort(parents, new PersonNameComparator());

		newParent = new Person(editedGender);

		if (editedGender.equals(Gender.FEMALE) && s.getPerson().hasMother())
			selectedParent = s.getPerson().getMother();
		else if (editedGender.equals(Gender.MALE) && s.getPerson().hasFather())
			selectedParent = s.getPerson().getFather();
		else if (parents.size() > 0)
			selectedParent = parents.get(0);
		else
			selectedParent = new Person(editedGender);

		return true;
	}

	public void saveParent(Scout s, Gender g, Person p) {
		if (!g.equals(Gender.NOTDEFINED)) {
			if (p.getId() == null)
				personService.create(p);
			else
				personService.update(p);

			if (p.getGender().equals(Gender.MALE))
				s.getPerson().setFather(p);
			else
				s.getPerson().setMother(p);

			personService.update(s.getPerson());
		}
	}

	public void deleteParent(Scout s, Gender g) {
		if (g.equals(Gender.MALE) && s.getPerson().hasFather()) {
			s.getPerson().setFather(null);
			personService.update(s.getPerson());
		} else if (g.equals(Gender.FEMALE) && s.getPerson().hasMother()) {
			s.getPerson().setMother(null);
			personService.update(s.getPerson());
		}
	}

}
