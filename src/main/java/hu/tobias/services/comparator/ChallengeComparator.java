package hu.tobias.services.comparator;

import java.util.Comparator;

import hu.tobias.entities.Challenge;

public class ChallengeComparator implements Comparator<Challenge> {

	@Override
	public int compare(Challenge o1, Challenge o2) {
		return o1.getType().compareTo(o2.getType());
	}

}
