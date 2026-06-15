package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Contrôleur pour le formulaire d'ajout et modification d'un rendez-vous.
 */
public class RendezVousEditController {

    private Stage stage;
    private Scene mainScene;

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

    private GestionRendezVous gestionRendezVous;
    private GestionPatients gestionPatients;
    private GestionMedecins gestionMedecins;

    /**
     * Stocke la fenêtre de l'application.
     *
     * @param stage La fenêtre de l'application.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void bind(GestionRendezVous rdv, GestionPatients patients, GestionMedecins medecins) {
        this.gestionRendezVous = rdv;
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
    void ajouter(ActionEvent event) {
        try {
            Patient patient = patientComboBox.getValue();
            Medecin medecin = medecinComboBox.getValue();
            LocalDate date = datePicker.getValue();

            if (patient == null || medecin == null || date == null || heureField.getText().isEmpty()) {
                afficherErreur("Champs obligatoires", "remplir tous les champs obligatoires (patient, médecin, date et heure).");
                return ;
            }

            String[] heureMinutes = heureField.getText().split(":");
            int heure = Integer.parseInt(heureMinutes[0].trim());
            int minutes = Integer.parseInt(heureMinutes[1].trim());
            LocalDateTime dateHeure = date.atTime(heure, minutes);

            int duree = Integer.parseInt(dureeField.getText().trim());
            String motif = motifField.getText();

            RendezVous rdv = gestionRendezVous.prendreRendezVous(patient, medecin, dateHeure, duree, motif);

            if (rdv == null) {
                afficherErreur("Créneau indisponible", "Le médecin choisi n'est pas disponible à cette date ou heure.");
            } else {
                stage.setScene(mainScene);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Si l'heure ou la durée ne sont pas dans un format numérique valide
            afficherErreur("Erreur de saisie", "vérifier le format de l'heure (HH:mm) et de la durée (minutes).");
        }
    }

    @FXML
    void annuler(ActionEvent event) {
        stage.setScene(mainScene);
    }
}
