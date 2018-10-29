package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.FeeTypeTable;

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

}
