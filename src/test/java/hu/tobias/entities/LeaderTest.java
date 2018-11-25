package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class LeaderTest {

	private Leader leader1;
	private Leader leader2;
	private Leader leader3;

	@Before
	public void before() {
		leader1 = new Leader(1, "username", "password");
		leader2 = new Leader(1);
		leader3 = new Leader();
	}

	@Test
	public void testConstructor() {
		assertEquals(leader1.getId().equals(1), true);
		assertEquals(leader1.getUsername(), "username");
		assertEquals(leader1.getPassword().equals("password"), false);
		assertEquals(BCrypt.checkpw("password", leader1.getPassword()), true);

		assertEquals(leader2.getId().equals(1), true);
		assertEquals(leader3.getId(), null);
	}

	@Test
	public void testEquals() {
		assertEquals(leader1.equals(leader2), true);
		assertEquals(leader3.equals(leader1), false);
		leader3.setId(2);
		assertEquals(leader3.equals(leader1), false);
		assertEquals(leader1.equals(new Fee()), false);
	}

	@Test
	public void testGetExtraInfo() {
		leader1.setGod(false);
		leader1.setUniformer(false);
		assertEquals(leader1.getExtraInfo(), null);

		leader1.setGod(true);
		assertEquals(leader1.getExtraInfo(), "adminisztrátor");

		leader1.setUniformer(true);
		leader2.setUniformer(true);
		assertEquals(leader1.getExtraInfo(), "adminisztrátor, cserkészingfelelős");
		assertEquals(leader2.getExtraInfo(), "cserkészingfelelős");
	}

	@Test
	public void testIsA() {
		leader1.setGod(true);
		leader2.setUniformer(true);

		assertEquals(leader1.isAUniformer(), true); // isAGod also tested
		assertEquals(leader2.isAUniformer(), true);
		assertEquals(leader3.isAUniformer(), false);
	}

}
