package hu.tobias.beans;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.tobias.entities.Address;
import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.AddressDao;
import hu.tobias.services.dao.PersonDao;

public class AddressBeanTest {

	private AddressBean addressBean;

	@Before
	public void before() {
		PersonDao mockPersonDao = Mockito.mock(PersonDao.class);
		Mockito.when(mockPersonDao.update(Mockito.any())).thenReturn(new Person());
		
		AddressDao mockAddressDao = Mockito.mock(AddressDao.class);
		Mockito.when(mockAddressDao.findAll()).thenReturn(new ArrayList<Address>(){{add(new Address()); add(new Address());}});		
		
		addressBean = new AddressBean();
	}

	@After
	public void after() {

	}

	@Test
	public void testForExamle() {
		assertEquals("this", "this");
	}

	@Test
	public void modalSetting() {
//		Scout s = new Scout();
//		s.setId(5);
//
//		addressBean.setForModal(s);
//
//		assertEquals(addressBean.getEditedScout(), s);
	}

}
