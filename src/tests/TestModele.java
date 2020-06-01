package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.Modele;

public class TestModele {
	
	public Modele m;

	@Before
	public void setUp() throws Exception {
		this.m = new Modele ();
		this.m.initialize();
	}

	@After
	public void tearDown() throws Exception {
		this.m = null;
	}

	@Test
	public void testCountDown() {
		int test = this.m.getTimeS();
		this.m.countDown();
		assertEquals(test-1, this.m.getTimeS());
	}
	
	@Test
	public void testSetPseudo() {
		this.m.setPseudo("LUCAS");
		assertEquals("LUCAS", this.m.getPseudo());
	}
	
	@Test
	public void testDesiredNumber () {
		boolean res = true;
		for (int i = 100; i < 999; i++) {
			this.m.initialize();
			if (this.m.getDesiredNumber() < 100 || this.m.getDesiredNumber() > 999)
				res = false;
		}
		assertTrue(res);
	}
	
	@Test
	public void testDeleteLastStepEmpty () {
		assertFalse(this.m.deleteLastStep());
	}
	
	@Test
	public void testtestValidStep () {
		this.m.setIndice1(0);
		this.m.setOperation("+");
		this.m.setIndice2(1);
		assertTrue(this.m.validStep());
	}
	
	@Test
	public void testUnvalidStep () {
		this.m.setIndice1(0);
		this.m.setOperation("+");
		this.m.setIndice2(0);
		assertFalse(this.m.validStep());
	}
	
	@Test
	public void testAddResult () {
		this.m.setPseudo("LUCAS");
		this.m.setIndice1(0);
		this.m.setOperation("+");
		this.m.setIndice2(1);
		this.m.validStep();
		this.m.countDown();
		assertTrue(this.m.addResult());
	}
	
	@Test
	public void testSetGameMode () {
		this.m.setGameMode("ok");
		assertEquals("ok", this.m.getGameMode());
	}

}
