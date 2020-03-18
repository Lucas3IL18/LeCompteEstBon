package ressources;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Score implements Comparable<Score>, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pseudo;
	private int valeur;
	private int temps;
	private String date;
	public Score (String pseudo, int valeur, int temps) {
		this.pseudo = pseudo;
		this.valeur = valeur;
		this.temps = temps;
		this.date = today();
	}
	
	public int getValeur () {
		return this.valeur;
	}
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getTempsMS () {
		int minutes = this.temps/60;
		int secondes = this.temps%60;
		return minutes+":"+secondes;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public String getDate() {
		return date;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	private String today () {
		final Date date = new Date();
	    return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}
	
	public String toString () {
		return this.pseudo+" = "+this.valeur+" en "+this.getTempsMS()+"  (le "+this.date+")";
	}
	
	@Override
	public boolean equals (Object arg0) {
		boolean result = false;
		if (this.valeur==((Score)arg0).getValeur() && this.temps==((Score)arg0).getTemps() && this.date.equals(((Score)arg0).getDate()) 
				&& this.pseudo.equals(((Score)arg0).getPseudo()))
			result = true;
		return result;
	}

	@Override
	public int compareTo(Score tmp) {
		if (this.valeur < tmp.getValeur()) {
			return 1;
		} else {
			if (this.valeur > tmp.getValeur()) {
				return -1;
			} else {
				if (this.temps < tmp.getTemps()) {
					return 1;
				} else {
					if (this.temps > tmp.getTemps() ) {
						return -1;
					} else {
						return this.pseudo.compareTo(tmp.getPseudo());
					}
				}
			}
		}
	}
}
