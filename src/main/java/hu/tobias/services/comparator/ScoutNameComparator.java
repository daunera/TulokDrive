package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Scout;

public class ScoutNameComparator implements Comparator<Scout> {

	@Override
	public int compare(Scout o1, Scout o2) {
		PersonNameComparator comparator = new PersonNameComparator();
		return comparator.compare(o1.getPerson(), o2.getPerson());
	}

}
