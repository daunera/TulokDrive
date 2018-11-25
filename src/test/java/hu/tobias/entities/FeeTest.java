package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import hu.tobias.entities.enums.FeeStatusType;

public class FeeTest {

	private Fee fee1;
	private Fee fee2;
	private Fee fee3;

	@Before
	public void before() {
		Scout s = new Scout();
		s.setId(1);
		fee1 = new Fee(s, 2010);
		fee1.setId(1);
		fee1.setStatus(FeeStatusType.CSPK);
		Date d = new Date();
		d.setTime(1);
		fee1.setCompleteDate(d);
		fee1.setContributor("apa");
		fee1.setOther("ausztrál dollárban");

		fee2 = new Fee();
		fee2.setId(1);

		fee3 = new Fee();
		fee3.setId(2);
	}

	@Test
	public void constructor() {
		assertEquals(fee1.getScout().getId().equals(1), true);
		assertEquals(fee1.getStatus(), FeeStatusType.CSPK);

		assertEquals(fee2.getScout(), null);
	}

	@Test
	public void equals() {
		assertEquals(fee1.equals(fee2), true);
		assertEquals(fee1.equals(fee3), false);
		assertEquals(new Fee().equals(fee3), false);
		assertEquals(fee1.equals(new Address()), false);
	}

	@Test
	public void getFullInfo() {
		assertEquals(fee1.getFullInfo(), "2010 - kp cspknál (1970.01.01)");
		assertEquals(fee2.getFullInfo(), "nem fizetett (2018.11.25)");
	}

	@Test
	public void getOtherInfo() {
		assertEquals(fee1.getOtherInfo(), "befizető: apa, ausztrál dollárban");
		assertEquals(fee2.getOtherInfo(), null);
	}

}
