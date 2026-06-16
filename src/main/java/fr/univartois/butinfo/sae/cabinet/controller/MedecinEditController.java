package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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

    private Stage stage;
    private Scene mainScene;
    private GestionMedecins gestionMedecins;
    private ObservableList<Medecin> medecinsList;
    private Medecin medecin;

    /**
     * Initialise la classe de contrôle. Cette méthode est appelée automatiquement
     * après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        specialiteComboBox.setItems(FXCollections.observableArrayList(Specialite.values()));
    }

    /**
     * Définit la fenêtre principale pour ce contrôleur.
     *
     * @param stage La fenêtre principale.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Définit la scène principale à restaurer lors de l'annulation ou de l'enregistrement.
     *
     * @param mainScene La scène principale.
     */
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Injecte les dépendances pour les opérations de mise à jour/enregistrement.
     */
    public void bind(GestionMedecins gestionMedecins, ObservableList<Medecin> medecinsList) {
        this.gestionMedecins = gestionMedecins;
        this.medecinsList = medecinsList;
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
     * Appelé lorsque l'utilisateur clique sur Enregistrer.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            if (medecin == null) {
                // Création d'un nouveau médecin
                Medecin newMedecin = new Medecin(
                        nomField.getText().trim(),
                        prenomField.getText().trim(),
                        telephoneField.getText().trim(),
                        emailField.getText().trim(),
                        ordreField.getText().trim(),
                        specialiteComboBox.getValue(),
                        Double.parseDouble(tarifField.getText().trim())
                );

                if (gestionMedecins != null) {
                    if (gestionMedecins.rechercherParNumeroOrdre(newMedecin.getNumeroOrdre()) != null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(stage);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Numéro d'ordre existant");
                        alert.setContentText("Un médecin avec ce numéro d'ordre existe déjà.");
                        alert.showAndWait();
                        return;
                    }
                    gestionMedecins.ajouterMedecin(newMedecin);
                }

                if (medecinsList != null) {
                    medecinsList.add(newMedecin);
                }
            } else {
                medecin.setNom(nomField.getText().trim());
                medecin.setPrenom(prenomField.getText().trim());
                medecin.setTelephone(telephoneField.getText().trim());
                medecin.setEmail(emailField.getText().trim());
                medecin.setSpecialite(specialiteComboBox.getValue());
                medecin.setTarifHoraire(Double.parseDouble(tarifField.getText().trim()));
            }

            stage.setScene(mainScene);
        }
    }

    /**
     * Appelé lorsque l'utilisateur clique sur Annuler.
     */
    @FXML
    private void handleCancel() {
        stage.setScene(mainScene);
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
                    errorMessage += "Le tarif de consultation doit être strictement supérieur à 0.\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Le tarif de consultation doit être un nombre valide.\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Affichage de l'alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Veuillez corriger les champs incorrects");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
