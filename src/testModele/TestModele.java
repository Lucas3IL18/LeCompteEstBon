package testModele;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.Modele;

public class TestModele {
	
	private Modele modele;

	@Before
	public void setUp() throws Exception {
		this.modele = new Modele ();
	}

	@After
	public void tearDown() throws Exception {
		this.modele = null;
	}

	@Test
	public void testValidEtape() {
		afficherTabint(this.modele.etapeEnCours().getPlaques());
		System.out.println("");
		this.modele.etapeEnCours().setIndiceBorn1(0);
		this.modele.etapeEnCours().setOperation("+");
		this.modele.etapeEnCours().setIndiceBorn2(1);
		System.out.println(this.modele.etapeEnCours().getResultat());
		this.modele.validEtape();
		
		System.out.println("Nouvelle plaques");
		afficherTabint(this.modele.etapeEnCours().getPlaques());
		System.out.println("");
		
		System.out.println("Ancienne etapes");
		System.out.println(this.modele.etapesToString());
		System.out.println("");
	}
	
	private void afficherTabint (int[] tab) {
		for(int e:tab)
			System.out.print(e+" ");
	}
}
