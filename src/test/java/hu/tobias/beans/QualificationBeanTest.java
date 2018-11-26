package hu.tobias.beans;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import hu.tobias.entities.Qualification;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.QualificationDao;

public class QualificationBeanTest {

	private QualificationBean qualificationBean;
	private Qualification qualificationDb = new Qualification();
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
		QualificationDao mockQualificationDao = mock(QualificationDao.class);

		doAnswer(new Answer<Qualification>() {

			@Override
			public Qualification answer(InvocationOnMock arg0) throws Throwable {
				Qualification q = arg0.getArgument(0);
				q.setId(10);
				qualificationDb = q;
				return q;
			}
		}).when(mockQualificationDao).create(any());
		doAnswer(new Answer<Qualification>() {

			@Override
			public Qualification answer(InvocationOnMock arg0) throws Throwable {
				Qualification q = arg0.getArgument(0);
				qualificationDb = q;
				return q;
			}
		}).when(mockQualificationDao).update(any());
		doAnswer(new Answer<Qualification>() {

			@Override
			public Qualification answer(InvocationOnMock arg0) throws Throwable {
				qualificationDb = null;
				return null;
			}
		}).when(mockQualificationDao).delete(any());

		qualificationBean = new QualificationBean(mockQualificationDao);
		scout = new Scout();
		scout.setId(1);
	}

	@Test
	public void testSetForEditModal() {
		Qualification q1 = new Qualification(scout);
		q1.setId(1);

		qualificationBean.setForEditModal(q1);
		assertEquals(qualificationBean.getSelectedQualification().equals(q1), true);
	}

	@Test
	public void testSetForNewModal() {
		qualificationBean.setForNewModal(scout);
		assertEquals(qualificationBean.getNewQualification().getScout().equals(scout), true);
	}

	@Test
	public void testSaveQualification() {
		runFlag = 0;
		qualificationBean.saveQualification(new Qualification(), runabbleStub);
		assertEquals(qualificationDb.getId().intValue(), 10);
		assertEquals(runFlag, 1);

		Qualification q2 = new Qualification();
		q2.setId(11);
		qualificationBean.saveQualification(q2, runabbleStub);
		assertEquals(qualificationDb.getId().intValue(), 11);
		assertEquals(runFlag, 2);
	}

	@Test
	public void testDeleteQualification() {
		runFlag = 0;
		qualificationBean.deleteQualification(null, runabbleStub);
		assertEquals(qualificationDb, null);
		assertEquals(runFlag, 1);
	}

}
