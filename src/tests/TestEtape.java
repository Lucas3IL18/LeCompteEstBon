package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

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
	public void testConstructor () {
		int[] plaques = {2,6,8,1,9,7};
		assertTrue(Arrays.equals(plaques, this.etape.getPlaques()));
	}
	
	@Test
	public void testSetIndice1() {
		this.etape.setIndice1(10);
		assertEquals(10, this.etape.getIndice1());
	}
	
	@Test
	public void testSetOperation() {
		this.etape.setIndice1(10);
		this.etape.setOperation("-");
		assertEquals("-", this.etape.getOperation());
	}
	
	@Test
	public void testIndice2SameIndice1 () {
		this.etape.setIndice1(10);
		this.etape.setOperation("-");
		assertFalse(this.etape.setIndice2(10));
	}
	
	@Test
	public void testGetCalcul () {
		assertEquals("" ,this.etape.getCalcul());
		this.etape.setIndice1(0);
		assertEquals("2" ,this.etape.getCalcul());
		this.etape.setOperation("X");
		assertEquals("2X" ,this.etape.getCalcul());
		this.etape.setIndice2(1);
		assertEquals("2X6=12" ,this.etape.getCalcul());
	}
	
	@Test
	public void testSetIndice2() {
		this.etape.setIndice1(0);
		this.etape.setOperation("-");
		this.etape.setIndice2(1);
		assertEquals(1, this.etape.getIndice2());
	}

	@Test
	public void testNombreEntierNegatif() {
		this.etape.setIndice1(0);
		this.etape.setOperation("-");
		this.etape.setIndice2(1);
		assertFalse(this.etape.isValid());
	}
	
	@Test
	public void testNombreEntierPositif() {
		this.etape.setIndice1(0);
		this.etape.setOperation("+");
		this.etape.setIndice2(1);
		assertTrue(this.etape.isValid());
	}
	
	@Test
	public void testNombreFlottant() {
		this.etape.setIndice1(0);
		this.etape.setOperation("/");
		this.etape.setIndice2(1);
		assertFalse(this.etape.isValid());
	}
	
	@Test
	public void testNombreFlottantResultEntier() {
		this.etape.setIndice1(0);
		this.etape.setOperation("/");
		this.etape.setIndice2(3);
		assertTrue(this.etape.isValid());
	}
	
	@Test
	public void testMultiplications() {
		this.etape.setIndice1(0);
		this.etape.setOperation("X");
		this.etape.setIndice2(1);
		assertTrue(this.etape.isValid());
	}
	
	@Test
	public void testConstructor2 () {
		Etape e = new Etape ();
		assertEquals(6, e.getPlaques().length);
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
