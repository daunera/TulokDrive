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

import hu.tobias.entities.Fee;
import hu.tobias.entities.FeeTypeTable;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.FeeType;
import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.dao.FeeDao;
import hu.tobias.services.dao.FeeTypeTableDao;
import hu.tobias.services.dao.ScoutDao;

@RunWith(Arquillian.class)
public class FeeBeanIT {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addPackage(FeeDao.class.getPackage()).addPackage(Fee.class.getPackage())
				.addPackage(NotFoundEntityException.class.getPackage()).addPackage(Gender.class.getPackage())
				.addClass(FeeBean.class);
	}

	@EJB
	private FeeDao feeService;
	@EJB
	private FeeTypeTableDao feeTypeService;

	@EJB
	private ScoutDao scoutService;

	private FeeBean feeBean;

	private Scout scout;
	private FeeTypeTable ftt;

	private int runFlag = 0;
	private Runnable runnableStub = new Runnable() {

		@Override
		public void run() {
			runFlag++;
		}
	};

	@Before
	public void before() {
		scout = new Scout();
		scoutService.create(scout);

		ftt = new FeeTypeTable();
		ftt.setAmount(3333);
		ftt.setType(FeeType.CSAPAT);
		feeTypeService.create(ftt);

		feeBean = new FeeBean(feeService, feeTypeService);
	}

	@Test
	public void testSetForNewModal() {
		feeBean.setForNewModal(scout, 2010);

		assertEquals(feeBean.getNewFee().getAmount().intValue(), 3333);
	}

	@Test
	public void testSaveFee() throws NotFoundEntityException {
		runFlag = 0;
		Fee newFee = new Fee();
		newFee.setScout(scout);

		feeBean.saveFee(newFee, runnableStub);
		assertEquals(runFlag, 1);
		assertEquals(newFee.getId().intValue(), feeService.findById(newFee.getId()).getId().intValue());

		Fee nextFee = new Fee();
		feeService.create(nextFee);
		nextFee.setScout(scout);
		feeBean.saveFee(newFee, runnableStub);
		assertEquals(runFlag, 2);
		assertEquals(nextFee.getId().intValue(), feeService.findById(nextFee.getId()).getId().intValue());
	}

	@Test
	public void testDeleteFee() {
		runFlag = 0;

		Fee toDeleteFee = new Fee();
		feeService.create(toDeleteFee);

		int toDeleteFeeId = toDeleteFee.getId().intValue();

		feeBean.deleteFee(toDeleteFee, runnableStub);
		assertEquals(runFlag, 1);
		boolean error = false;
		try {
			feeService.findById(toDeleteFeeId);
		} catch (NotFoundEntityException e) {
			error = true;
		}
		assertEquals(error, true);
	}

}
