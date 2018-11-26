package hu.tobias.services.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

import hu.tobias.entities.Address;
import hu.tobias.entities.Leader;
import hu.tobias.entities.Patrol;
import hu.tobias.entities.Person;
import hu.tobias.entities.Scout;
import hu.tobias.entities.Troop;
import hu.tobias.services.comparator.AddressComparator;
import hu.tobias.services.comparator.LeaderNameComparator;
import hu.tobias.services.comparator.PatrolNameComparator;
import hu.tobias.services.comparator.PersonNameComparator;
import hu.tobias.services.comparator.ScoutNameComparator;
import hu.tobias.services.comparator.ScoutStatusNameComparator;
import hu.tobias.services.comparator.TroopNameComparator;

public class Utils {

	public static boolean isEmpty(final String s) {
		return s == null || s.trim().isEmpty();
	}

	public static String simpleDate(final Date d) {
		if (d != null)
			return new SimpleDateFormat("yyyy.MM.dd").format(d);
		return null;
	}

	public static String simpleDatetime(final Date d) {
		if (d != null)
			return new SimpleDateFormat("yyyy.MM.dd hh:MM:ss").format(d);
		return null;
	}

	public static int ageInYear(final Date d) {
		LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return Period.between(date, LocalDate.now()).getYears();
	}
	
	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	public static String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	public static String getHelpMailto() {
		return "mailto:dauner.agoston+tulokdrive@cserkesz.hu?subject=TulokDrive+visszajelzés";
	}

	public static List<Patrol> orderPatrolSet(Set<Patrol> set) {
		List<Patrol> result = new ArrayList<Patrol>(set);
		Collections.sort(result, new PatrolNameComparator());
		return result;
	}

	public static List<Patrol> orderPatrolList(List<Patrol> list) {
		List<Patrol> result = list;
		Collections.sort(result, new PatrolNameComparator());
		return result;
	}

	public static List<Leader> orderLeaderSet(Set<Leader> set) {
		List<Leader> result = new ArrayList<Leader>(set);
		Collections.sort(result, new LeaderNameComparator());
		return result;
	}

	public static List<Leader> orderLeaderList(List<Leader> list) {
		List<Leader> result = list;
		Collections.sort(result, new LeaderNameComparator());
		return result;
	}

	public static List<Troop> orderTroopSet(Set<Troop> set) {
		List<Troop> result = new ArrayList<Troop>(set);
		Collections.sort(result, new TroopNameComparator());
		return result;
	}

	public static List<Troop> orderTroopList(List<Troop> list) {
		List<Troop> result = list;
		Collections.sort(result, new TroopNameComparator());
		return result;
	}

	public static List<Scout> orderScoutSet(Set<Scout> set) {
		List<Scout> result = new ArrayList<Scout>(set);
		Collections.sort(result, new ScoutNameComparator());
		return result;
	}

	public static List<Scout> orderScoutList(List<Scout> list) {
		List<Scout> result = list;
		Collections.sort(result, new ScoutNameComparator());
		return result;
	}

	public static List<Scout> orderScoutSetByStatus(Set<Scout> set) {
		List<Scout> result = new ArrayList<Scout>(set);
		Collections.sort(result, new ScoutStatusNameComparator());
		return result;
	}

	public static List<Address> orderAddressSet(Set<Address> set) {
		List<Address> result = new ArrayList<Address>(set);
		Collections.sort(result, new AddressComparator());
		return result;
	}

	public static List<Address> orderAddressList(List<Address> list) {
		List<Address> result = list;
		Collections.sort(result, new AddressComparator());
		return result;
	}

	public static List<Person> orderPersonSet(Set<Person> set) {
		List<Person> result = new ArrayList<Person>(set);
		Collections.sort(result, new PersonNameComparator());
		return result;
	}

	public static List<Person> orderPersonList(List<Person> list) {
		List<Person> result = list;
		Collections.sort(result, new PersonNameComparator());
		return result;
	}

}
