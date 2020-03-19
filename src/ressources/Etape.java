package ressources;

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
		if (this.resultat()>=0) {
			res = true;
		}
		return res;
	}
	
	public String getCalcul () {
		if (isValid())
			return plaques[indice1]+operation+plaques[indice2]+"="+this.resultat();
		else
			return plaques[indice1]+operation+plaques[indice2]+"="+this.resultat()+"?";
	}
	
}
