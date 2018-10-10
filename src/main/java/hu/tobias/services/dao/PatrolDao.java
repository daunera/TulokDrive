package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.Patrol;

@Stateless
public class PatrolDao extends AbstractDao<Patrol, Integer> {
	
	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public PatrolDao() {
		super(Patrol.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}

}
