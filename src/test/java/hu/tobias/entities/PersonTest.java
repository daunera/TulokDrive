package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import hu.tobias.entities.enums.Gender;

public class PersonTest {

	private Person person1;
	private Person person2;

	@Before
	public void before() {
		person1 = new Person(Gender.MALE);
		person1.setId(1);
		person1.setLastname("Vezetéknév");
		person1.setFirstname("Keresztnév");
		person2 = new Person();
	}

	@Test
	public void testConstructor() {
		assertEquals(person1.getGender(), Gender.MALE);
	}

	@Test
	public void testEquals() {
		assertEquals(person2.equals(person1), false);
		person2.setId(1);
		assertEquals(person1.equals(person2), true);
		person2.setId(2);
		assertEquals(person1.equals(person2), false);
		assertEquals(person1.equals(new Fee()), false);
	}

	@Test
	public void testGetFullName() {
		assertEquals(person1.getFullName(), "Vezetéknév Keresztnév");
		assertEquals(person2.getFullName(), "Nincs név");
		person2.setLastname("Valaminév");
		assertEquals(person2.getFullName(), "Valaminév");
		person2.setLastname(null);
		person2.setFirstname("Valamimásiknév");
		assertEquals(person2.getFullName(), "Valamimásiknév");
		person2.setFirstname(null);
	}

	@Test
	public void testGetPersonalName() {
		assertEquals(person1.getPersonalName(), "Keresztnév");
		assertEquals(person2.getPersonalName(), "Nincs név");
		person2.setNickname("Becenevecske");
		assertEquals(person2.getPersonalName(), "Becenevecske");
	}

	@Test
	public void testHasFunctions() {
		assertEquals(person1.hasAddress(), false);
		assertEquals(person1.hasFather(), false);
		assertEquals(person1.hasMother(), false);
		
		person1.setAddress(new Address());
		person1.setFather(new Person());
		person1.setMother(new Person());
		
		assertEquals(person1.hasAddress(), true);
		assertEquals(person1.hasFather(), true);
		assertEquals(person1.hasMother(), true);
	}

	@Test
	public void testGetAge() {
		Date d = new Date();
		d.setTime(1);
		LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int age = Period.between(date, LocalDate.now()).getYears();
		
		person1.getBirthdate().setTime(1);
		assertEquals(person1.getAge(), age);
	}

}
