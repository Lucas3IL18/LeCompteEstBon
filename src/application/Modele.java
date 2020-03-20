package application;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	public static final int TIME_PLAY = 180;
	/** Mode de jeu */
	private String gameMode;
	private Random r;
	private Etape etapeEnCours;
	
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
		this.gestion.charge();
		this.etapeEnCours = new Etape ();
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
	
	public boolean setPseudo (String name) {
		boolean res = true;
		if (name.length()>0 && name.length()<15)
			this.pseudo = name;
		else
			res = false;
		return res;
	}

	public int getDesiredNumber() {
		return desiredNumber;
	}
	
	public int getPlaquesEnCours (int index) {
		return this.etapeEnCours.getPlaques()[index];
	}
	
	public int getIndexPlaquesEncours(int value) {
		int i = 0;
		int[] tab = this.etapeEnCours.getPlaques();
		while (i < tab.length && tab[i]!=value) {
			i++;
		}
		return i;
	}
	
	public String getScores () {
		return this.gestion.toString();
	}
	
	public static String realTimeHMS () {
		final Date dateTmp = new Date();
	    return new SimpleDateFormat("hh:mm:ss").format(dateTmp);
	}
	
	public Etape etapeEnCours () {
		return this.etapeEnCours;
	}
	
	public String etapesToString () {
		String res = "";
		for (Etape e:this.list) {
			res += e.getCalcul()+"\n";
		}
		return res;
	}

}
