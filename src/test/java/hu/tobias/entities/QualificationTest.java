package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import hu.tobias.entities.enums.QualificationType;

public class QualificationTest {
	
	private Qualification qualification1;
	private Qualification qualification2;
	private Qualification qualification3;

	@Before
	public void before() {
		Scout s = new Scout();
		s.setId(1);
		qualification1 = new Qualification(s, QualificationType.OV);
		qualification1.setId(1);
		qualification1.setPlace("Budapest");
		qualification1.setCourse("PUF2010");
		Date d = new Date();
		d.setTime(1);
		qualification1.setDate(d);

		qualification2 = new Qualification(s);
		qualification2.setId(1);

		qualification3 = new Qualification();
		qualification3.setId(2);
	}

	@Test
	public void testConstructor() {
		assertEquals(qualification1.getScout().getId().equals(1), true);
		assertEquals(qualification1.getType(), QualificationType.OV);

		assertEquals(qualification2.getScout().getId().equals(1), true);

		assertEquals(qualification3.getScout(), null);
	}

	@Test
	public void testEquals() {
		assertEquals(qualification1.equals(qualification2), true);
		assertEquals(qualification1.equals(qualification3), false);
		assertEquals(new Qualification().equals(qualification3), false);
		assertEquals(new Qualification().equals(new Fee()), false);
	}

	@Test
	public void testGetFullInfo() {
		assertEquals(qualification1.getFullInfo(), "őrsvezetői (PUF2010, 1970.01.01, Budapest)");
		assertEquals(qualification2.getFullInfo(), "képesítés");
	}

}
