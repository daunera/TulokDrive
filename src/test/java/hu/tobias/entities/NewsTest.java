package hu.tobias.entities;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class NewsTest {

	News news1;
	News news2;
	News news3;

	@Before
	public void before() {
		news1 = new News(new Leader(1));
		news2 = new News();
		news3 = new News();

		news1.setId(1);
		news2.setId(1);
	}

	@Test
	public void testConstructor() {
		assertEquals(news1.getSubmitter().getId().equals(1), true);
		assertEquals(news1.getExpire(), new Date(new Date().getTime() + 604800000));

		assertEquals(news2.getExpire(), new Date(new Date().getTime() + 604800000));
	}

	@Test
	public void testEquals() {
		assertEquals(news1.equals(news2), true);
		assertEquals(news3.equals(news1), false);

		news3.setId(2);
		assertEquals(news3.equals(news1), false);

		assertEquals(news3.equals(new Fee()), false);
	}

}
