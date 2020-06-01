package ressources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Step {
	
	private static final int BORN_INF_PLAQUES = 1;
	private static final int BORN_SUP_PLAQUES = 6;
	
	private int[] plaques;
	private String operation;
	private int indice1;
	private int indice2;
	private int resultat;
	
	/**
	 * Constructeur pouvant servir a tester la classe par exemple
	 * @param plaques
	 */
	public Step (int[]plaques) {
		if (plaques.length>BORN_SUP_PLAQUES || plaques==null || plaques.length<BORN_INF_PLAQUES) {
			throw new IllegalArgumentException();
		} 
		this.plaques=plaques;
		this.resetCalculationInProgress();
	}
	
	public Step () {
		this.plaques=generateTabInt();
		this.resetCalculationInProgress();
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

	public boolean setIndice1(int indice) {
		boolean res;
		if ((this.operation!=null || this.indice2>-1)||(indice1 >= this.plaques.length || indice < 0 || indice == this.indice2)) {
			res = false;
		} else {
			this.indice1 = indice;
			res = true;
		}
		return res;
	}

	public boolean setIndice2(int indice) {
		boolean res;
		if ((this.operation==null || this.indice1<0)||(indice >= this.plaques.length || indice < 0 || indice == this.indice1)) {
			res = false;
		} else {
			this.indice2 = indice;
			res = true;
			this.resultat = this.result();
		}
		return res;
	}

	public boolean setOperation(String ope) {
		boolean res = false;
		if ((this.indice1>-1 && this.indice2<0)&&(ope.equals("X") || ope.equals("/") || ope.equals("+") || ope.equals("-"))) {
			this.operation = ope;
			res = true;
		}
		return res;
	}
	
	public void resetCalculationInProgress() {
		this.operation = null;
		this.indice1 = -1;
		this.indice2 = -1;
		this.resultat=-1;
	}
	
	private String getResultToString() {
		if (this.resultat==-1)
			return "?";
		return String.valueOf(this.resultat);
	}
	
	public int getResultat () {
		return this.resultat;
	}

	/**
	 * Calcul du resultat d une etape
	 * @return en nombre entier (-1 si non conforme)
	 */
	private int result () {
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
	
	public int[] getPlates () {
		return this.plaques;
	}
	
	public boolean isValid () {
		boolean res = false;
		if (this.indice1>-1 && this.indice2>-1 && this.operation!=null && this.resultat>=0) {
			res = true;
		}
		return res;
	}
	
	public String getCalculation () {
		if (this.indice1==-1) {
			return "";
		} else {
			if (this.operation==null) {
				return String.valueOf(this.plaques[indice1]);
			} else {
				if (this.indice2==-1) {
					return this.plaques[indice1]+this.operation;
				} else {
					return this.plaques[indice1]+this.operation+this.plaques[indice2]+"="+this.getResultToString();
				}
			}
		}
	}
	
}
