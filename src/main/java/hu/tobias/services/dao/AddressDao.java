package hu.tobias.services.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import hu.tobias.entities.Address;

@Stateless
public class AddressDao extends AbstractDao<Address, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public AddressDao() {
		super(Address.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}
	
	public List<Address> findAllNameOrder() {
		CriteriaBuilder cb = em().getCriteriaBuilder();
		CriteriaQuery<Address> cq = cb.createQuery(Address.class);
		Root<Address> root = cq.from(Address.class);
		cq.select(root);
		
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get("country")));
		orderList.add(cb.asc(root.get("postcode")));
		orderList.add(cb.asc(root.get("city")));
		orderList.add(cb.asc(root.get("street")));
		
		cq.orderBy(orderList);
		
		return em().createQuery(cq).getResultList();
	}

}
