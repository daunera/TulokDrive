package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.Challenge;

@Stateless
public class ChallengeDao extends AbstractDao<Challenge, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public ChallengeDao() {
			super(Challenge.class);
		}

	@Override
	public EntityManager em() {
		return em;
	}

}
