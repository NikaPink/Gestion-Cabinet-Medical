package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Contrôleur pour le formulaire d'ajout d'un médicament.
 */
public class MedicamentEditController {

    private Stage stage;
    private Scene mainScene;

    @FXML
    private TextField nomField;

    @FXML
    private TextField principeField;

    @FXML
    private TextField prixField;

    private GestionMedicaments gestionMedicaments;
    private GestionPatients gestionPatients;
    private GestionMedecins gestionMedecins;
    private GestionRendezVous gestionRendezVous;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void bind(GestionMedicaments medicaments, GestionPatients patients, GestionMedecins medecins, GestionRendezVous rdv) {
        this.gestionMedicaments = medicaments;
        this.gestionPatients = patients;
        this.gestionMedecins = medecins;
        this.gestionRendezVous = rdv;

        nomField.setText("");
        principeField.setText("");
        prixField.setText("10.0"); // prix par défaut
    }

    @FXML
    void handleOk(ActionEvent event) {
        if (isInputValid()) {
            Medicament nouveau = new Medicament(
                    nomField.getText().trim(),
                    principeField.getText().trim(),
                    Double.parseDouble(prixField.getText().trim())
            );
            gestionMedicaments.ajouterMedicament(nouveau);
            stage.setScene(mainScene);
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        stage.setScene(mainScene);
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomField.getText() == null || nomField.getText().trim().isEmpty()) {
            errorMessage += "Le nom n'est pas renseigné ";
        }
        if (principeField.getText() == null || principeField.getText().trim().isEmpty()) {
            errorMessage += "Le principe actif n'est pas renseigné ";
        }

        if (prixField.getText() == null || prixField.getText().trim().isEmpty()) {
            errorMessage += "Le prix moyen n'est pas renseigné ";
        } else {
            try {
                double prix = Double.parseDouble(prixField.getText().trim());
                if (prix <= 0) {
                    errorMessage += "Le prix doit être strictement supérieur à 0";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Le prix doit être un nombre valide (ex: 12.5) ";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            afficherErreur("Champs invalides", errorMessage);
            return false;
        }
    }
}
