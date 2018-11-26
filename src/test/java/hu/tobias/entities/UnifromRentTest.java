package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import hu.tobias.entities.enums.PromiseType;
import hu.tobias.entities.enums.RentType;
import hu.tobias.entities.enums.Size;
import hu.tobias.entities.enums.Status;
import hu.tobias.services.utils.Utils;

public class UnifromRentTest {

	private UniformRent uniform1;
	private UniformRent uniform2;
	private Date now;
	private Scout scout;

	@Before
	public void before() {
		scout = new Scout();
		scout.setId(1);
		uniform1 = new UniformRent(scout);

		uniform2 = new UniformRent();
		now = Utils.now();
	}

	@Test
	public void testConstructor() {
		assertEquals(uniform1.getScout().equals(scout), true);
		assertEquals(uniform1.getBegindate(), now);
		assertEquals(uniform1.getRenttype(), RentType.NOW);
		assertEquals(uniform1.getUniformsize(), Size.NOTDEFINED);

		assertEquals(uniform2.getScout(), null);
	}

	@Test
	public void testGetActualReturnPrice() {
		uniform2.setRenttype(RentType.VERYOLD);
		assertEquals(uniform2.getActualReturnPrice().equals(0), true);
		uniform2.setRenttype(RentType.OLD);
		uniform2.setBegindate(null);
		assertEquals(uniform2.getActualReturnPrice().equals(-1), true);

		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, -2);
		uniform2.setBegindate(c.getTime());
		assertEquals(uniform2.getActualReturnPrice().equals(2100), true);
		c.add(Calendar.YEAR, -10);
		uniform2.setBegindate(c.getTime());
		assertEquals(uniform2.getActualReturnPrice().equals(0), true);

		uniform2.setRenttype(RentType.NOTDEFINED);
		assertEquals(uniform2.getActualReturnPrice().equals(0), true);

		uniform2.setRenttype(RentType.NOW);
		c.add(Calendar.YEAR, 10);
		uniform2.setBegindate(c.getTime());
		assertEquals(uniform2.getActualReturnPrice().equals(2000), true);
		c.add(Calendar.YEAR, -10);
		uniform2.setBegindate(c.getTime());
		assertEquals(uniform2.getActualReturnPrice().equals(0), true);
	}

	@Test
	public void testGetPlannedReturnDate() {
		uniform1.setRenttype(RentType.VERYOLD);
		assertEquals(uniform1.getPlannedReturnDate(), "MOST");

		uniform1.setRenttype(RentType.OLD);
		uniform1.getScout().getPromises().add(new Promise(null, PromiseType.CS));
		assertEquals(uniform1.getPlannedReturnDate(), "MOST");

		uniform1.getScout().getPromises().clear();
		uniform1.getScout().setStatus(Status.QUITTED);
		assertEquals(uniform1.getPlannedReturnDate(), "MOST");

		uniform1.getScout().setStatus(Status.ACTIVE);
		uniform1.getScout().setPatrol(new Patrol());
		uniform1.getScout().getPatrol().setBirthdate(now);
		uniform1.getScout().getPatrol().setStartclass(6);
		assertEquals(uniform1.getPlannedReturnDate(), "MOST");

		uniform1.getScout().getPatrol().setStartclass(2);
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.MONTH, Calendar.SEPTEMBER);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.YEAR, 6 - 2);
		uniform1.setBegindate(null);
		assertEquals(uniform1.getPlannedReturnDate(), Utils.simpleDate(c.getTime()));

		uniform1.getScout().getPatrol().setBirthdate(null);
		assertEquals(uniform1.getPlannedReturnDate(), "PASSZ");
		uniform1.getScout().getPatrol().setBirthdate(now);

		uniform1.setBegindate(now);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(now);
		c2.add(Calendar.YEAR, 3);
		c2.add(Calendar.DATE, 1);
		if (c.after(c2))
			assertEquals(uniform1.getPlannedReturnDate(), Utils.simpleDate(c2.getTime()));
		else
			assertEquals(uniform1.getPlannedReturnDate(), Utils.simpleDate(c.getTime()));

		uniform1.setRenttype(RentType.NOW);
		uniform1.getScout().setStatus(Status.QUITTED);
		assertEquals(uniform1.getPlannedReturnDate(), "MOST");

		uniform1.getScout().setStatus(Status.ACTIVE);
		uniform1.getScout().getPatrol().setStartclass(6);
		assertEquals(uniform1.getPlannedReturnDate(), "MOST");

		uniform1.getScout().getPatrol().setStartclass(2);
		assertEquals(uniform1.getPlannedReturnDate(), Utils.simpleDate(c.getTime()));
		
		uniform1.getScout().getPatrol().setBirthdate(null);
		assertEquals(uniform1.getPlannedReturnDate(), "PASSZ");
		uniform1.getScout().getPatrol().setBirthdate(now);

		uniform1.setRenttype(RentType.NOTDEFINED);
		assertEquals(uniform1.getPlannedReturnDate(), "MOST");
	}

}
