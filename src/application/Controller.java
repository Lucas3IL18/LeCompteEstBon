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
	
	public Controller () {
		this.modele = new Modele ();
	}
	
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
	
	@FXML
	private void initialize () {
		this.pseudo.setEditable(true);
		this.pseudo.setText(this.modele.getPseudo());
		this.realTime.setText(Modele.realTimeHMS());
		this.majEcran();
		this.time.setText(String.valueOf(Score.convertTempsMS(Modele.TIME_PLAY)));
		this.modele.setGameMode("00");
		this.attendre();
	}
	
	@FXML
	private void attendre () {
		this.changeEtatBtn(false);
		this.modele.setGameMode("01");
	}
	
	@FXML
	private void actionAfficherScores (ActionEvent evt) {
		Alert bteDialog = new Alert(AlertType.INFORMATION);
		bteDialog.setTitle("SCORES");
		bteDialog.setHeaderText("Voici les meilleures scores :");
		bteDialog.setContentText(this.modele.getScores());
		bteDialog.showAndWait();
	}
	
	@FXML
	private void actionJouer (ActionEvent evt) {
		if (this.modele.getGameMode().contentEquals("01") && this.modele.setPseudo(this.pseudo.getText())) {
			this.changeEtatBtn(true);
			this.pseudo.setEditable(false);
			this.modele.initialiser();
			this.majEcran();
			// lancer le chrono
			this.btnJouer.setText("Reset");
			this.modele.setGameMode("02");
		} else {
			this.reset();
		}
	}
	
	@FXML
	private void actionSelection (ActionEvent evt) {
		Button btn = (Button) evt.getSource();
		if (this.modele.etapeEnCours().getIndice1()==-1) {
			this.modele.etapeEnCours().setIndiceBorn1(this.modele.getIndexPlaquesEncours(Integer.valueOf(btn.getText())));
			btn.setDisable(true);
		} else {
			if (this.modele.etapeEnCours().getOperation()==null) {
				this.modele.etapeEnCours().setOperation(btn.getText());
				this.etatBtnOperations(false);
			} else {
				if (this.modele.etapeEnCours().getIndice2()==-1) {
					this.modele.etapeEnCours().setIndiceBorn2(this.modele.getIndexPlaquesEncours(Integer.valueOf(btn.getText())));
					btn.setDisable(true);
				}
			}
		}
		this.majEcran();
	}
	
	private void majEcran () {
		this.plaque1.setText(String.valueOf(this.modele.getPlaquesEnCours(0)));
		this.plaque2.setText(String.valueOf(this.modele.getPlaquesEnCours(1)));
		this.plaque3.setText(String.valueOf(this.modele.getPlaquesEnCours(2)));
		this.plaque4.setText(String.valueOf(this.modele.getPlaquesEnCours(3)));
		this.plaque5.setText(String.valueOf(this.modele.getPlaquesEnCours(4)));
		this.plaque6.setText(String.valueOf(this.modele.getPlaquesEnCours(5)));
		this.ligneCalculs.setText(this.modele.etapesToString());
		this.nombre.setText(String.valueOf(this.modele.getDesiredNumber()));
		this.calcul.setText(this.modele.etapeEnCours().getCalcul());
	}
	
	private void reset () {
		this.modele.initialiser();
		this.initialize();
		this.btnJouer.setText("Jouer");
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
