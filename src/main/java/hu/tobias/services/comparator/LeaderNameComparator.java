package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Leader;

public class LeaderNameComparator implements Comparator<Leader> {

	@Override
	public int compare(Leader o1, Leader o2) {
		PersonNameComparator comparator = new PersonNameComparator();
		return comparator.compare(o1.getScout().getPerson(), o2.getScout().getPerson());
	}

}
