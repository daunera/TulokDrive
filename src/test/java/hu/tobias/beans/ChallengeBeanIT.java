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

import hu.tobias.entities.Challenge;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.exceptions.NotFoundEntityException;
import hu.tobias.services.dao.ChallengeDao;
import hu.tobias.services.dao.ScoutDao;

@RunWith(Arquillian.class)
public class ChallengeBeanIT {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addPackage(ChallengeDao.class.getPackage()).addPackage(Challenge.class.getPackage())
				.addPackage(NotFoundEntityException.class.getPackage()).addPackage(Gender.class.getPackage())
				.addClass(ChallengeBean.class);
	}

	@EJB
	private ChallengeDao challengeService;

	@EJB
	private ScoutDao scoutService;

	private ChallengeBean challengeBean;

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

		challengeBean = new ChallengeBean(challengeService);
	}

	@Test
	public void testSaveChallenge() throws NotFoundEntityException {
		runFlag = 0;

		scout.getChallenges().clear();
		scoutService.update(scout);

		Challenge toCreate = new Challenge(scout);
		challengeBean.saveChallenge(toCreate, runnableStub);
		assertEquals(runFlag, 1);
		assertEquals(toCreate.getId().intValue(), challengeService.findById(toCreate.getId()).getId().intValue());
		Scout tmp = scoutService.findById(scout.getId());
		assertEquals(tmp.getChallenges().size(), 1);

		Challenge toUpdate = new Challenge();
		challengeService.create(toUpdate);

		toUpdate.setScout(scout);
		challengeBean.saveChallenge(toUpdate, runnableStub);
		assertEquals(runFlag, 2);
		assertEquals(challengeService.findById(toUpdate.getId()).getScout().getId().intValue(),
				scout.getId().intValue());
		Scout tmp2 = scoutService.findById(scout.getId());
		assertEquals(tmp2.getChallenges().size(), 2);
	}

	@Test
	public void testDeleteChallenge() throws NotFoundEntityException {
		runFlag = 0;

		scout.getChallenges().clear();
		scoutService.update(scout);

		Challenge toDelete = new Challenge(scout);
		challengeService.create(toDelete);

		challengeBean.deleteChallenge(toDelete, runnableStub);
		assertEquals(runFlag, 1);
		boolean error = false;
		try {
			challengeService.findById(toDelete.getId());
		} catch (NotFoundEntityException e) {
			error = true;
		}
		assertEquals(error, true);
		Scout tmp = scoutService.findById(scout.getId());
		assertEquals(tmp.getChallenges().size(), 0);
	}

}
