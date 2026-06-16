package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient; // Supposé existant
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients; // Supposé existant
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PatientEditController {
    @FXML
    private Label adresseLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private ListView<Patient> patientListView;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label secuLabel;

    @FXML
    private Label tauxLabel;

    @FXML
    private Label telephoneLabel;

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField adresseField;
    @FXML private TextField emailField;
    @FXML private TextField telephoneField;
    @FXML private DatePicker naissancePicker;
    @FXML private TextField secuField;
    @FXML private TextField tauxField;

    private Stage stage;
    private Scene mainScene;
    private GestionPatients gestionPatients;
    private ObservableList<Patient> patientsList;
    private Patient patient;

    @FXML
    public void initialize() {
        // Plus besoin de charger la spécialité ComboBox ici
    }

    public void setStage(Stage stage) { this.stage = stage; }
    public void setMainScene(Scene mainScene) { this.mainScene = mainScene; }

    public void bind(GestionPatients gestionPatients, ObservableList<Patient> patientsList) {
        this.gestionPatients = gestionPatients;
        this.patientsList = patientsList;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;

        if (patient != null) {
            nomField.setText(patient.getNom());
            prenomField.setText(patient.getPrenom());
            adresseField.setText(patient.getAdresse());
            emailField.setText(patient.getEmail());
            telephoneField.setText(patient.getTelephone());
            naissancePicker.setValue(patient.getDateNaissance());
            secuField.setText(patient.getNumeroSecu());
            secuField.setDisable(true); // Bloqué si modification
            tauxField.setText(String.valueOf(patient.getTauxRemboursement()));
        } else {
            nomField.clear();
            prenomField.clear();
            adresseField.clear();
            emailField.clear();
            telephoneField.clear();
            naissancePicker.setValue(null);
            secuField.clear();
            secuField.setDisable(false);
            tauxField.setText("0.70"); // Valeur par défaut (70% par ex)
        }
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            if (patient == null) {
                Patient newPatient = new Patient(
                        nomField.getText().trim(),
                        prenomField.getText().trim(),
                        adresseField.getText().trim(),
                        emailField.getText().trim(),
                        telephoneField.getText().trim(),
                        naissancePicker.getValue(),
                        secuField.getText().trim(),
                        Double.parseDouble(tauxField.getText().trim())
                );

                if (gestionPatients != null) {
                    if (gestionPatients.rechercherParNumeroSecu(newPatient.getNumeroSecu()) != null) {
                        afficherAlerte("Erreur", "Numéro de sécurité sociale existant", "Ce patient existe déjà.");
                        return;
                    }
                    gestionPatients.ajouterPatient(newPatient);
                }

                if (patientsList != null) {
                    patientsList.add(newPatient);
                }
            } else {
                patient.setNom(nomField.getText().trim());
                patient.setPrenom(prenomField.getText().trim());
                patient.setAdresse(adresseField.getText().trim());
                patient.setEmail(emailField.getText().trim());
                patient.setTelephone(telephoneField.getText().trim());
                patient.setDateNaissance(naissancePicker.getValue());
                patient.setTauxRemboursement(Double.parseDouble(tauxField.getText().trim()));
            }
            stage.setScene(mainScene);
        }
    }

    @FXML
    private void handleCancel() {
        stage.setScene(mainScene);
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (nomField.getText() == null || nomField.getText().trim().isEmpty()) errorMessage.append("Le nom est obligatoire !\n");
        if (prenomField.getText() == null || prenomField.getText().trim().isEmpty()) errorMessage.append("Le prénom est obligatoire !\n");
        if (naissancePicker.getValue() == null) errorMessage.append("La date de naissance est obligatoire !\n");
        if (secuField.getText() == null || secuField.getText().trim().isEmpty()) errorMessage.append("Le numéro de sécurité sociale est obligatoire !\n");

        if (tauxField.getText() == null || tauxField.getText().trim().isEmpty()) {
            errorMessage.append("Le taux de remboursement n'est pas renseigné !\n");
        } else {
            try {
                double taux = Double.parseDouble(tauxField.getText().trim());
                if (taux < 0 || taux > 1) errorMessage.append("Le taux doit être compris entre 0.0 et 1.0.\n");
            } catch (NumberFormatException e) {
                errorMessage.append("Le taux doit être un nombre valide.\n");
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            afficherAlerte("Champs invalides", "Veuillez corriger les erreurs", errorMessage.toString());
            return false;
        }
    }

    private void afficherAlerte(String titre, String entete, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle(titre);
        alert.setHeaderText(entete);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    public void bind(GestionPatients gestionPatients) {
        this.gestionPatients = gestionPatients;
    }

    public void setPatients(Patient p) {
        this.patient = p;

        if (p != null) {
            adresseLabel.setText(p.getAdresse());
            emailLabel.setText(p.getEmail());
            idLabel.setText(String.valueOf(p.getId()));
            nomLabel.setText(p.getNom());
            prenomLabel.setText(p.getPrenom());
            secuLabel.setText(p.getNumeroSecu());
            telephoneLabel.setText(p.getTelephone());
        } else {
            adresseLabel.setText("");
            emailLabel.setText("");
            idLabel.setText("");
            nomLabel.setText("");
            prenomLabel.setText("");
            secuLabel.setText("");
            telephoneLabel.setText("");
        }
    }
}