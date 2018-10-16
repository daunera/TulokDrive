package hu.tobias.services.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

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

	public static String generatePassword() {
		return RandomStringUtils.randomAlphabetic(10);
	}

	public static String getHelpMailto() {
		return "mailto:dauner.agoston+tulokdrive@cserkesz.hu?subject=TulokDrive+visszajelzés";
	}

}
