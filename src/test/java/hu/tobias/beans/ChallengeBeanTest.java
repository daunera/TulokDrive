package hu.tobias.beans;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import hu.tobias.entities.Challenge;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.ChallengeDao;

public class ChallengeBeanTest {

	private ChallengeBean challengeBean;
	private Challenge challengeDb = new Challenge();
	private Scout scout;
	private int runFlag = 0;

	private Runnable runnableStub = new Runnable() {

		@Override
		public void run() {
			runFlag++;
		}
	};

	@Before
	public void before() {
		ChallengeDao mockChallengeDao = mock(ChallengeDao.class);

		doAnswer(new Answer<Challenge>() {

			@Override
			public Challenge answer(InvocationOnMock arg0) throws Throwable {
				Challenge c = arg0.getArgument(0);
				c.setId(10);
				challengeDb = c;
				return c;
			}
		}).when(mockChallengeDao).create(any());
		doAnswer(new Answer<Challenge>() {

			@Override
			public Challenge answer(InvocationOnMock arg0) throws Throwable {
				Challenge c = arg0.getArgument(0);
				challengeDb = c;
				return c;
			}
		}).when(mockChallengeDao).update(any());
		doAnswer(new Answer<Challenge>() {

			@Override
			public Challenge answer(InvocationOnMock arg0) throws Throwable {
				challengeDb = null;
				return null;
			}
		}).when(mockChallengeDao).delete(any());

		challengeBean = new ChallengeBean(mockChallengeDao);
		scout = new Scout();
		scout.setId(1);
	}

	@Test
	public void testSetForEditModal() {
		Challenge c1 = new Challenge(scout);
		c1.setId(1);

		challengeBean.setForEditModal(c1);
		assertEquals(challengeBean.getSelectedChallenge().equals(c1), true);
	}

	@Test
	public void testSetForNewModal() {
		challengeBean.setForNewModal(scout);
		assertEquals(challengeBean.getNewChallenge().getScout().equals(scout), true);
	}

	@Test
	public void testSaveChallenge() {
		runFlag = 0;
		challengeBean.saveChallenge(new Challenge(), runnableStub);
		assertEquals(challengeDb.getId().intValue(), 10);
		assertEquals(runFlag, 1);

		Challenge c2 = new Challenge();
		c2.setId(11);
		challengeBean.saveChallenge(c2, runnableStub);
		assertEquals(challengeDb.getId().intValue(), 11);
		assertEquals(runFlag, 2);
	}

	@Test
	public void testDeleteChallenge() {
		runFlag = 0;
		challengeBean.deleteChallenge(null, runnableStub);
		assertEquals(challengeDb, null);
		assertEquals(runFlag, 1);
	}

}
