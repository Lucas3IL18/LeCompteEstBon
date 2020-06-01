package ressources;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

public class ManageScores {
	
	private TreeSet<Score> list;
	/** Chemin d accee au repertoire data */
	private final String path = System.getProperty("user.dir")+"//data//";
	/** Nom du fichier BIN */
	private static final String SERIALNAME = "serial.bin"; 
	/** Nom du fichier HTML */
	private static final String HTMLNAME = "resultat.html";
	
	/** 
	 * Constructeur de la classe GereScores 
	 * Charge le dernier fichier utiliser sinon creer un nouveau
	 */
	public ManageScores () {
		this.list = new TreeSet<>();
		if (!this.load())
			record();
	}
	
	/**
	 * Ajoute un nouveau score
	 * @param pseudo
	 * @param valeur (valeur > 0)
	 * @param temps (temps > 0)
	 * @return true si le score a bien ete ajouter.
	 */
	public boolean addScore (String pseudo, int valeur, int temps) {
		boolean res = false;
		if (valeur >= 0 && temps > 0) {
			this.list.add(new Score(pseudo, valeur, temps));
			if (this.list.size()==10) { 
				this.list.pollLast();
			}
			res = true;
		}
		return res;
	}
	
	private void addScore (Score s) {
		this.list.add(s);
		if (this.list.size()==10) {
			this.list.pollLast();
		}
	}
	
	public void display () {
		for (Score e : this.list) {
			System.out.println(e);
		}
	}
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		for (Score e:this.list ) {
			res.append(e.toString()+"\n");
		}
		return res.toString();
	}
	
	/**
	 * Sauvegarde du tableau des scores dans un fichier .bin
	 */
	public void record () {
		File file = new File (path+SERIALNAME);
	    ObjectOutputStream oos = null;
	    try (final FileOutputStream fichier = new FileOutputStream(file)) {
	    	oos = new ObjectOutputStream(fichier);
	    	for (Score score:this.list ) {
	    		oos.writeObject(score);
	    		oos.flush();
	    	}
	    } catch (final java.io.IOException e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		if (oos != null) {
	    			oos.flush();
	    			oos.close();
	    		}
	    	} catch (final IOException ex) {
	    	  ex.printStackTrace(); 
	    	}
	    }
	}
	
	/**
	 * Extraction du tableau des scores depuis un fichier .bin
	 */
	public boolean load () {
		ObjectInputStream ois = null;
		File file = new File (path+SERIALNAME);
		if (file.exists()) {
			try (final FileInputStream fichier = new FileInputStream(file)) {
			      ois = new ObjectInputStream(fichier);
			      updateSerialScore(ois);
			} catch (java.io.IOException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ois != null) {
						ois.close();
			        }
			    } catch (final IOException ex) {
			        ex.printStackTrace();
			    }
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Procedure permettant de mettre a jour le tableau des scores
	 * @param oi : Objet pointant vers le fichier de serialisation .bin
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void updateSerialScore (ObjectInputStream oi) throws ClassNotFoundException, IOException {
		boolean end = false;
	      while (!end) {
	    	  try {
	    		  Score score = (Score) oi.readObject();
	    		  if (score!=null)
			    	  this.addScore(score);
	    	  } catch (EOFException e) {
	    		  end=true;
	    	  }
	      }
	}
	
	/**
	 * Exporte le tableau des scores dans un fichier html
	 */
	public boolean export () {
		boolean res = false;
		try {
			File htmlFile= new File(path+HTMLNAME);
			BufferedWriter fichier = new BufferedWriter(new FileWriter(htmlFile));

			fichier.write("<HTML>\r\n" + 
					"			<head>\r\n" + 
					"				<title>S C O R E S</title>\r\n" + 
					"			</head>\r\n" + 
					"			<body>\r\n" + 
					"				<h1>S C O R E S</h1>\n");
			for(Score s : this.list) {
				fichier.write("<p><b style=\"color:#B41414\">"+s.getPseudo()
				+ "="+s.getValeur()
				+"</b> en "+s.getTempsMS()
				+"<small><small>(le "+s.getDate()
				+")</small></small></p>\n");
			}

			fichier.write("</body>\r\n" + 
					"</HTML>");

			fichier.close();
			res = true;
		}catch (Exception e) {
			System.err.println(e);
		}
		return res;
	}
}
