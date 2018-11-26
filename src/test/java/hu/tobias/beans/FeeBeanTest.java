package hu.tobias.beans;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import hu.tobias.entities.Fee;
import hu.tobias.entities.FeeTypeTable;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.FeeStatusType;
import hu.tobias.services.dao.FeeDao;
import hu.tobias.services.dao.FeeTypeTableDao;

public class FeeBeanTest {

	private FeeBean feeBean;
	private Fee feeDb = new Fee();
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
		FeeDao mockFeeDao = mock(FeeDao.class);

		doAnswer(new Answer<Fee>() {

			@Override
			public Fee answer(InvocationOnMock arg0) throws Throwable {
				Fee f = arg0.getArgument(0);
				f.setId(10);
				feeDb = f;
				return f;
			}
		}).when(mockFeeDao).create(any());
		doAnswer(new Answer<Fee>() {

			@Override
			public Fee answer(InvocationOnMock arg0) throws Throwable {
				Fee f = arg0.getArgument(0);
				feeDb = f;
				return f;
			}
		}).when(mockFeeDao).update(any());
		doAnswer(new Answer<Fee>() {

			@Override
			public Fee answer(InvocationOnMock arg0) throws Throwable {
				feeDb = null;
				return null;
			}
		}).when(mockFeeDao).delete(any());

		FeeTypeTableDao mockFeeTypeDao = mock(FeeTypeTableDao.class);

		doAnswer(new Answer<FeeTypeTable>() {

			@Override
			public FeeTypeTable answer(InvocationOnMock arg0) throws Throwable {
				FeeTypeTable ftt = new FeeTypeTable();
				ftt.setAmount(10000);
				return ftt;
			}
		}).when(mockFeeTypeDao).findActualTeamFee();

		feeBean = new FeeBean(mockFeeDao, mockFeeTypeDao);
		scout = new Scout();
		scout.setId(1);
	}

	@Test
	public void testInit() {
		feeBean.getYears().clear();
		assertEquals(feeBean.getYears().isEmpty(), true);

		feeBean.init();
		assertEquals(feeBean.getSelectedYear().intValue(), LocalDateTime.now().getYear());
		assertEquals(feeBean.getYears().size(), LocalDateTime.now().getYear() - 2016 + 1);

		feeBean.setSelectedYear(2017);
		feeBean.init();
		assertEquals(feeBean.getSelectedYear().intValue(), 2017);
	}

	@Test
	public void testSetForEditModal() {
		Fee f1 = new Fee(scout, 2010);
		f1.setId(1);

		feeBean.setForSelectedModal(f1);
		assertEquals(feeBean.getSelectedFee().equals(f1), true);
	}

	@Test
	public void testSetForNewModal() {
		feeBean.setForNewModal(scout, 2010);
		assertEquals(feeBean.getNewFee().getScout().equals(scout), true);
		assertEquals(feeBean.getNewFee().getAmount().intValue(), 10000);
		assertEquals(feeBean.getNewFee().getStatus(), FeeStatusType.OV);
	}

	@Test
	public void testSaveFee() {
		runFlag = 0;
		feeBean.saveFee(new Fee(), runabbleStub);
		assertEquals(feeDb.getId().intValue(), 10);
		assertEquals(runFlag, 1);

		Fee f2 = new Fee();
		f2.setId(11);
		feeBean.saveFee(f2, runabbleStub);
		assertEquals(feeDb.getId().intValue(), 11);
		assertEquals(runFlag, 2);
	}

	@Test
	public void testDeleteFee() {
		runFlag = 0;
		feeBean.deleteFee(null, runabbleStub);
		assertEquals(feeDb, null);
		assertEquals(runFlag, 1);
	}

}
