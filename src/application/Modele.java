package application;

import java.util.LinkedList;
import java.util.Random;

import ressources.*;

public class Modele {
	
	public static final int BORN_INF = 100;
	public static final int BORN_SUP = 999;
	
	private LinkedList<Step> listSteps;
	private ManageScores gestion;
	/** Pseudo du joueur en cours */
	private String sPseudo;
	/** Nombre a trouver (entre 100 et 999) */
	private int iDesiredNumber;
	/** Duree de la partie en secondes */
	public static final int TIME_PLAY = 180;
	/** Mode de jeu */
	private String sGameMode;
	private Random random;
	/** Duree de la partie en cours */
	private int iTime;
	private Step stepInProgress;
	
	public Modele () {
		random = new Random ();
		this.gestion = new ManageScores();
	}
	
	public void initTime () {
		this.iTime = TIME_PLAY;
	}
	
	public void countDown () {
		if (this.iTime > 0)
			this.iTime--;
	}
	
	public int getTimeS () {
		return this.iTime;
	}
	
	public void initialize () {
		int nb = BORN_INF + random.nextInt(BORN_SUP-BORN_INF);
		this.iDesiredNumber = nb;
		this.listSteps = new LinkedList<>();
		this.stepInProgress = new Step ();
		this.initTime();
	}
	
	public boolean deleteLastStep () {
		boolean res = false;
		if (!this.listSteps.isEmpty()) {
			this.stepInProgress = this.listSteps.removeLast();
			res = true;
		}
		return res;
	}
	
	public void resetCalculationInProgress () {
		this.stepInProgress.resetCalculationInProgress();
	}

	public String getGameMode() {
		return sGameMode;
	}

	public void setGameMode(String gameMode) {
		this.sGameMode = gameMode;
	}

	public String getPseudo() {
		return sPseudo;
	}
	
	public boolean isPlayable () {
		return (this.listSteps.isEmpty())||(this.listSteps.size() < this.listSteps.get(0).getPlates().length-1);
	}
	
	public boolean loadScores () {
		return this.gestion.load();
	}
	
	public boolean addResult () {
		boolean res = false;
		res = this.gestion.addScore(this.sPseudo, this.getResultat(), TIME_PLAY - this.iTime);
		if (res) {
			this.gestion.record();
			this.gestion.export();
		}
		return res;
	}
	
	public boolean setIndice1 (int ind) {
		return this.stepInProgress().setIndice1(ind);
	}
	
	public boolean setIndice2 (int ind) {
		return this.stepInProgress().setIndice2(ind);
	}
	
	public boolean setOperation (String text) {
		return this.stepInProgress().setOperation(text);
	}
	
	public boolean validStep () {
		boolean res = false;
		if (this.stepInProgress.isValid()) {
			res = this.listSteps.add(this.stepInProgress());
		}
		if (res) {
			int[] oldlaques = stepInProgress.getPlates().clone();
			oldlaques[this.stepInProgress.getIndice1()] = -1;
			oldlaques[this.stepInProgress.getIndice2()] = -1;
			int[] newPlaques = new int[oldlaques.length-1];
			int i = 0;
			for (int e:oldlaques)
				if (e != -1) {
					newPlaques[i]=e;
					i++;
				}
			newPlaques[newPlaques.length-1]=this.stepInProgress.getResultat();
			this.stepInProgress=new Step (newPlaques);
		}
		return res;
	}
	
	public boolean setPseudo (String name) {
		boolean res = true;
		if (name != null && name.length()>0 && name.length()<15)
			this.sPseudo = name;
		else
			res = false;
		return res;
	}

	public int getDesiredNumber() {
		return iDesiredNumber;
	}
	
	public int[] getPlatesInProgress () {
		return this.stepInProgress.getPlates();
	}
	
	public int getResultat () {
		if (!this.listSteps.isEmpty())
			return Math.abs(this.listSteps.getLast().getResultat()-this.iDesiredNumber);
		else
			return -1;
	}
	
	public String getScores () {
		return this.gestion.toString();
	}
	
	public Step stepInProgress () {
		return this.stepInProgress;
	}
	
	public String stepsToString () {
		String res = "";
		for (Step e:this.listSteps) {
			res += e.getCalculation()+"\n";
		}
		return res;
	}

}
