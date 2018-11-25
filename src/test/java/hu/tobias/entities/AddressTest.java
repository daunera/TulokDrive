package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AddressTest {
	
	private Address address1;
	private Address address2;
	private Address address3;
	private Address address4;
	
	@Before
	public void before() {
		address1 = new Address();
		address1.setId(1);
		address1.setPostcode("1111");
		address1.setStreet("Kis utca 1");
		address1.setOther("1. emelet");
		address1.setPhone("0611234567");
		
		address2 = new Address();
		address2.setId(1);
		
		address3 = new Address();
		address3.setId(2);
		
		address4 = new Address();
	}
	
	@Test
	public void testEquals() {
		assertEquals(address1.equals(address2), true);
		assertEquals(address1.equals(address3), false);
		assertEquals(address4.equals(address1), false);
		assertEquals(address4.equals(new Fee()), false);
	}
	
	@Test
	public void testGetFullAddress() {
		assertEquals(address1.getFullAddress(), "Magyarország, 1111 Budapest, Kis utca 1, 1. emelet");
		assertEquals(address2.getFullAddress(), "Magyarország, null Budapest, null");
	}
	
	@Test
	public void testGetFullAddressWtihPhone() {
		assertEquals(address1.getFullAddressWithPhone(), "Magyarország, 1111 Budapest, Kis utca 1, 1. emelet, 0611234567");
		assertEquals(address2.getFullAddressWithPhone(), "Magyarország, null Budapest, null");
		assertEquals(address4.getFullAddressWithPhone(), "Új cím hozzáadása");
	}

}
