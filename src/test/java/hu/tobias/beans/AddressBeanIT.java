package hu.tobias.beans;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import hu.tobias.entities.Address;
import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.dao.AddressDao;
import hu.tobias.services.dao.PersonDao;
import hu.tobias.services.dao.ScoutDao;

@RunWith(Arquillian.class)
public class AddressBeanIT {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addPackage(AddressDao.class.getPackage()).addPackage(Address.class.getPackage())
				.addPackage(NotFoundEntityException.class.getPackage()).addPackage(Gender.class.getPackage())
				.addClass(AddressBean.class);
	}

	@EJB
	private AddressDao addressService;
	@EJB
	private PersonDao personService;
	@EJB
	private ScoutDao scoutService;

	private AddressBean addressBean;

	private Person person;
	private Scout scout;
	private Address address;

	private int runFlag = 0;
	private Runnable runnableStub = new Runnable() {

		@Override
		public void run() {
			runFlag++;
		}
	};

	@Before
	public void before() {
		addressBean = new AddressBean(personService, addressService);
	}

	@Test
	public void testSetForModal() {
		initData();

		addressBean.setForModal(scout);

		assertEquals(addressBean.getAddresses().size(), 2);
		assertEquals(addressBean.getEditedScout().getId().intValue(), scout.getId().intValue());
		assertEquals(addressBean.getSelectedAddress().getStreet(), "Szezam utca");
	}

	@Test
	public void testSaveAddress() {
		initData();

		Address newA = new Address();
		newA.setStreet("Ujcim utca");
		addressBean.saveAddress(scout, newA, runnableStub);
		assertEquals(runFlag, 1);
		Address returnA = new Address();
		Person returnP = new Person();
		try {
			returnA = addressService.findById(newA.getId());
			returnP = personService.findById(scout.getPerson().getId());
		} catch (NotFoundEntityException e) {
			e.printStackTrace();
		}
		assertEquals(returnA.getId().intValue(), returnP.getAddress().getId().intValue());

		addressBean.saveAddress(scout, address, runnableStub);
		assertEquals(runFlag, 2);
		Person returnP2 = new Person();
		try {
			returnP2 = personService.findById(scout.getPerson().getId());
		} catch (NotFoundEntityException e) {
			e.printStackTrace();
		}
		assertEquals(address.getId().intValue(), returnP2.getAddress().getId().intValue());
	}

	@Test
	public void testDeleteAddress() {
		initData();

		person.setAddress(address);
		personService.update(person);

		addressBean.deleteAddress(scout, runnableStub);
		assertEquals(runFlag, 1);
		assertEquals(scout.getPerson().getAddress(), null);
		assertEquals(person.getAddress(), null);

	}

	private void initData() {
		for (Scout tmp : scoutService.findAll()) {
			scoutService.delete(tmp);
		}
		for (Person tmp : personService.findAll()) {
			personService.delete(tmp);
		}
		for (Address tmp : addressService.findAll()) {
			addressService.delete(tmp);
		}

		address = new Address();
		address.setStreet("Szezam utca");
		addressService.create(address);

		Address a2 = new Address();
		a2.setStreet("Elm utca");
		addressService.create(a2);

		person = new Person();
		person.setLastname("Kis");
		person.setFirstname("Bela");
		personService.create(person);

		scout = new Scout();
		scout.setPerson(person);
		scoutService.create(scout);

		runFlag = 0;
	}

}
