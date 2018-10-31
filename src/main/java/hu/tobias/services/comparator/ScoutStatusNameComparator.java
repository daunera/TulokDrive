package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Scout;

public class ScoutStatusNameComparator implements Comparator<Scout> {

	@Override
	public int compare(Scout o1, Scout o2) {
		int result = o1.getStatus().compareTo(o2.getStatus());
		if(result == 0){
			PersonNameComparator comparator = new PersonNameComparator();
			return comparator.compare(o1.getPerson(), o2.getPerson());
		} else {
			return result;
		}
	}

}
