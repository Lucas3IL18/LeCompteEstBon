package ressources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Etape {
	
	private static final int BORN_INF_PLAQUES = 1;
	private static final int BORN_SUP_PLAQUES = 6;
	
	private int[] plaques;
	private String operation;
	private int indice1;
	private int indice2;
	
	public Etape (int[]plaques) {
		if (plaques.length>BORN_SUP_PLAQUES || plaques==null || plaques.length<BORN_INF_PLAQUES) {
			throw new IllegalArgumentException();
		} 
		this.plaques=plaques;
		this.indice1=-1;
		this.indice2=-1;
	}
	
	public Etape () {
		this.plaques=generateTabInt();
		this.indice1=-1;
		this.indice2=-1;
	}
	
	private int[] generateTabInt () {
		int[] res = new int[6];
		List<Integer> nombresATirer = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,25,50,75,100));
		Random r = new Random ();
		for(int i = 0 ; i < 6 ; i++) {
			//Tirage au sort de l index
			int index = r.nextInt(nombresATirer.size());
			//Affectation a la liste de la valeur correspondant a l index tire
			res[i]=nombresATirer.get(index);
			//Le nombre tire est supprime pour ne pas etre tire 2 fois
			nombresATirer.remove(index);
		}
		return res;
	}

	public String getOperation() {
		return operation;
	}

	public int getIndice1() {
		return indice1;
	}

	public int getIndice2() {
		return indice2;
	}

	public boolean setIndiceBorn1(int indice) {
		boolean res;
		if (indice1 >= this.plaques.length || indice < 0 || indice == this.indice2) {
			res = false;
		} else {
			this.indice1 = indice;
			res = true;
		}
		return res;
	}
	
	public void resetIndiceBorn1 () {
		this.indice1 = -1;
	}

	public boolean setIndiceBorn2(int indice) {
		boolean res;
		if (indice >= this.plaques.length || indice < 0 || indice == this.indice1) {
			res = false;
		} else {
			this.indice2 = indice;
			res = true;
		}
		return res;
	}
	
	public void resetIndiceBorn2 () {
		this.indice2 = -1;
	}

	public boolean setOperation(String ope) {
		boolean res = false;
		if (ope.equals("X") || ope.equals("/") || ope.equals("+") || ope.equals("-")) {
			this.operation = ope;
			res = true;
		}
		return res;
	}
	
	public void resetOperation () {
		this.operation = null;
	}
	
	public String getResultat() {
		if (resultat()==-1)
			return "?";
		return String.valueOf(resultat());
	}

	/**
	 * Calcul du resultat d une etape
	 * @return en nombre entier (-1 si non conforme)
	 */
	public int resultat () {
		int res;
		switch(this.operation) {
		case "X":
			res = this.plaques[indice1]*this.plaques[indice2];
		break; 
		case "/":
			if (this.plaques[indice1]%this.plaques[indice2] == 0)
				res = this.plaques[indice1]/this.plaques[indice2];
			else
				res = -1;
		break;
		case "-":
			res = this.plaques[indice1]-this.plaques[indice2];
		break;
		default:
			res = this.plaques[indice1]+this.plaques[indice2];
		break;
		}	
		return res;
	}
	
	public int[] getPlaques () {
		return this.plaques;
	}
	
	public boolean isValid () {
		boolean res = false;
		if (this.indice1>-1 && this.indice2>-1 && this.operation!=null && this.resultat()>=0) {
			res = true;
		}
		return res;
	}
	
	public String getCalcul () {
		if (this.indice1==-1) {
			return "";
		} else {
			if (this.operation==null) {
				return String.valueOf(this.plaques[indice1]);
			} else {
				if (this.indice2==-1) {
					return this.plaques[indice1]+this.operation;
				} else {
					return this.plaques[indice1]+this.operation+this.plaques[indice2]+"="+this.getResultat();
				}
			}
		}
	}
	
}
