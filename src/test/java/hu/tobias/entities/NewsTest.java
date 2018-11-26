package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import hu.tobias.services.utils.Utils;

public class NewsTest {

	private News news1;
	private News news2;
	private Date now;

	@Before
	public void before() {
		news1 = new News(new Leader(1));
		news2 = new News();
		now = Utils.now();

		news1.setId(1);
		news2.setId(1);
	}

	@Test
	public void testConstructor() {
		assertEquals(news1.getSubmitter().getId().equals(1), true);
		assertEquals(Utils.simpleDate(news1.getExpire()), Utils.simpleDate(new Date(now.getTime() + 604800000)));

		assertEquals(Utils.simpleDate(news2.getExpire()), Utils.simpleDate(new Date(now.getTime() + 604800000)));
	}

	@Test
	public void testEquals() {
		assertEquals(news2.equals(news1), true);
		news2.setId(2);
		assertEquals(news2.equals(news1), false);
		news2.setId(null);
		assertEquals(news2.equals(news1), false);
		assertEquals(news2.equals(new Fee()), false);
	}

}
