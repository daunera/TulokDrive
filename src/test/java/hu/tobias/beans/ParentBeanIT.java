package hu.tobias.beans;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.dao.PersonDao;

@RunWith(Arquillian.class)
public class ParentBeanIT {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addPackage(PersonDao.class.getPackage()).addPackage(Person.class.getPackage())
				.addPackage(NotFoundEntityException.class.getPackage()).addPackage(Gender.class.getPackage())
				.addClass(ParentBean.class);
	}

	@EJB
	private PersonDao personService;

	private ParentBean parentBean;

	private Person person;
	private Scout scout;

	private Person father;
	private Person mother;

	private int runFlag = 0;
	private Runnable runnableStub = new Runnable() {

		@Override
		public void run() {
			runFlag++;
		}
	};

	@Before
	public void before() {
		parentBean = new ParentBean(personService);

		person = new Person();
		personService.create(person);
		scout = new Scout();
		scout.setPerson(person);
	}

	@Test
	public void testSetForNewModal() {
		initData();

		parentBean.setForFatherModal(scout);
		assertEquals(parentBean.getParents().size(), 3);

		parentBean.setForMotherModal(scout);
		assertEquals(parentBean.getParents().size(), 2);
	}

	@Test
	public void testSaveFee() throws NotFoundEntityException {
		father = new Person(Gender.MALE);

		parentBean.saveParent(scout, Gender.MALE, father, runnableStub);
		assertEquals(runFlag, 1);
		assertEquals(father.getId() != null, true);
		assertEquals(scout.getPerson().getFather().getId().intValue(), father.getId().intValue());

		mother = new Person(Gender.FEMALE);
		mother.setLastname("Elsomama");
		personService.create(mother);
		mother.setLastname("Rózsa");

		parentBean.saveParent(scout, Gender.FEMALE, mother, runnableStub);
		assertEquals(runFlag, 2);
		Person returnP = personService.findById(person.getId());
		assertEquals(returnP.getMother().getLastname(), "Rózsa");
		assertEquals(returnP.getMother().getId().intValue(), mother.getId().intValue());
	}

	@Test
	public void testDeleteParent() {
		initData();

		person.setFather(personService.findAllInOneGender(Gender.MALE).get(0));
		person.setMother(personService.findAllInOneGender(Gender.FEMALE).get(0));
		personService.update(person);

		assertEquals(person.hasFather(), true);
		assertEquals(person.hasMother(), true);

		parentBean.deleteParent(scout, Gender.MALE, runnableStub);
		assertEquals(runFlag, 1);
		assertEquals(person.hasFather(), false);
		assertEquals(person.hasMother(), true);

		parentBean.deleteParent(scout, Gender.FEMALE, runnableStub);
		assertEquals(runFlag, 2);
		assertEquals(person.hasMother(), false);

	}

	private void initData() {
		for (Person per : personService.findAll()) {
			personService.delete(per);
		}

		father = new Person(Gender.MALE);
		father.setLastname("Elsopapa");
		personService.create(father);

		Person apu2 = new Person(Gender.MALE);
		apu2.setLastname("Masodpapa");
		personService.create(apu2);

		Person apu3 = new Person(Gender.MALE);
		apu2.setLastname("Harmadpapa");
		personService.create(apu3);

		mother = new Person(Gender.FEMALE);
		mother.setLastname("Elsomama");
		personService.create(mother);

		Person anyu2 = new Person(Gender.FEMALE);
		anyu2.setLastname("Masodmama");
		personService.create(anyu2);

		runFlag = 0;
	}

}
