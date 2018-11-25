package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import hu.tobias.entities.enums.PromiseType;

public class PromiseTest {

	private Promise promise1;
	private Promise promise2;
	private Promise promise3;

	@Before
	public void before() {
		Scout s = new Scout();
		s.setId(1);
		promise1 = new Promise(s, PromiseType.CS);
		promise1.setId(1);
		promise1.setPlace("Budapest");
		Date d = new Date();
		d.setTime(1);
		promise1.setDate(d);

		promise2 = new Promise(s);
		promise2.setId(1);

		promise3 = new Promise();
		promise3.setId(2);
	}

	@Test
	public void constructor() {
		assertEquals(promise1.getScout().getId().equals(1), true);
		assertEquals(promise1.getType(), PromiseType.CS);

		assertEquals(promise2.getScout().getId().equals(1), true);

		assertEquals(promise3.getScout(), null);
	}

	@Test
	public void equals() {
		assertEquals(promise1.equals(promise2), true);
		assertEquals(promise1.equals(promise3), false);
		assertEquals(new Promise().equals(promise3), false);
		assertEquals(new Promise().equals(new Fee()), false);
	}

	@Test
	public void getFullInfo() {
		assertEquals(promise1.getFullInfo(), "Cserkész fogadalom (1970.01.01, Budapest)");
		assertEquals(promise2.getFullInfo(), "Fogadalom");
	}

	@Test
	public void getDetails() {
		assertEquals(promise1.getDetails(), "1970.01.01, Budapest");
		assertEquals(promise2.getDetails(), "Letette");
	}

}
