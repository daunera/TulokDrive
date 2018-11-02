package hu.tobias.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import hu.tobias.entities.Leader;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Scout;
import hu.tobias.entities.Troop;
import hu.tobias.entities.enums.Status;
import hu.tobias.services.comparator.LeaderNameComparator;
import hu.tobias.services.comparator.PatrolNameComparator;
import hu.tobias.services.comparator.ScoutNameComparator;
import hu.tobias.services.comparator.ScoutStatusNameComparator;
import hu.tobias.services.comparator.TroopNameComparator;
import hu.tobias.services.utils.Utils;

@Named(value = "utils")
@ApplicationScoped
public class UtilsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Patrol> orderPatrolSet(Set<Patrol> set) {
		List<Patrol> result = new ArrayList<Patrol>(set);
		Collections.sort(result, new PatrolNameComparator());
		return result;
	}

	public List<Leader> orderLeaderSet(Set<Leader> set) {
		List<Leader> result = new ArrayList<Leader>(set);
		Collections.sort(result, new LeaderNameComparator());
		return result;
	}

	public List<Troop> orderTroopSet(Set<Troop> set) {
		List<Troop> result = new ArrayList<Troop>(set);
		Collections.sort(result, new TroopNameComparator());
		return result;
	}

	public List<Scout> orderScoutList(List<Scout> list) {
		List<Scout> result = new ArrayList<Scout>(list);
		Collections.sort(result, new ScoutNameComparator());
		return result;
	}

	public List<Scout> orderScoutSet(Set<Scout> set) {
		List<Scout> result = new ArrayList<Scout>(set);
		Collections.sort(result, new ScoutNameComparator());
		return result;
	}

	public List<Scout> orderScout(Collection<Scout> set) {
		List<Scout> result = new ArrayList<Scout>(set);
		Collections.sort(result, new ScoutNameComparator());
		return result;
	}

	public List<Scout> orderScoutSetByStatus(Set<Scout> set) {
		List<Scout> result = new ArrayList<Scout>(set);
		Collections.sort(result, new ScoutStatusNameComparator());
		return result;
	}

	public List<Scout> addLeader(List<Scout> scouts, Set<Leader> leaders) {
		List<Leader> lead = new ArrayList<Leader>(leaders);
		Collections.sort(lead, new LeaderNameComparator());

		List<Scout> result = new ArrayList<Scout>();

		for (Leader l : lead) {
			result.add(l.getScout());
		}

		scouts.removeAll(result);
		result.addAll(scouts);

		return result;
	}

	public List<Scout> getPatrolPeople(Patrol p) {

		List<Scout> result = new ArrayList<Scout>();
		for (Leader l : orderLeaderSet(p.getLeaders())) {
			result.add(l.getScout());
		}

		List<Scout> scoutList = orderScoutSetByStatus(p.getScouts());
		scoutList.removeAll(result);
		result.addAll(scoutList);

		return result;
	}

	public int countActive(Collection<Scout> collection) {
		int count = 0;
		for (Scout s : collection) {
			if (s.getStatus().equals(Status.ACTIVE))
				count++;
		}
		return count;
	}

	public <T> int countItem(Collection<T> collection) {
		int count = 0;
		for (T t : collection) {
			count++;
		}
		return count;
	}
	
	public static String simpleDate(Date d) {
		return Utils.simpleDate(d);
	}

}
