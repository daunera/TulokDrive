package hu.tobias.services.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.tobias.entities.News;

@Stateless
public class NewsDao extends AbstractDao<News, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public NewsDao() {
		super(News.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}
	
	public List<News> findActualNews() {
		TypedQuery<News> query = em
				.createQuery("SELECT n FROM News n WHERE expire > now() ORDER BY expire", News.class);
		return query.getResultList();
	}

}
