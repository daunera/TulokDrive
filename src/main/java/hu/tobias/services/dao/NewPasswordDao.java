package hu.tobias.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.tobias.entities.NewPassword;

@Stateless
public class NewPasswordDao extends AbstractDao<NewPassword, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public NewPasswordDao() {
		super(NewPassword.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}

	public NewPassword findLine(String code) {
		return em.createQuery("SELECT n FROM NewPassword n WHERE code=:c", NewPassword.class)
				.setParameter("c", code)
				.getSingleResult();
	}

}
