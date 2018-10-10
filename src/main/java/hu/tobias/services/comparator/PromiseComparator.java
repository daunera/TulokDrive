package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Promise;

public class PromiseComparator implements Comparator<Promise> {

	@Override
	public int compare(Promise o1, Promise o2) {
		return o1.getType().compareTo(o2.getType());
	}

}
