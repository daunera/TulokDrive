package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.tobias.entities.enums.Status;
import hu.tobias.mocks.ContextMocker;

public class PatrolTest {

	private Patrol patrol1;
	private Patrol patrol2;
	private FacesContext context;

	@Before
	public void before() {
		context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		when(context.getExternalContext()).thenReturn(ext);
		when(ext.getRequestContextPath()).thenReturn("http://tulokdrive.com:8080");

		patrol1 = new Patrol();
		patrol1.setId(1);

		patrol2 = new Patrol();
	}

	@Test
	public void testEquals() {
		assertEquals(patrol2.equals(patrol1), false);
		patrol2.setId(1);
		assertEquals(patrol1.equals(patrol2), true);
		patrol2.setId(2);
		assertEquals(patrol1.equals(patrol2), false);
		assertEquals(patrol1.equals(new Fee()), false);
	}

	@Test
	public void testGetPatrolUrl() {
		assertEquals(patrol1.getPatrolUrl(), "http://tulokdrive.com:8080/patrol/1");
		assertEquals(patrol2.getPatrolUrl(), "http://tulokdrive.com:8080/patrol/null");
	}

	@Test
	public void testGetLeaderNum() {
		patrol1.getLeaders().add(new Leader());

		assertEquals(patrol1.getLeaderNum(), 1);
		assertEquals(patrol2.getLeaderNum(), 0);
	}

	@Test
	public void testGetActiveNum() {
		Scout s1 = new Scout();
		s1.setStatus(Status.ACTIVE);
		patrol1.getScouts().add(s1);
		Scout s2 = new Scout();
		s2.setStatus(Status.INACTIVE);
		patrol1.getScouts().add(s2);

		assertEquals(patrol1.getActiveNum(), 1);
		assertEquals(patrol2.getActiveNum(), 0);
	}

	@Test
	public void testGetActualClass() {
		patrol1.setStartclass(10);
		assertEquals(patrol1.getActualClass(), 10);

		assertEquals(patrol2.getActualClass(), 2);
		patrol2.setBirthdate(null);
		assertEquals(patrol2.getActualClass(), 0);
	}

}
