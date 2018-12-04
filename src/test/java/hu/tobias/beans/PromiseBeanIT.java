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

import hu.tobias.entities.Promise;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.dao.PromiseDao;
import hu.tobias.services.dao.ScoutDao;

@RunWith(Arquillian.class)
public class PromiseBeanIT {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addPackage(PromiseDao.class.getPackage()).addPackage(Promise.class.getPackage())
				.addPackage(NotFoundEntityException.class.getPackage()).addPackage(Gender.class.getPackage())
				.addClass(PromiseBean.class);
	}

	@EJB
	private PromiseDao promiseService;

	@EJB
	private ScoutDao scoutService;

	private PromiseBean promiseBean;

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
		scout = new Scout();
		scoutService.create(scout);

		promiseBean = new PromiseBean(promiseService);
	}

	@Test
	public void testSavePromise() throws NotFoundEntityException {
		runFlag = 0;

		scout.getPromises().clear();
		scoutService.update(scout);

		Promise toCreate = new Promise(scout);
		promiseBean.savePromise(toCreate, runnableStub);
		assertEquals(runFlag, 1);
		assertEquals(toCreate.getId().intValue(), promiseService.findById(toCreate.getId()).getId().intValue());
		Scout tmp = scoutService.findById(scout.getId());
		assertEquals(tmp.getPromises().size(), 1);

		Promise toUpdate = new Promise();
		promiseService.create(toUpdate);

		toUpdate.setScout(scout);
		promiseBean.savePromise(toUpdate, runnableStub);
		assertEquals(runFlag, 2);
		assertEquals(promiseService.findById(toUpdate.getId()).getScout().getId().intValue(), scout.getId().intValue());
		Scout tmp2 = scoutService.findById(scout.getId());
		assertEquals(tmp2.getPromises().size(), 2);
	}

	@Test
	public void testDeletePromise() throws NotFoundEntityException {
		runFlag = 0;

		scout.getPromises().clear();
		scoutService.update(scout);

		Promise toDelete = new Promise(scout);
		promiseService.create(toDelete);

		promiseBean.deletePromise(toDelete, runnableStub);
		assertEquals(runFlag, 1);
		boolean error = false;
		try {
			promiseService.findById(toDelete.getId());
		} catch (NotFoundEntityException e) {
			error = true;
		}
		assertEquals(error, true);
		Scout tmp = scoutService.findById(scout.getId());
		assertEquals(tmp.getPromises().size(), 0);
	}

}
