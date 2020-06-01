package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ressources.Score;

public class TestScore {
	
	private Score score;

	@Before
	public void setUp() throws Exception {
		this.score = new Score ("MARCEL", 0, 67);
	}

	@After
	public void tearDown() throws Exception {
		this.score = null;
	}

	@Test
	public void testSetPseudo() {
		this.score.setPseudo("LUCAS");
		assertEquals("LUCAS", this.score.getPseudo());
	}
	
	@Test
	public void testSetTime() {
		this.score.setTemps(64);
		assertEquals(64, this.score.getTemps());
	}
	
	@Test
	public void testSetValue() {
		this.score.setValeur(12);
		assertEquals(12, this.score.getValeur());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSetDate () {
		this.score.setDate("05/05/1997");
		assertEquals("05/05/1997", this.score.getDate());
	}
	
	@Test
	public void testEquals () {
		Score score2 = new Score ("MARCEL", 0, 67);
		assertEquals(score2, this.score);
	}
	
	

}
