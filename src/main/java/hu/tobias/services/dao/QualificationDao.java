package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.Qualification;

@Stateless
public class QualificationDao extends AbstractDao<Qualification, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public QualificationDao() {
			super(Qualification.class);
		}

	@Override
	public EntityManager em() {
		return em;
	}

}
