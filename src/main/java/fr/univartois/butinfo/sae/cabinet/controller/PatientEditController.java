package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientEditController {

    private Stage stage;
    private Scene mainScene;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField emailField;

    @FXML
    private DatePicker naissancePicker;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField secuField;

    @FXML
    private TextField tauxField;

    @FXML
    private TextField telephoneField;

    private GestionPatients gestionPatients;

    private Scene scene;
    @FXML
    void ajouterPatient() throws IOException {
            gestionPatients = new GestionPatients();

            if (adresseField == null || emailField == null || nomField == null || prenomField == null || secuField == null || tauxField == null || telephoneField == null || naissancePicker== null) {
                afficherErreur("Champs obligatoires", "remplir tous les champs obligatoires (patient, médecin, date et heure).");
            } else {

                Patient pat = new Patient(nomField.getText(), prenomField.getText(), telephoneField.getText(), emailField.getText(), adresseField.getText(), naissancePicker.getValue(), secuField.getText());


                if (pat == null) {
                    afficherErreur("Erreur patient", "Le patient n'existe pas.");
                } else {
                    stage.setScene(mainScene);
                }
            }



    }

    @FXML
    void annulerPatient(ActionEvent event)  {
        stage.setScene(mainScene);
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

}
