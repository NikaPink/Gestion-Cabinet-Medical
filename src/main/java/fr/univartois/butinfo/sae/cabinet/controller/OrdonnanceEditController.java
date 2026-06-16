package fr.univartois.butinfo.sae.cabinet.controller;
import fr.univartois.butinfo.sae.cabinet.modele.LignePrescription;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

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
    private TextField remarquesField;

    @FXML
    private ComboBox<Medicament> medicamentComboBox;

    @FXML
    private TextField quantiteField;

    @FXML
    private TextField posologieField;

    @FXML
    private TextField dureeField;

    @FXML
    private ListView<LignePrescription> prescriptionListView;

    private GestionOrdonnances gestionOrdonnances;
    private GestionPatients gestionPatients;
    private GestionMedecins gestionMedecins;
    private GestionMedicaments gestionMedicaments;

    /** Lignes de prescription saisies avant la création de l'ordonnance. */
    private final ObservableList<LignePrescription> lignes = FXCollections.observableArrayList();

    private Stage stage;
    private Scene mainScene;

    public void bind(GestionOrdonnances ordonnances, GestionPatients patients,
                     GestionMedecins medecins, GestionMedicaments medicaments) {
        this.gestionOrdonnances = ordonnances;
        this.gestionPatients = patients;
        this.gestionMedecins = medecins;
        this.gestionMedicaments = medicaments;

        medicamentComboBox.setItems(medicaments.listerTousLesMedicaments());
        prescriptionListView.setItems(lignes);

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
    void ajouterPrescription(ActionEvent event) {
        Medicament medicament = medicamentComboBox.getValue();
        if (medicament == null) {
            afficherErreur("Médicament manquant", "Veuillez sélectionner un médicament.");
            return;
        }

        int quantite = parseIntOuDefaut(quantiteField.getText(), 1);
        int duree = parseIntOuDefaut(dureeField.getText(), 0);
        String posologie = posologieField.getText();

        lignes.add(new LignePrescription(medicament, quantite, posologie, duree, ""));

        medicamentComboBox.setValue(null);
        quantiteField.clear();
        posologieField.clear();
        dureeField.clear();
    }

    private int parseIntOuDefaut(String valeur, int defaut) {
        try {
            return Integer.parseInt(valeur.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return defaut;
        }
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
                String remarques = remarquesField != null ? remarquesField.getText() : "";
                Ordonnance ordonnance = gestionOrdonnances.creerOrdonnance(medecin, patient, date, remarques);

                if (ordonnance == null) {
                    afficherErreur("Erreur", "L'ordonnance n'a pas pu être créée.");
                } else {
                    for (LignePrescription ligne : lignes) {
                        ordonnance.ajouterPrescription(ligne);
                    }
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
