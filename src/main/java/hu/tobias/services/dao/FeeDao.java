package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.Fee;

@Stateless
public class FeeDao extends AbstractDao<Fee, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public FeeDao() {
			super(Fee.class);
		}

	@Override
	public EntityManager em() {
		return em;
	}

}
