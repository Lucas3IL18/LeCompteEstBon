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

	public void setIndiceBorn1(int indice1) {
		this.indice1 = indice1;
	}

	public void setIndiceBorn2(int indice2) {
		this.indice2 = indice2;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Calcul du resultat d une etape
	 * @return en nombre entier (-1 si non conforme)
	 */
	private int resultat () {
		int res;
		switch(operation) {
		case "x":
			res = plaques[indice1]*plaques[indice2];
		break; 
		case "/":
			if (plaques[indice1]%plaques[indice2] == 0)
				res = plaques[indice1]/plaques[indice2];
			else
				res = -1;
		break;
		case "-":
			res = plaques[indice1]-plaques[indice2];
		break;
		default:
			res = plaques[indice1]+plaques[indice2];
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
				return String.valueOf(plaques[indice1]);
			} else {
				if (this.indice2==-1) {
					return plaques[indice1]+operation;
				} else {
					return plaques[indice1]+operation+plaques[indice2]+"="+this.resultat();
				}
			}
		}
	}
	
}
