package hu.tobias.beans;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import hu.tobias.entities.Promise;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.PromiseDao;

public class PromiseBeanTest {

	private PromiseBean promiseBean;
	private Promise promiseDb = new Promise();
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
		PromiseDao mockPromiseDao = mock(PromiseDao.class);

		doAnswer(new Answer<Promise>() {

			@Override
			public Promise answer(InvocationOnMock arg0) throws Throwable {
				Promise p = arg0.getArgument(0);
				p.setId(10);
				promiseDb = p;
				return p;
			}
		}).when(mockPromiseDao).create(any());
		doAnswer(new Answer<Promise>() {

			@Override
			public Promise answer(InvocationOnMock arg0) throws Throwable {
				Promise p = arg0.getArgument(0);
				promiseDb = p;
				return p;
			}
		}).when(mockPromiseDao).update(any());
		doAnswer(new Answer<Promise>() {

			@Override
			public Promise answer(InvocationOnMock arg0) throws Throwable {
				promiseDb = null;
				return null;
			}
		}).when(mockPromiseDao).delete(any());

		promiseBean = new PromiseBean(mockPromiseDao);
		scout = new Scout();
		scout.setId(1);
	}

	@Test
	public void testSetForEditModal() {
		Promise p1 = new Promise(scout);
		p1.setId(1);

		promiseBean.setForEditModal(p1);
		assertEquals(promiseBean.getSelectedPromise().equals(p1), true);
	}

	@Test
	public void testSetForNewModal() {
		promiseBean.setForNewModal(scout);
		assertEquals(promiseBean.getNewPromise().getScout().equals(scout), true);
	}

	@Test
	public void testSavePromise() {
		runFlag = 0;
		promiseBean.savePromise(new Promise(), runabbleStub);
		assertEquals(promiseDb.getId().intValue(), 10);
		assertEquals(runFlag, 1);

		Promise p2 = new Promise();
		p2.setId(11);
		promiseBean.savePromise(p2, runabbleStub);
		assertEquals(promiseDb.getId().intValue(), 11);
		assertEquals(runFlag, 2);
	}

	@Test
	public void testDeletePromise() {
		runFlag = 0;
		promiseBean.deletePromise(null, runabbleStub);
		assertEquals(promiseDb, null);
		assertEquals(runFlag, 1);
	}

}
