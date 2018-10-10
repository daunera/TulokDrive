package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.UniformRent;

@Stateless
public class UniformRentDao extends AbstractDao<UniformRent, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;
	
	public UniformRentDao() {
		super(UniformRent.class);
	}
	
	@Override
	public EntityManager em() {
		return em;
	}

}
