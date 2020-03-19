package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
	private TextField calcul;
	
	@FXML
	private void initialize () {
		this.btnDiviser.setDisable(true);
		this.btnPlus.setDisable(true);
		this.btnFois.setDisable(true);
		this.btnMoins.setDisable(true);
		this.btnAnnuler.setDisable(true);
		this.btnValider.setDisable(true);
		this.btnProposer.setDisable(true);
		this.btnSupprimer.setDisable(true);
	}
	
	@FXML
	private void actionAfficherScores (ActionEvent evt) {
		Alert bteDialog = new Alert(AlertType.INFORMATION);
		bteDialog.setTitle("SCORES");
		bteDialog.setHeaderText("Voici les meilleures scores :");
		bteDialog.setContentText(this.modele.getScores());
		bteDialog.showAndWait();
	}
	
	
}
