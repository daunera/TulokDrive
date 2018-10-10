package hu.tobias.services.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.tobias.entities.Person;
import hu.tobias.entities.enums.Gender;

@Stateless
public class PersonDao extends AbstractDao<Person, Integer> {

	@PersistenceContext(name = "MemberHub")
	EntityManager em;

	public PersonDao() {
		super(Person.class);
	}

	@Override
	public EntityManager em() {
		return em;
	}

	public List<Person> findAllInOneGender(Gender g) {
		TypedQuery<Person> query = em.createQuery(
				"SELECT p FROM Person p WHERE gender IN (:gender, :notdefined) ORDER BY lastname, firstname",
				Person.class).setParameter("gender", g).setParameter("notdefined", Gender.NOTDEFINED);
		return query.getResultList();
	}

	public List<Person> findAllHappyBois() {
		
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		int oneYear = cal.isLeapYear(cal.get(GregorianCalendar.YEAR)) ? 366 :  365;
		Integer dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		String days = (++dayOfYear).toString();
		for(int i = 0; i < 14; i++) {
			if(dayOfYear > oneYear)
				dayOfYear = dayOfYear - oneYear;
			days += ", "+((++dayOfYear).toString());
		}
		
		TypedQuery<Person> query = em.createQuery(
				"SELECT p FROM Person p WHERE dayofyear(p.birthdate) IN ("+days+") ORDER BY dayofyear(p.birthdate)",
				Person.class);
		return query.getResultList();
	}

}
