package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.tobias.entities.enums.ChallengeType;
import hu.tobias.entities.enums.PromiseType;
import hu.tobias.entities.enums.QualificationType;
import hu.tobias.mocks.ContextMocker;

public class ScoutTest {

	private Scout scout1;
	private Scout scout2;
	private FacesContext context;

	@Before
	public void before() {
		context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		when(context.getExternalContext()).thenReturn(ext);
		when(ext.getRequestContextPath()).thenReturn("http://tulokdrive.com:8080");

		scout1 = new Scout();
		scout1.setId(1);
		Fee f = new Fee(scout1, 2010);
		f.setId(1);
		scout1.getFees().add(f);
		Promise p = new Promise(scout1, PromiseType.FE);
		p.setId(1);
		scout1.getPromises().add(p);
		Challenge c = new Challenge(scout1, ChallengeType.KO);
		c.setId(1);
		scout1.getChallenges().add(c);
		scout1.getQualifications().add(new Qualification(scout1, QualificationType.OV));

		scout2 = new Scout();
	}

	@Test
	public void testIsALeader() {
		scout1.setLeader(new Leader());
		assertEquals(scout1.isALeader(), true);
		assertEquals(scout2.isALeader(), false);
	}

	@Test
	public void testHasFunctions() {
		assertEquals(scout2.hasChallenges(), false);
		assertEquals(scout2.hasFees(), false);
		assertEquals(scout2.hasPromises(), false);
		assertEquals(scout2.hasQualifications(), false);

		assertEquals(scout1.hasChallenges(), true);
		assertEquals(scout1.hasFees(), true);
		assertEquals(scout1.hasPromises(), true);
		assertEquals(scout1.hasQualifications(), true);
	}

	@Test
	public void testGetScoutUrl() {
		assertEquals(scout1.getScoutUrl(), "http://tulokdrive.com:8080/scout/1");
		assertEquals(scout2.getScoutUrl(), "http://tulokdrive.com:8080/scout/null");
	}

	@Test
	public void testEquals() {
		assertEquals(scout2.equals(scout1), false);
		scout2.setId(1);
		assertEquals(scout2.equals(scout1), true);
		scout2.setId(2);
		assertEquals(scout2.equals(scout1), false);
		assertEquals(scout2.equals(new Fee()), false);
		scout2.setId(null);
	}

	@Test
	public void testGetPromiseByType() {
		Promise p = new Promise();
		p.setId(1);
		assertEquals(scout1.getPromiseByType("FE").equals(p), true);
		assertEquals(scout2.getPromiseByType("FE"), null);
	}

	@Test
	public void testHasEveryPromise() {
		assertEquals(scout1.hasEveryPromise(), false);
		scout1.getPromises().add(new Promise(scout1, PromiseType.CS));
		scout1.getPromises().add(new Promise(scout1, PromiseType.KCS));
		scout1.getPromises().add(new Promise(scout1, PromiseType.TISZTI));
		assertEquals(scout1.hasEveryPromise(), true);
	}

	@Test
	public void testGetChallengeByType() {
		Challenge c = new Challenge();
		c.setId(1);
		assertEquals(scout1.getChallengeByType("KO").equals(c), true);
		assertEquals(scout2.getChallengeByType("KO"), null);
	}

	@Test
	public void testHasEveryChallenge() {
		assertEquals(scout1.hasEveryChallenge(), false);
		for (ChallengeType ct : ChallengeType.values()) {
			scout1.getChallenges().add(new Challenge(scout1, ct));
		}
		assertEquals(scout1.hasEveryChallenge(), true);
	}

	@Test
	public void testGetFeeByYear() {
		Fee f = new Fee(scout1, 2010);
		f.setId(1);
		assertEquals(scout1.getFeeByYear(2011), null);
		assertEquals(scout1.getFeeByYear(2010).equals(f), true);
	}

}
