package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
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
	private HBox plaques;
	@FXML
	private Label userMessage;
	
	private boolean chrono;
	
	public Controller () {
		this.modele = new Modele ();
		this.chrono = false;
	}
	
	@FXML
	private void initialize () {
		this.modele.setGameMode("00");
		this.modele.setPseudo(null);
		// initialise le modele
		this.modele.initialiser();
		this.etatBtnPlaques(false);
		
		Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), ae -> actionTimer()));
		timer.setCycleCount(Animation.INDEFINITE);
		timer.play();

		this.time.setText(String.valueOf(Score.convertTempsMS(Modele.TIME_PLAY)));
		
		// met a jour l ecran en focntion du modele
		this.majEcran();
		// passe e l etat attendre
		this.attendre();
	}
	
	private void actionTimer () {
		this.realTime.setText(Modele.realTimeHMS());
		if (chrono) {
			this.modele.decompter();
		}
		this.time.setText(String.valueOf(Score.convertTempsMS(this.modele.getTimeS())));
		if (this.modele.getTimeS()==0)
			this.actionProposer();
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
		etatBtnPlaques(false);
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
	
	private void lancerChrono () {
		this.modele.initTime();
		this.chrono=true;
	}
	
	@FXML
	private void actionPreparer(ActionEvent evt) {
		if (this.btnJouer.getText().equals("Reset")) {
			this.attendre();
			this.modele.initTime();
			this.chrono=false;
		} else {
			if (this.modele.getGameMode().equals("01") && this.modele.setPseudo(this.pseudo.getText())) {
				this.modele.setGameMode("02");
				this.changeEtatBtn(true);
				this.pseudo.setEditable(false);
				this.modele.initialiser();
				this.majEcran();
				this.lancerChrono();
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
		if (this.plaques.getChildren().contains(btn)) {
			if (this.modele.setIndice1(this.plaques.getChildren().indexOf(btn))) {
				btn.setDisable(true);
			}
			if (this.modele.setIndice2(this.plaques.getChildren().indexOf(btn))) {
				etatBtnPlaques(false);
				if (this.modele.estJouable() && this.modele.etapeEnCours().isValid())
					this.btnValider.setDisable(false);
			}
		} else {
			this.modele.setOperation(btn.getText());
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
		if (this.modele.supprimerLastEtape()) {
			this.modele.resetCalculEnCours();
			majEcran();
		}
	}
	
	@FXML
	private void actionProposer () {
		this.modele.setGameMode("03");
		this.actionScore();
	}
	/******* FIN ACTION JOUER ********/
	
	private void actionScore () {
		this.modele.setGameMode("04");
		if (this.modele.addResult()) {
			this.chrono = false;
			this.userMessage.setText("Message : BRAVO! Votre score est de "+this.modele.getResultat()+".");
			this.attendre();
		} else {
			if (this.modele.getTimeS()==0) {
				this.chrono = false;
				this.userMessage.setText("Message : PERDU!");
				this.attendre();
			} else { 
				this.modele.setGameMode("03");
			}
		}
	}
	
	private void afficherPlaques () {
		this.plaques.getChildren().clear();
		for (int e:this.modele.getPlaquesEnCours()) {
			Button btn = new Button ();
			btn.setText(e+"");
			btn.setPrefSize(50, 27);
			btn.setOnAction(event->{this.actionSelection(event);});
			this.plaques.getChildren().add(btn);
		}
	}
	
	private void majEcran () {
		this.afficherPlaques();
		this.ligneCalculs.setText(this.modele.etapesToString());
		this.nombre.setText(String.valueOf(this.modele.getDesiredNumber()));
		this.calcul.setText(this.modele.etapeEnCours().getCalcul());
		this.userMessage.setText("");
	}
	
	private void etatBtnPlaques (boolean etat) {
		for (int i=0; i < this.plaques.getChildren().size(); i++) {
			Button b = (Button)(this.plaques.getChildren().get(i));
			b.setDisable(!etat);
		}
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
	}
	
}
