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

import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.services.comparator.PersonNameComparator;
import hu.tobias.services.dao.PersonDao;

@Named(value = "patrolParents")
@ViewScoped
public class PatrolParentsController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PersonDao personService;

	@Inject
	private PatrolController patrolController;

	private List<Person> fathers = new ArrayList<Person>();
	private Person newFather = new Person();
	private Person selectedFather = new Person();
	
	private List<Person> mothers = new ArrayList<Person>();
	private Person newMother = new Person();
	private Person selectedMother = new Person();
	
	private Gender editedGender;

	public PatrolParentsController() {
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

	public List<Person> getFathers() {
		return fathers;
	}

	public void setFathers(List<Person> fathers) {
		this.fathers = fathers;
	}

	public Person getNewFather() {
		return newFather;
	}

	public void setNewFather(Person newFather) {
		this.newFather = newFather;
	}

	public Person getSelectedFather() {
		return selectedFather;
	}

	public void setSelectedFather(Person selectedFather) {
		this.selectedFather = selectedFather;
	}

	public List<Person> getMothers() {
		return mothers;
	}

	public void setMothers(List<Person> mothers) {
		this.mothers = mothers;
	}

	public Person getNewMother() {
		return newMother;
	}

	public void setNewMother(Person newMother) {
		this.newMother = newMother;
	}

	public Person getSelectedMother() {
		return selectedMother;
	}

	public void setSelectedMother(Person selectedMother) {
		this.selectedMother = selectedMother;
	}

	public Gender getEditedGender() {
		return editedGender;
	}

	public void setEditedGender(Gender editedGender) {
		this.editedGender = editedGender;
	}

	public void saveEdit() {
		for (Scout s : patrolController.getScoutList()) {
			personService.update(s.getPerson());
		}
		patrolController.getUserController().changeEdit();
	}

	public boolean setForFatherModal(Scout s) {
		editedGender = Gender.MALE;
		fathers = personService.findAllInOneGender(editedGender);
		fathers.remove(s.getPerson());
		Collections.sort(fathers, new PersonNameComparator());

		newFather = new Person();

		if (s.getPerson().hasFather())
			selectedFather = s.getPerson().getFather();
		else if (fathers.size() > 0)
			selectedFather = fathers.get(0);
		else
			selectedFather = new Person();

		patrolController.setModdedScout(s);
		return true;
	}
	
	public boolean setForMotherModal(Scout s) {
		editedGender = Gender.FEMALE;
		mothers = personService.findAllInOneGender(editedGender);
		mothers.remove(s.getPerson());
		Collections.sort(mothers, new PersonNameComparator());

		newMother = new Person(editedGender);

		if (s.getPerson().hasMother())
			selectedMother = s.getPerson().getMother();
		else if (mothers.size() > 0)
			selectedMother = mothers.get(0);
		else
			selectedMother = new Person();

		patrolController.setModdedScout(s);
		return true;
	}

	public void saveParent(Scout s, Gender g, Person p) {
		p.setGender(g);

		if (p.getId() == null)
			personService.create(p);
		else
			personService.update(p);

		if (g.equals(Gender.MALE))
			s.getPerson().setFather(p);
		else
			s.getPerson().setMother(p);

		personService.update(s.getPerson());
		patrolController.loadData();
	}

	public void deleteParent(Scout s, Gender g) {;
		if (g.equals(Gender.MALE) && s.getPerson().hasFather()) {
			s.getPerson().setFather(null);
			personService.update(s.getPerson());
			patrolController.loadData();
		}
		else if (g.equals(Gender.FEMALE) && s.getPerson().hasMother()) {
			
			s.getPerson().setMother(null);
			personService.update(s.getPerson());
			patrolController.loadData();
		}
	}

}
