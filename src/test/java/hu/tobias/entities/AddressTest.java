package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AddressTest {

	private Address address1;
	private Address address2;

	@Before
	public void before() {
		address1 = new Address();
		address1.setId(1);
		address1.setPostcode("1111");
		address1.setStreet("Kis utca 1");
		address1.setOther("1. emelet");
		address1.setPhone("0611234567");

		address2 = new Address();
		address2.setId(2);
	}

	@Test
	public void testEquals() {
		address2.setId(null);
		assertEquals(address2.equals(address1), false);
		address2.setId(1);
		assertEquals(address2.equals(address1), true);
		address2.setId(2);
		assertEquals(address2.equals(address1), false);
		assertEquals(address2.equals(new Fee()), false);
	}

	@Test
	public void testGetFullAddress() {
		assertEquals(address1.getFullAddress(), "Magyarország, 1111 Budapest, Kis utca 1, 1. emelet");
		assertEquals(address2.getFullAddress(), "Magyarország, null Budapest, null");
	}

	@Test
	public void testGetFullAddressWtihPhone() {
		assertEquals(address1.getFullAddressWithPhone(),
				"Magyarország, 1111 Budapest, Kis utca 1, 1. emelet, 0611234567");
		assertEquals(address2.getFullAddressWithPhone(), "Magyarország, null Budapest, null");
		address2.setId(null);
		assertEquals(address2.getFullAddressWithPhone(), "Új cím hozzáadása");
	}

}
