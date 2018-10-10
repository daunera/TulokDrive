package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Patrol;

public class PatrolNameComparator implements Comparator<Patrol> {

	@Override
	public int compare(Patrol o1, Patrol o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
