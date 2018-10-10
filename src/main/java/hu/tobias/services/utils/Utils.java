package hu.tobias.services.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

import hu.tobias.entities.Patrol;
import hu.tobias.services.comparator.PatrolNameComparator;

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
	
	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	public static String generatePassword() {
		return RandomStringUtils.randomAlphabetic(10);
	}
	
	public static String getHelpMailto() {
		return "mailto:dauner.agoston+tulokdrive@cserkesz.hu?subject=TulokDrive+visszajelzés";
	}

}
