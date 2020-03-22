package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ressources.Score;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {
	
	private final Modele modele;
	
	@FXML
	private Button btnJouer;
	@FXML
	private Button btnScores;
	@FXML
	private Button btnPlus;
	@FXML
	private Button btnMoins;
	@FXML
	private Button btnFois;
	@FXML
	private Button btnDiviser;
	@FXML
	private Button btnAnnuler;
	@FXML
	private Button btnValider;
	@FXML
	private Button btnProposer;
	@FXML
	private Button btnSupprimer;
	@FXML
	private Button plaque1;
	@FXML
	private Button plaque2;
	@FXML
	private Button plaque3;
	@FXML
	private Button plaque4;
	@FXML
	private Button plaque5;
	@FXML
	private Button plaque6;
	@FXML
	private TextField calcul;
	@FXML
	private TextField pseudo;
	@FXML
	private TextArea ligneCalculs;
	@FXML
	private Label nombre;
	@FXML
	private Label time;
	@FXML
	private Label realTime;
	
	public Controller () {
		this.modele = new Modele ();
	}
	
	@FXML
	private void initialize () {
		this.modele.setGameMode("00");
		this.modele.setPseudo(null);
		// initialise le modele
		this.modele.initialiser();
		
		this.realTime.setText(Modele.realTimeHMS());
		this.time.setText(String.valueOf(Score.convertTempsMS(Modele.TIME_PLAY)));
		
		// met a jour l ecran en focntion du modele
		this.majEcran();
		// passe e l etat attendre
		this.attendre();
	}
	
	private void attendre () {
		// mettre a jour le mode de jeu
		this.modele.setGameMode("01");
		
		this.btnJouer.setText("Jouer");
		this.btnScores.setDisable(false);
		
		// grise tout les boutons sauf jouer et scores
		this.changeEtatBtn(false);
		// activer la zone pseudo
		this.pseudo.setEditable(true);
	}
	
	@FXML
	private void actionAfficherScores (ActionEvent evt) {
		if (this.modele.chargeScores()) {
			Alert bteDialog = new Alert(AlertType.INFORMATION);
			bteDialog.setTitle("SCORES");
			bteDialog.setHeaderText("Voici les meilleures scores :");
			bteDialog.setContentText(this.modele.getScores());
			bteDialog.showAndWait();
		} else {
			Alert bteDialog = new Alert(AlertType.ERROR);
			bteDialog.setTitle("ERROR");
			bteDialog.setHeaderText("Impossible de recuperer les scores.");
			bteDialog.showAndWait();
		}
	}
	
	@FXML
	private void actionPreparer(ActionEvent evt) {
		if (this.btnJouer.getText().equals("Reset")) {
			this.attendre();
		} else {
			if (this.modele.getGameMode().equals("01") && this.modele.setPseudo(this.pseudo.getText())) {
				this.modele.setGameMode("02");
				this.changeEtatBtn(true);
				this.pseudo.setEditable(false);
				this.modele.initialiser();
				this.majEcran();
				// lancer le chrono
				this.btnJouer.setText("Reset");
				this.btnValider.setDisable(true);
				this.btnScores.setDisable(true);
			}
		}
	}
	
	/********** ACTION JOUER ***********/
	@FXML
	private void actionSelection (ActionEvent evt) {
		this.modele.setGameMode("03");
		Button btn = (Button) evt.getSource();
		if (this.modele.etapeEnCours().getIndice1()==-1) {
			try {
				this.modele.etapeEnCours().setIndiceBorn1(this.modele.getIndexPlaquesEncours(Integer.valueOf(btn.getText())));
				btn.setDisable(true);
			} catch (NumberFormatException e) {
				System.out.println("Entrer incorrect.");
			}
		} else {
			if (this.modele.etapeEnCours().getOperation()==null) {
				this.modele.etapeEnCours().setOperation(btn.getText());
				this.etatBtnOperations(false);
			} else {
				if (this.modele.etapeEnCours().getIndice2()==-1) {
					this.modele.etapeEnCours().setIndiceBorn2(this.modele.getIndexPlaquesEncours(Integer.valueOf(btn.getText())));
					etatBtnPlaques(false);
					if (this.modele.etapeEnCours().isValid())
						this.btnValider.setDisable(false);
				}
			}
		}
		this.majEcran();
	}
	
	@FXML
	private void actionAnnuler () {
		this.modele.setGameMode("03");
		this.modele.resetCalculEnCours();
		etatBtnPlaques(true);
		etatBtnOperations(true);
		this.btnValider.setDisable(true);
		majEcran();
	}
	
	@FXML
	private void actionValider() {
		this.modele.setGameMode("03");
		if(this.modele.validEtape()) {
			etatBtnPlaques(true);
			etatBtnOperations(true);
			this.btnValider.setDisable(true);
			majEcran();
		}
	}
	
	@FXML
	private void actionSupprimer () {
		this.modele.setGameMode("03");
		this.modele.supprimerLastEtape();
		this.btnValider.setDisable(false);
		majEcran();
	}
	
	@FXML
	private void actionProposer () {
		this.modele.setGameMode("03");
		this.actionScore();
	}
	/******* FIN ACTION JOUER ********/
	
	private void actionScore () {
		this.modele.setGameMode("04");
		// stopper le chrono
		this.modele.addResult();
		this.attendre();
	}
	
	private void majEcran () {
		switch(this.modele.etapeEnCours().getPlaques().length) {
		case 6:
			this.plaque1.setText(String.valueOf(this.modele.getPlaquesEnCours(0)));
			this.plaque2.setText(String.valueOf(this.modele.getPlaquesEnCours(1)));
			this.plaque3.setText(String.valueOf(this.modele.getPlaquesEnCours(2)));
			this.plaque4.setText(String.valueOf(this.modele.getPlaquesEnCours(3)));
			this.plaque5.setText(String.valueOf(this.modele.getPlaquesEnCours(4)));
			this.plaque6.setText(String.valueOf(this.modele.getPlaquesEnCours(5)));
		break;
		case 5 :
			this.plaque1.setText(String.valueOf(this.modele.getPlaquesEnCours(0)));
			this.plaque2.setText(String.valueOf(this.modele.getPlaquesEnCours(1)));
			this.plaque3.setText(String.valueOf(this.modele.getPlaquesEnCours(2)));
			this.plaque4.setText(String.valueOf(this.modele.getPlaquesEnCours(3)));
			this.plaque5.setText(String.valueOf(this.modele.getPlaquesEnCours(4)));
		break;
		case 4 :
			this.plaque1.setText(String.valueOf(this.modele.getPlaquesEnCours(0)));
			this.plaque2.setText(String.valueOf(this.modele.getPlaquesEnCours(1)));
			this.plaque3.setText(String.valueOf(this.modele.getPlaquesEnCours(2)));
			this.plaque4.setText(String.valueOf(this.modele.getPlaquesEnCours(3)));
		break;
		case 3:
			this.plaque1.setText(String.valueOf(this.modele.getPlaquesEnCours(0)));
			this.plaque2.setText(String.valueOf(this.modele.getPlaquesEnCours(1)));
			this.plaque3.setText(String.valueOf(this.modele.getPlaquesEnCours(2)));
		break;
		default :
			this.plaque1.setText(String.valueOf(this.modele.getPlaquesEnCours(0)));
			this.plaque2.setText(String.valueOf(this.modele.getPlaquesEnCours(1)));
		break;
		}
		this.ligneCalculs.setText(this.modele.etapesToString());
		this.nombre.setText(String.valueOf(this.modele.getDesiredNumber()));
		this.calcul.setText(this.modele.etapeEnCours().getCalcul());
	}
	
	private void etatBtnPlaques (boolean etat) {
		this.plaque1.setDisable(!etat);
		this.plaque2.setDisable(!etat);
		this.plaque3.setDisable(!etat);
		this.plaque4.setDisable(!etat);
		this.plaque5.setDisable(!etat);
		this.plaque6.setDisable(!etat);
	}
	
	private void etatBtnOperations (boolean etat) {
		this.btnDiviser.setDisable(!etat);
		this.btnPlus.setDisable(!etat);
		this.btnFois.setDisable(!etat);
		this.btnMoins.setDisable(!etat);
	}
	
	private void changeEtatBtn (boolean etat) {
		this.etatBtnOperations(etat);
		this.btnAnnuler.setDisable(!etat);
		this.btnValider.setDisable(!etat);
		this.btnProposer.setDisable(!etat);
		this.btnSupprimer.setDisable(!etat);
		this.plaque1.setDisable(!etat);
		this.plaque2.setDisable(!etat);
		this.plaque3.setDisable(!etat);
		this.plaque4.setDisable(!etat);
		this.plaque5.setDisable(!etat);
		this.plaque6.setDisable(!etat);
	}
	
}
