package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Troop;

public class TroopNameComparator implements Comparator<Troop> {

	@Override
	public int compare(Troop o1, Troop o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
