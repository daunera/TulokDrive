package hu.tobias.beans;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.services.dao.PersonDao;

public class ParentBeanTest {

	private ParentBean parentBean;
	private Person personDb = new Person();
	private List<Person> persons = new ArrayList<>();
	private Scout scout;
	private int runFlag = 0;

	private Runnable runabbleStub = new Runnable() {

		@Override
		public void run() {
			runFlag++;
		}
	};

	@Before
	public void before() {
		PersonDao mockPersonDao = mock(PersonDao.class);

		doAnswer(new Answer<Person>() {

			@Override
			public Person answer(InvocationOnMock arg0) throws Throwable {
				Person p = arg0.getArgument(0);
				p.setId(10);
				personDb = p;
				return p;
			}
		}).when(mockPersonDao).create(any());
		doAnswer(new Answer<Person>() {

			@Override
			public Person answer(InvocationOnMock arg0) throws Throwable {
				Person p = arg0.getArgument(0);
				if (p.getId().intValue() == 1) {
					personDb = p;
				}
				return p;
			}
		}).when(mockPersonDao).update(any());
		doAnswer(new Answer<List<Person>>() {

			@Override
			public List<Person> answer(InvocationOnMock arg0) throws Throwable {
				return persons;
			}
		}).when(mockPersonDao).findAllInOneGender(any());

		parentBean = new ParentBean(mockPersonDao);
		scout = new Scout();
		scout.setId(1);
		scout.setPerson(new Person());
		scout.getPerson().setId(1);

		Person person = new Person();
		person.setId(99);
		persons.add(person);
		persons.add(scout.getPerson());
	}

	@Test
	public void testSetForModal() {
		scout.getPerson().setFather(null);
		scout.getPerson().setMother(null);

		parentBean.setForFatherModal(scout);
		assertEquals(parentBean.getEditedScout().equals(scout), true);
		assertEquals(parentBean.getEditedGender(), Gender.MALE);
		assertEquals(parentBean.getParents().size(), 1);
		assertEquals(parentBean.getSelectedParent().equals(persons.get(0)), true);
		assertEquals(parentBean.getNewParent().getId(), null);
		assertEquals(parentBean.getNewParent().getGender(), Gender.MALE);

		persons.clear();
		parentBean.setForFatherModal(scout);
		assertEquals(parentBean.getSelectedParent().getGender(), Gender.MALE);
		assertEquals(parentBean.getSelectedParent().getId(), null);

		Person father = new Person();
		father.setId(30);
		scout.getPerson().setFather(father);
		parentBean.setForFatherModal(scout);
		assertEquals(parentBean.getSelectedParent().getId().intValue(), 30);

		Person mother = new Person();
		mother.setId(40);
		scout.getPerson().setMother(mother);
		parentBean.setForMotherModal(scout);
		assertEquals(parentBean.getSelectedParent().getId().intValue(), 40);
	}

	@Test
	public void testSaveParent() {
		runFlag = 0;
		personDb = null;
		parentBean.saveParent(scout, null, null, runabbleStub);
		assertEquals(runFlag, 0);
		parentBean.saveParent(scout, Gender.NOTDEFINED, null, runabbleStub);
		assertEquals(runFlag, 0);

		Person p1 = new Person();
		p1.setId(20);
		p1.setGender(Gender.MALE);
		parentBean.saveParent(scout, Gender.MALE, p1, runabbleStub);
		assertEquals(personDb.getFather().getId().intValue(), 20);
		assertEquals(runFlag, 1);

		Person p2 = new Person();
		p2.setGender(Gender.FEMALE);
		parentBean.saveParent(scout, Gender.MALE, p2, runabbleStub);
		assertEquals(personDb.getMother().getId().intValue(), 10);
		assertEquals(runFlag, 2);
	}

	@Test
	public void testDeleteParent() {
		runFlag = 0;
		scout.getPerson().setFather(null);
		scout.getPerson().setMother(null);
		parentBean.deleteParent(null, null, runabbleStub);
		assertEquals(runFlag, 0);
		parentBean.deleteParent(scout, Gender.MALE, runabbleStub);
		assertEquals(runFlag, 0);
		parentBean.deleteParent(scout, Gender.FEMALE, runabbleStub);
		assertEquals(runFlag, 0);

		scout.getPerson().setFather(new Person(Gender.MALE));
		parentBean.deleteParent(scout, Gender.MALE, runabbleStub);
		assertEquals(personDb.equals(scout.getPerson()), true);
		assertEquals(personDb.hasFather(), false);
		assertEquals(runFlag, 1);

		scout.getPerson().setMother(new Person(Gender.FEMALE));
		parentBean.deleteParent(scout, Gender.FEMALE, runabbleStub);
		assertEquals(personDb.equals(scout.getPerson()), true);
		assertEquals(personDb.hasMother(), false);
		assertEquals(runFlag, 2);
	}

}
