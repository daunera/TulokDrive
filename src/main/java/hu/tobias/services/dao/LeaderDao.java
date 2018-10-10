package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.Leader;

@Stateless
public class LeaderDao extends AbstractDao<Leader, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public LeaderDao() {
		super(Leader.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}

	public String findPWbyUsername(String username) {
		String pw = (String) em.createQuery("SELECT password FROM Leader WHERE username=:value")
				.setParameter("value", username).getSingleResult();
		return pw;
	}

	public Leader findUserByUsername(String username) {
		return em.createQuery("SELECT l FROM Leader l WHERE username=:value", Leader.class)
				.setParameter("value", username)
				.getSingleResult();
	}
	public Leader findUser(String username, String password) {
		return em.createQuery("SELECT l FROM Leader l WHERE username=:un AND password=:pw",Leader.class)
				.setParameter("un", username)
				.setParameter("pw", password)
				.getSingleResult();	
	}

}
