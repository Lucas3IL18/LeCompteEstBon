package application;

import java.util.LinkedList;
import java.util.Random;

import ressources.*;

public class Modele {
	
	public static final int BORN_INF = 100;
	public static final int BORN_SUP = 999;
	
	private LinkedList<Etape> listEtapes;
	private GereScores gestion;
	/** Pseudo du joueur en cours */
	private String pseudo;
	/** Nombre a trouver (entre 100 et 999) */
	private int desiredNumber;
	/** Duree de la partie en secondes */
	public static final int TIME_PLAY = 180;
	/** Mode de jeu */
	private String gameMode;
	private Random r;
	/** Duree de la partie en cours */
	private int time;
	private Etape etapeEnCours;
	
	public Modele () {
		r = new Random ();
		this.gestion = new GereScores();
	}
	
	public void initTime () {
		this.time = TIME_PLAY;
	}
	
	public void decompter () {
		if (this.time > 0)
			this.time--;
	}
	
	public int getTimeS () {
		return this.time;
	}
	
	public void initialiser () {
		int nb = BORN_INF + r.nextInt(BORN_SUP-BORN_INF);
		this.desiredNumber = nb;
		this.listEtapes = new LinkedList<>();
		this.etapeEnCours = new Etape ();
		this.initTime();
	}
	
	public boolean supprimerLastEtape () {
		boolean res = false;
		if (!this.listEtapes.isEmpty()) {
			this.etapeEnCours = this.listEtapes.removeLast();
			res = true;
		}
		return res;
	}
	
	public void resetCalculEnCours () {
		this.etapeEnCours.resetCalculEnCours();
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public String getPseudo() {
		return pseudo;
	}
	
	public boolean estJouable () {
		return (this.listEtapes.isEmpty())||(this.listEtapes.size() < this.listEtapes.get(0).getPlaques().length-1);
	}
	
	public boolean chargeScores () {
		return this.gestion.charge();
	}
	
	public boolean addResult () {
		boolean res = false;
		res = this.gestion.addScore(this.pseudo, this.getResultat(), TIME_PLAY - this.time);
		if (res) {
			this.gestion.enregistre();
			this.gestion.export();
		}
		return res;
	}
	
	public boolean setIndice1 (int ind) {
		return this.etapeEnCours().setIndice1(ind);
	}
	
	public boolean setIndice2 (int ind) {
		return this.etapeEnCours().setIndice2(ind);
	}
	
	public boolean setOperation (String text) {
		return this.etapeEnCours().setOperation(text);
	}
	
	public boolean validEtape () {
		boolean res = false;
		if (this.etapeEnCours.isValid()) {
			res = this.listEtapes.add(this.etapeEnCours());
		}
		if (res) {
			int[] oldlaques = etapeEnCours.getPlaques().clone();
			oldlaques[this.etapeEnCours.getIndice1()] = -1;
			oldlaques[this.etapeEnCours.getIndice2()] = -1;
			int[] newPlaques = new int[oldlaques.length-1];
			int i = 0;
			for (int e:oldlaques)
				if (e != -1) {
					newPlaques[i]=e;
					i++;
				}
			newPlaques[newPlaques.length-1]=this.etapeEnCours.getResultat();
			this.etapeEnCours=new Etape (newPlaques);
		}
		return res;
	}
	
	public boolean setPseudo (String name) {
		boolean res = true;
		if (name != null && name.length()>0 && name.length()<15)
			this.pseudo = name;
		else
			res = false;
		return res;
	}

	public int getDesiredNumber() {
		return desiredNumber;
	}
	
	public int[] getPlaquesEnCours () {
		return this.etapeEnCours.getPlaques();
	}
	
	public int getResultat () {
		if (!this.listEtapes.isEmpty())
			return Math.abs(this.listEtapes.getLast().getResultat()-this.desiredNumber);
		else
			return -1;
	}
	
	public String getScores () {
		return this.gestion.toString();
	}
	
	public Etape etapeEnCours () {
		return this.etapeEnCours;
	}
	
	public String etapesToString () {
		String res = "";
		for (Etape e:this.listEtapes) {
			res += e.getCalcul()+"\n";
		}
		return res;
	}

}
