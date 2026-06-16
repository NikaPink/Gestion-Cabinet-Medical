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
    private Patient patient;

    public void bind(GestionPatients gp) {
        this.gestionPatients = gp;
    }

    public void setPatients(Patient patient) {
        this.patient = patient;
        if (patient != null) {
            nomField.setText(patient.getNom());
            prenomField.setText(patient.getPrenom());
            telephoneField.setText(patient.getTelephone());
            emailField.setText(patient.getEmail());
            adresseField.setText(patient.getAdresse());
            naissancePicker.setValue(patient.getDateNaissance());
            secuField.setText(patient.getNumeroSecu());
            tauxField.setText(String.valueOf(patient.getTauxRemboursement()));
            secuField.setDisable(true); // Le numéro de sécu est immuable
        } else {
            nomField.clear();
            prenomField.clear();
            telephoneField.clear();
            emailField.clear();
            adresseField.clear();
            naissancePicker.setValue(null);
            secuField.clear();
            secuField.setDisable(false);
            tauxField.setText("0.75");
        }
    }

    @FXML
    void ajouterPatient() {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String tel = telephoneField.getText();
            String email = emailField.getText();
            String adresse = adresseField.getText();
            LocalDate naissance = naissancePicker.getValue();
            String secu = secuField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || tel.isEmpty() || email.isEmpty() || adresse.isEmpty() || naissance == null || secu.isEmpty()) {
                afficherErreur("Champs obligatoires", "Veuillez remplir tous les champs obligatoires.");
                return;
            }

            double taux = 0.75;
            if (tauxField.getText() != null && !tauxField.getText().isEmpty()) {
                taux = Double.parseDouble(tauxField.getText().trim());
            }

            if (patient == null) {
                // Mode ajout
                Patient newPatient = new Patient(nom, prenom, tel, email, adresse, naissance, secu, taux);
                gestionPatients.ajouterPatient(newPatient);
            } else {
                // Mode modification
                gestionPatients.mettreAJourPatient(patient.getId(), nom, prenom, tel, email, adresse, taux);
            }

            stage.setScene(mainScene);
        } catch (NumberFormatException e) {
            afficherErreur("Erreur de format", "Le taux de remboursement doit être un nombre décimal.");
        }
    }

    @FXML
    void annulerPatient(ActionEvent event) {
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
