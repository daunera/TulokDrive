package hu.tobias.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import hu.tobias.entities.Address;
import hu.tobias.entities.Leader;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Scout;
import hu.tobias.entities.Troop;
import hu.tobias.entities.enums.Status;
import hu.tobias.services.utils.Utils;

@Named(value = "utils")
@ApplicationScoped
public class UtilsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Patrol> orderPatrolSet(Set<Patrol> set) {
		return Utils.orderPatrolSet(set);
	}

	public List<Patrol> orderPatrolList(List<Patrol> list) {
		return Utils.orderPatrolList(list);
	}

	public List<Leader> orderLeaderSet(Set<Leader> set) {
		return Utils.orderLeaderSet(set);
	}

	public List<Leader> orderLeaderList(List<Leader> list) {
		return Utils.orderLeaderList(list);
	}

	public List<Troop> orderTroopSet(Set<Troop> set) {
		return Utils.orderTroopSet(set);
	}

	public List<Troop> orderTroopList(List<Troop> list) {
		return Utils.orderTroopList(list);
	}

	public List<Scout> orderScoutSet(Set<Scout> set) {
		return orderScoutSet(set);
	}

	public List<Scout> orderScoutList(List<Scout> list) {
		return Utils.orderScoutList(list);
	}

	public List<Scout> orderScoutSetByStatus(Set<Scout> set) {
		return Utils.orderScoutSetByStatus(set);
	}

	public List<Address> orderAddressSet(Set<Address> set) {
		return Utils.orderAddressSet(set);
	}

	public List<Address> orderAddressList(List<Address> list) {
		return Utils.orderAddressList(list);
	}

	public List<Scout> getPatrolPeople(Patrol p) {

		List<Scout> result = new ArrayList<Scout>();
		for (Leader l : orderLeaderSet(p.getLeaders())) {
			result.add(l.getScout());
		}

		List<Scout> scoutList = Utils.orderScoutSetByStatus(p.getScouts());
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

	public static String simpleDate(Date d) {
		return Utils.simpleDate(d);
	}

}
