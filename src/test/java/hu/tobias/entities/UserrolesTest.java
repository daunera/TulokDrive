package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserrolesTest {

	private Userroles userrole1;
	private Userroles userrole2;
	private Leader leader;

	@Before
	public void before() {
		leader = new Leader(1, "bela", "jelszo");
		userrole1 = new Userroles(leader);
		userrole2 = new Userroles();
	}

	@Test
	public void testConstructor() {
		assertEquals(userrole1.getUsername(), leader.getUsername());
		assertEquals(userrole2.getUsername(), null);
	}

}
