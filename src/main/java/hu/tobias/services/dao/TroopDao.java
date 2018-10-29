package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.Troop;

@Stateless
public class TroopDao extends AbstractDao<Troop, Integer> {
	
	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public TroopDao() {
		super(Troop.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}

}
