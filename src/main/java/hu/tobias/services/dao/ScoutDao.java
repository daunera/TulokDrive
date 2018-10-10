package hu.tobias.services.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.tobias.entities.Scout;

@Stateless
public class ScoutDao extends AbstractDao<Scout, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public ScoutDao() {
		super(Scout.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}
	
	public List<Scout> findAllExecptParamList(List<Scout> list) {
		TypedQuery<Scout> query = em
				.createQuery("SELECT s FROM Scout s WHERE s NOT IN (:list)", Scout.class)
				.setParameter("list", list);
		return query.getResultList();
	}
}
