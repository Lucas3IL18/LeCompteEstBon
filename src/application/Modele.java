package application;

import java.util.LinkedList;
import java.util.Random;

import ressources.*;

public class Modele {
	
	public static final int BORN_INF = 100;
	public static final int BORN_SUP = 999;
	
	private LinkedList<Etape> list;
	private GereScores gestion;
	/** Pseudo du joueur en cours */
	private String pseudo;
	/** Nombre a trouver (entre 100 et 999) */
	private int desiredNumber;
	/** Duree de la partie en secondes */
	private int timePlay;
	/** Mode de jeu */
	private String gameMode;
	private Random r;
	
	public Modele () {
		r = new Random ();
		this.gestion = new GereScores();
		this.initialiser();
	}
	
	public void initialiser () {
		this.gameMode = "00";
		this.pseudo = null;
		int nb = BORN_INF + r.nextInt(BORN_SUP-BORN_INF);
		this.desiredNumber = nb;
		this.list = new LinkedList<>();
		this.list.add(new Etape ());
		this.gestion.charge();
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

	public int getDesiredNumber() {
		return desiredNumber;
	}

	public int getTimePlay() {
		return timePlay;
	}
	
	public String getScores () {
		return this.gestion.toString();
	}

	public void setTimePlay(int timePlay) {
		this.timePlay = timePlay;
	}

}
