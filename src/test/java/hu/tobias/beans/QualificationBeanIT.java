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

import hu.tobias.entities.Qualification;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.dao.QualificationDao;
import hu.tobias.services.dao.ScoutDao;

@RunWith(Arquillian.class)
public class QualificationBeanIT {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addPackage(QualificationDao.class.getPackage()).addPackage(Qualification.class.getPackage())
				.addPackage(NotFoundEntityException.class.getPackage()).addPackage(Gender.class.getPackage())
				.addClass(QualificationBean.class);
	}

	@EJB
	private QualificationDao qualificationService;

	@EJB
	private ScoutDao scoutService;

	private QualificationBean qualificationBean;

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

		qualificationBean = new QualificationBean(qualificationService);
	}

	@Test
	public void testSaveQualification() throws NotFoundEntityException {
		runFlag = 0;

		scout.getQualifications().clear();
		scoutService.update(scout);

		Qualification toCreate = new Qualification(scout);
		qualificationBean.saveQualification(toCreate, runnableStub);
		assertEquals(runFlag, 1);
		assertEquals(toCreate.getId().intValue(), qualificationService.findById(toCreate.getId()).getId().intValue());
		Scout tmp = scoutService.findById(scout.getId());
		assertEquals(tmp.getQualifications().size(), 1);

		Qualification toUpdate = new Qualification();
		qualificationService.create(toUpdate);

		toUpdate.setScout(scout);
		qualificationBean.saveQualification(toUpdate, runnableStub);
		assertEquals(runFlag, 2);
		assertEquals(qualificationService.findById(toUpdate.getId()).getScout().getId().intValue(),
				scout.getId().intValue());
		Scout tmp2 = scoutService.findById(scout.getId());
		assertEquals(tmp2.getQualifications().size(), 2);
	}

	@Test
	public void testDeleteQualification() throws NotFoundEntityException {
		runFlag = 0;

		scout.getQualifications().clear();
		scoutService.update(scout);

		Qualification toDelete = new Qualification(scout);
		qualificationService.create(toDelete);

		qualificationBean.deleteQualification(toDelete, runnableStub);
		assertEquals(runFlag, 1);
		boolean error = false;
		try {
			qualificationService.findById(toDelete.getId());
		} catch (NotFoundEntityException e) {
			error = true;
		}
		assertEquals(error, true);
		Scout tmp = scoutService.findById(scout.getId());
		assertEquals(tmp.getQualifications().size(), 0);
	}

}
