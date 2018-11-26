package hu.tobias.beans;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import hu.tobias.entities.Address;
import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.AddressDao;
import hu.tobias.services.dao.PersonDao;

public class AddressBeanTest {

	private AddressBean addressBean;
	private List<Address> addresses = new ArrayList<Address>();
	private Address addressDb = new Address();
	private Person personDb = new Person();
	private Scout scout;
	private int runFlag = 0;

	private Runnable runabbleStub = new Runnable() {

		@Override
		public void run() {
			runFlag++;
		}
	};

	@Before
	public void before() {
		PersonDao mockPersonDao = mock(PersonDao.class);
		when(mockPersonDao.update(any())).then(new Answer<Person>() {

			@Override
			public Person answer(InvocationOnMock arg0) {
				Person p = arg0.getArgument(0);
				personDb = p;
				return p;
			}
		});

		AddressDao mockAddressDao = mock(AddressDao.class);
		when(mockAddressDao.update(any())).then(new Answer<Address>() {

			@Override
			public Address answer(InvocationOnMock arg0) {
				Address a = arg0.getArgument(0);
				addressDb = a;
				return a;
			}
		});
		doAnswer(new Answer<Address>() {

			@Override
			public Address answer(InvocationOnMock arg0) throws Throwable {
				Address a = arg0.getArgument(0);
				a.setId(1);
				addressDb = a;
				return a;
			}
		}).when(mockAddressDao).create(any());
		when(mockAddressDao.findAll()).thenReturn(addresses);

		addressBean = new AddressBean(mockPersonDao, mockAddressDao);

		scout = new Scout();
		scout.setId(1);
		scout.setPerson(new Person());
	}

	@Test
	public void testSetForModal() {
		addressBean.setForModal(scout);
		assertEquals(addressBean.getSelectedAddress().getId(), null);
		assertEquals(addressBean.getAddresses().equals(addresses), true);
		assertEquals(addressBean.getEditedScout().equals(scout), true);

		Address a1 = new Address();
		a1.setId(2);
		addresses.add(a1);
		addressBean.setForModal(scout);
		assertEquals(addressBean.getSelectedAddress().getId().intValue(), a1.getId().intValue());

		a1.setId(3);
		scout.getPerson().setAddress(a1);
		addressBean.setForModal(scout);
		assertEquals(addressBean.getSelectedAddress().getId().intValue(), a1.getId().intValue());
	}

	@Test
	public void testSaveAddress() {
		runFlag = 0;

		addressBean.saveAddress(scout, new Address(), runabbleStub);
		assertEquals(addressDb.getId().intValue(), 1);
		assertEquals(personDb.getAddress().getId().intValue(), 1);
		assertEquals(runFlag, 1);

		Address a2 = new Address();
		a2.setId(2);
		addressBean.saveAddress(scout, a2, runabbleStub);
		assertEquals(addressDb.getId().intValue(), 2);
	}

	@Test
	public void testDeleteAddress() {
		runFlag = 0;
		scout.getPerson().setAddress(new Address());

		addressBean.deleteAddress(scout, runabbleStub);
		assertEquals(personDb.getAddress(), null);
		assertEquals(runFlag, 1);
		
		addressBean.deleteAddress(scout, runabbleStub);
		assertEquals(personDb.getAddress(), null);
		assertEquals(runFlag, 2);
	}

}
