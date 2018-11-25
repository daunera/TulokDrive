package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import hu.tobias.entities.enums.ChallengeType;

public class ChallengeTest {

	private Challenge challenge1;
	private Challenge challenge2;
	private Challenge challenge3;

	@Before
	public void before() {
		Scout s = new Scout();
		s.setId(1);
		challenge1 = new Challenge(s, ChallengeType.CS1);
		challenge1.setId(1);
		challenge1.setPlace("Budapest");
		Date d = new Date();
		d.setTime(1);
		challenge1.setDate(d);

		challenge2 = new Challenge(s);
		challenge2.setId(1);

		challenge3 = new Challenge();
		challenge3.setId(2);
	}

	@Test
	public void testConstructor() {
		assertEquals(challenge1.getScout().getId().equals(1), true);
		assertEquals(challenge1.getType(), ChallengeType.CS1);

		assertEquals(challenge2.getScout().getId().equals(1), true);

		assertEquals(challenge3.getScout(), null);
	}

	@Test
	public void testEquals() {
		assertEquals(challenge1.equals(challenge2), true);
		assertEquals(challenge1.equals(challenge3), false);
		assertEquals(new Challenge().equals(challenge3), false);
		assertEquals(new Challenge().equals(new Fee()), false);
	}

	@Test
	public void testGetFullInfo() {
		assertEquals(challenge1.getFullInfo(), "Újonc (1970.01.01, Budapest)");
		assertEquals(challenge2.getFullInfo(), "Próba");
	}

	@Test
	public void testGetDetails() {
		assertEquals(challenge1.getDetails(), "1970.01.01, Budapest");
		assertEquals(challenge2.getDetails(), "Megszerezte");
	}

}
