package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.Promise;

@Stateless
public class PromiseDao extends AbstractDao<Promise, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public PromiseDao() {
		super(Promise.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}
}
