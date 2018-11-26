package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.tobias.mocks.ContextMocker;

public class EmailTest {

	private Email email1;
	private Email email2;
	private Email email3;
	private FacesContext context;

	@Before
	public void before() {
		context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		when(context.getExternalContext()).thenReturn(ext);
		when(ext.getRequestScheme()).thenReturn("http");
		when(ext.getRequestServerName()).thenReturn("tulokdrive.com");
		when(ext.getRequestServerPort()).thenReturn(8080);
		when(ext.getRequestContextPath()).thenReturn("/mailtest");

		email1 = new Email();
		email2 = new Email("address@mail.com", "Name Tom", "Subject", "Title", "New Line");
		email3 = new Email("address@mail.com", "Name Tom", "Subject", "Title", "Nem Line", "linkend");
	}

	@After
	public void after() {
		context.release();
	}

	@Test
	public void testConstructor() {
		assertEquals(email1.getLink(), "http://tulokdrive.com:8080/mailtest");

		assertEquals(email2.getToAddress(), "address@mail.com");
		assertEquals(email2.getToName(), "Name Tom");
		assertEquals(email2.getSubject(), "Subject");
		assertEquals(email2.getTitle(), "Title");
		assertEquals(email2.getLink(), "http://tulokdrive.com:8080/mailtest");
		assertEquals(email2.getLines().get(0), "New Line");
		assertEquals(email2.getLines().size(), 1);

		assertEquals(email3.getLink(), "http://tulokdrive.com:8080/mailtest/linkend");
	}

	@Test
	public void testSetFullLink() {
		email3.setFullLink("tulokdrive.com/fulllink");
		assertEquals(email3.getLink(), "tulokdrive.com/fulllink");
	}
	
	@Test
	public void testAddLine() {
		email3.setLines(new ArrayList<String>());
		email3.addLine("lineone");
		assertEquals(email3.getLines().get(0), "lineone");
		assertEquals(email3.getLines().size(), 1);
	}

}
