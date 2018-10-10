package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Qualification;

public class QualificationComparator implements Comparator<Qualification> {

	@Override
	public int compare(Qualification o1, Qualification o2) {
		return o1.getType().compareTo(o2.getType());
	}

}
