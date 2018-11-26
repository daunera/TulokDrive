package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.tobias.entities.enums.Status;
import hu.tobias.mocks.ContextMocker;

public class TroopTest {

	private Troop troop1;
	private Troop troop2;
	private FacesContext context;

	@Before
	public void before() {
		context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		when(context.getExternalContext()).thenReturn(ext);
		when(ext.getRequestContextPath()).thenReturn("http://tulokdrive.com:8080");

		troop1 = new Troop();
		troop1.setId(1);

		troop1.getLeaders().add(new Leader(1));
		troop1.getLeaders().add(new Leader(3));

		Patrol p1 = new Patrol();
		p1.getLeaders().add(new Leader(1));
		p1.getLeaders().add(new Leader(2));

		Scout s1 = new Scout();
		s1.setId(1);
		p1.getScouts().add(s1);
		p1.getScouts().add(new Scout());
		p1.getScouts().add(new Scout());

		Patrol p2 = new Patrol();
		p2.getLeaders().add(new Leader(2));
		p2.getScouts().add(new Scout());
		Scout s2 = new Scout();
		s2.setStatus(Status.INACTIVE);
		p1.getScouts().add(s2);

		troop1.getPatrols().add(p1);
		troop1.getPatrols().add(p2);

		troop2 = new Troop();
	}

	@Test
	public void testEquals() {
		assertEquals(troop2.equals(troop1), false);
		troop2.setId(1);
		assertEquals(troop2.equals(troop1), true);
		troop2.setId(2);
		assertEquals(troop2.equals(troop1), false);
		assertEquals(troop2.equals(new Fee()), false);
		troop2.setId(null);
	}

	@Test
	public void testGetTroopUrl() {
		assertEquals(troop1.getTroopUrl(), "http://tulokdrive.com:8080/troop/1");
		assertEquals(troop2.getTroopUrl(), "http://tulokdrive.com:8080/troop/null");
	}

	@Test
	public void testGetLeaderNum() {
		assertEquals(troop1.getLeaderNum(), 2);
		assertEquals(troop2.getLeaderNum(), 0);
	}

	@Test
	public void testGetPatrolLeader() {
		assertEquals(troop1.getPatrolLeaderNum(), 2); // getPatrolLeader is lefut és visszaadja a patrol's leaderket
		assertEquals(troop2.getPatrolLeaderNum(), 0);
	}

	@Test
	public void testGetAllLeader() {
		assertEquals(troop1.getAllLeaderNum(), 3); // getAllLeader és getPatrolLeader is lefut és visszaadja a leadereket
		assertEquals(troop2.getAllLeaderNum(), 0);
	}

	@Test
	public void testGetActiveNum() {
		assertEquals(troop1.getActiveNum(), 4);
		assertEquals(troop2.getActiveNum(), 0);
	}

	@Test
	public void testGetScouts() {
		assertEquals(troop1.getScouts().size(), 5);
		assertEquals(troop2.getScouts().size(), 0);
	}

}
