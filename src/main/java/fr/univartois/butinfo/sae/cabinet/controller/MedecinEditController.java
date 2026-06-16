package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Contrôleur pour le formulaire d'ajout et modification d'un médecin.
 */
public class MedecinEditController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ordreField;

    @FXML
    private ComboBox<Specialite> specialiteComboBox;

    @FXML
    private TextField tarifField;

    private Stage dialogStage;
    private Medecin medecin;
    private boolean okClicked = false;

    /**
     * Initialise la classe de contrôle. Cette méthode est appelée automatiquement
     * après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        specialiteComboBox.setItems(FXCollections.observableArrayList(Specialite.values()));
    }

    /**
     * Définit la scène de dialogue pour ce contrôleur.
     *
     * @param dialogStage La scène de dialogue.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Définit le médecin à éditer dans le formulaire.
     * Si le médecin est nul, le formulaire servira à créer un nouveau médecin.
     *
     * @param medecin Le médecin à éditer, ou null pour un ajout.
     */
    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;

        if (medecin != null) {
            nomField.setText(medecin.getNom());
            prenomField.setText(medecin.getPrenom());
            telephoneField.setText(medecin.getTelephone());
            emailField.setText(medecin.getEmail());
            ordreField.setText(medecin.getNumeroOrdre());
            ordreField.setDisable(true);
            specialiteComboBox.setValue(medecin.getSpecialite());
            tarifField.setText(String.valueOf(medecin.getTarifHoraire()));
        } else {
            nomField.setText("");
            prenomField.setText("");
            telephoneField.setText("");
            emailField.setText("");
            ordreField.setText("");
            ordreField.setDisable(false);
            specialiteComboBox.setValue(null);
            tarifField.setText("30.0"); // Valeur par défaut
        }
    }

    /**
     * Retourne true si l'utilisateur a cliqué sur Enregistrer, sinon false.
     *
     * @return true si l'enregistrement a été validé, sinon false.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Retourne le médecin créé ou modifié.
     *
     * @return Le médecin.
     */
    public Medecin getMedecin() {
        return medecin;
    }

    /**
     * Appelé lorsque l'utilisateur clique sur Enregistrer.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            if (medecin == null) {
                // Création d'un nouveau médecin
                medecin = new Medecin(
                        nomField.getText().trim(),
                        prenomField.getText().trim(),
                        telephoneField.getText().trim(),
                        emailField.getText().trim(),
                        ordreField.getText().trim(),
                        specialiteComboBox.getValue(),
                        Double.parseDouble(tarifField.getText().trim())
                );
            } else {
                medecin.setNom(nomField.getText().trim());
                medecin.setPrenom(prenomField.getText().trim());
                medecin.setTelephone(telephoneField.getText().trim());
                medecin.setEmail(emailField.getText().trim());
                medecin.setSpecialite(specialiteComboBox.getValue());
                medecin.setTarifHoraire(Double.parseDouble(tarifField.getText().trim()));
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Appelé lorsque l'utilisateur clique sur Annuler.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Valide les saisies utilisateur dans les champs de texte.
     *
     * @return true si la saisie est valide.
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nomField.getText() == null || nomField.getText().trim().isEmpty()) {
            errorMessage += "Le nom n'est pas renseigné !\n";
        }
        if (prenomField.getText() == null || prenomField.getText().trim().isEmpty()) {
            errorMessage += "Le prénom n'est pas renseigné !\n";
        }
        if (telephoneField.getText() == null || telephoneField.getText().trim().isEmpty()) {
            errorMessage += "Le numéro de téléphone n'est pas renseigné !\n";
        }
        if (emailField.getText() == null || emailField.getText().trim().isEmpty()) {
            errorMessage += "L'adresse e-mail n'est pas renseignée !\n";
        }
        if (ordreField.getText() == null || ordreField.getText().trim().isEmpty()) {
            errorMessage += "Le numéro d'ordre n'est pas renseigné !\n";
        }
        if (specialiteComboBox.getValue() == null) {
            errorMessage += "La spécialité doit être sélectionnée !\n";
        }

        if (tarifField.getText() == null || tarifField.getText().trim().isEmpty()) {
            errorMessage += "Le tarif n'est pas renseigné !\n";
        } else {
            try {
                double tarif = Double.parseDouble(tarifField.getText().trim());
                if (tarif <= 0) {
                    errorMessage += "Le tarif de consultation doit être strictement supérieur à 0 !\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Le tarif de consultation doit être un nombre valide (ex: 25.0) !\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Affichage de l'alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Veuillez corriger les champs incorrects");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
