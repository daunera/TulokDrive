package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.FeeTypeTable;
import hu.tobias.entities.enums.FeeType;

@Stateless
public class FeeTypeTableDao extends AbstractDao<FeeTypeTable, Integer> {
	
	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public FeeTypeTableDao() {
			super(FeeTypeTable.class);
		}

	@Override
	public EntityManager em() {
		return em;
	}
	
	public FeeTypeTable findActualTeamFee() {
		return em.createQuery("SELECT f FROM FeeTypeTable f WHERE type=:value", FeeTypeTable.class)
				.setParameter("value", FeeType.CSAPAT)
				.getSingleResult();
	}

}
