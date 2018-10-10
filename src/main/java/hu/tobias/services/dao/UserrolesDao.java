package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.Userroles;

@Stateless
public class UserrolesDao extends AbstractDao<Userroles, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public UserrolesDao() {
		super(Userroles.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}

}
