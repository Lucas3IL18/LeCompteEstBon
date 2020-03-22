package testressources;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ressources.Etape;

public class TestEtape {
	
	private Etape etape;

	@Before
	public void setUp() {
		int[] plaques = {2,6,8,1,9,7};
		this.etape = new Etape (plaques);
	}

	@After
	public void tearDown() {
		this.etape = null;
	}

	@Test
	public void testNombreEntierNegatif() {
		this.etape.setIndiceBorn1(0);
		this.etape.setIndiceBorn2(1);
		this.etape.setOperation("-");
		assertFalse(this.etape.isValid());
	}
	
	@Test
	public void testNombreEntierPositif() {
		this.etape.setIndiceBorn1(0);
		this.etape.setIndiceBorn2(1);
		this.etape.setOperation("+");
		assertTrue(this.etape.isValid());
	}
	
	@Test
	public void testNombreFlottant() {
		this.etape.setIndiceBorn1(0);
		this.etape.setIndiceBorn2(1);
		this.etape.setOperation("/");
		assertFalse(this.etape.isValid());
	}
	
	@Test
	public void testNombreFlottantResultEntier() {
		this.etape.setIndiceBorn1(0);
		this.etape.setIndiceBorn2(3);
		this.etape.setOperation("/");
		assertTrue(this.etape.isValid());
	}
	
	@Test
	public void testMultiplications() {
		this.etape.setIndiceBorn1(0);
		this.etape.setIndiceBorn2(1);
		this.etape.setOperation("X");
		assertTrue(this.etape.isValid());
	}
	
	 @Test(expected=NullPointerException.class)
	 public void testExceptionNullConstructeur() {
		 this.etape = new Etape(null);
	 }
	 
	 @Test(expected=IllegalArgumentException.class)
	 public void testExceptionIllegalArgumentConstructeur() {
		 int[] plaques = {2,6,8,1,9,7,8};
		 this.etape = new Etape(plaques);
	 }

}
