package testressources;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ressources.GereScores;

public class TestGereScore {
	
	private GereScores g;

	@Before
	public void setUp() throws Exception {
		g = new GereScores();
	}

	@After
	public void tearDown() throws Exception {
		g = null;
	}

	@Test
	public void testEnregistrementEtChargement() {
		g.addScore("MARCEL", 0, 67);
		g.addScore("GEORGES", 0, 67);
		g.addScore("RENEE", 0, 155);
		g.addScore("GEORGES", 2, 84);
		g.addScore("LUCIEN", 15, 220);
		g.addScore("ANTHO", 10, 150);
		g.addScore("MARCEL", 5, 67);
		g.addScore("MARCEL", 4, 80);
		g.addScore("LUCAS", 3, 67);
		g.addScore("MARCEL", 2, 90);
		g.addScore("MARCEL", 1, 67);
		g.addScore("MARCEL", 50, 450);
		g.addScore("MARCEL", 30, 67);
		g.addScore("MARCEL", 20, 100);
		
		g.enregistre();
		
		GereScores g2 = new GereScores();
		g2.charge();

		assertTrue(g.toString().equals(g2.toString()));
	}

}
