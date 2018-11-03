package hu.tobias.services.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	
	public List<Patrol> findAllWithoutTroop() {
		TypedQuery<Patrol> query = em.createQuery("SELECT p FROM Patrol p WHERE p.troop IS EMPTY", Patrol.class);
		return query.getResultList();
	}

}
