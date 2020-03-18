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

public class GereScores {
	
	private TreeSet<Score> list;
	private final String path = System.getProperty("user.dir")+"//data//";
	private final String serialName = "serial.bin";
	private final String htmlName = "resultat.html";
	
	public GereScores () {
		this.list = new TreeSet<>();
		this.charge();
	}
	
	public void addScore (String pseudo, int valeur, int temps) {
		this.list.add(new Score(pseudo, valeur, temps));
		if (this.list.size()==10) {
			this.list.pollLast();
		}
	}
	
	public void affiche () {
		for (Score e : this.list) {
			System.out.println(e);
		}
	}
	
	public String toString() {
		String res = "";
		for (Score e:this.list ) {
			res += e.toString()+"\n";
		}
		return res;
	}
	
	public void enregistre () {
		File file = new File (path+serialName);
	    ObjectOutputStream oos = null;
	    try {
	    	final FileOutputStream fichier = new FileOutputStream(file);
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
	
	public boolean charge () {
		ObjectInputStream ois = null;
		File file = new File (path+serialName);
		if (file.exists()) {
			try {
			      final FileInputStream fichier = new FileInputStream(file);
			      ois = new ObjectInputStream(fichier);
			      boolean end = false;
			      while (!end) {
			    	  try {
			    		  Score score = (Score) ois.readObject();
			    		  if (score!=null)
					    	  this.addScore(score.getPseudo(), score.getValeur(), score.getTemps());
			    	  } catch (EOFException e) {
			    		  end=true;
			    	  }
			      }
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
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
	
	public void export () {
		try {
			File HTMLFILE= new File(path+htmlName);
			BufferedWriter fichier = new BufferedWriter(new FileWriter(HTMLFILE));

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
		}catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public static void main (String[]args) {
		GereScores g = new GereScores();
		g.addScore("MARCEL", 0, 67);
		g.addScore("GEORGES", 0, 67);
		g.addScore("RENEE", 0, 155);
		g.addScore("GEORGES", 2, 84);
		g.addScore("LUCIEN", 15, 220);
		g.addScore("ANTHO", 10, 150);
		g.addScore("MARCEL", 5, 67);
		g.addScore("MARCEL", 4, 80);
		g.addScore("LUCAS", 3, 67);
		g.addScore("MARCEL", 2, 90);
		g.addScore("MARCEL", 1, 67);
		g.addScore("MARCEL", 50, 450);
		g.addScore("MARCEL", 30, 67);
		g.addScore("MARCEL", 20, 100);
		
		g.affiche();
	
		g.enregistre();
		g = null;
		g = new GereScores();
		g.charge();
		
		System.out.println("-----------------");
		g.affiche();
		g.export();
	}
}
