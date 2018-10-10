package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Person;

public class PersonNameComparator implements Comparator<Person> {

	@Override
	public int compare(Person o1, Person o2) {
		return o1.getFullName().compareTo(o2.getFullName());
	}

}
