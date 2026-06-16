package fr.univartois.butinfo.sae.cabinet.controller;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Contrôleur pour le formulaire d'ajout et modification d'une ordonnance.
 */
public class OrdonnanceEditController {

    @FXML
    private ComboBox<Patient> patientComboBox;

    @FXML
    private ComboBox<Medecin> medecinComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField heureField;

    @FXML
    private TextField dureeField;

    @FXML
    private TextField motifField;

    private GestionOrdonnances gestionOrdonnances;
    private GestionPatients gestionPatients;
    private GestionMedecins gestionMedecins;

    private Stage stage;
    private Scene mainScene;

    public void bind(GestionOrdonnances ordonnances, GestionPatients patients, GestionMedecins medecins) {
        this.gestionOrdonnances = ordonnances;
        this.gestionPatients = patients;
        this.gestionMedecins = medecins;

        patientComboBox.setItems(patients.listerTousLesPatients());
        patientComboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNomComplet());
            }
        });
        patientComboBox.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNomComplet());
            }
        });

        medecinComboBox.setItems(FXCollections.observableArrayList(medecins.listerTousLesMedecins()));
        medecinComboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Medecin item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : "Dr " + item.getNomComplet() + " (" + item.getSpecialite().getLibelle() + ")");
            }
        });
        medecinComboBox.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Medecin item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : "Dr " + item.getNomComplet() + " (" + item.getSpecialite().getLibelle() + ")");
            }
        });
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void creer(ActionEvent event) {
        try {
            Patient patient = patientComboBox.getValue();
            Medecin medecin = medecinComboBox.getValue();
            LocalDate date = datePicker.getValue();

            if (patient == null || medecin == null || date == null) {
                afficherErreur("Champs obligatoires", "Veuillez remplir tous les champs obligatoires (patient, médecin, date de validité).");
            } else {
                String remarques = motifField != null ? motifField.getText() : "";
                Ordonnance ordonnance = gestionOrdonnances.creerOrdonnance(medecin, patient, date, remarques);

                if (ordonnance == null) {
                    afficherErreur("Erreur", "L'ordonnance n'a pas pu être créée.");
                } else {
                    stage.setScene(mainScene);
                }
            }
        } catch (Exception e) {
            afficherErreur("Erreur de saisie", "Veuillez vérifier les informations saisies.");
        }
    }

    @FXML
    void annuler(ActionEvent event) {
        stage.setScene(mainScene);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

}
