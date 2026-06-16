package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientController {

    @FXML private ListView<Patient> listPatient; // Liste principale de patients
    @FXML private Label idLabel;
    @FXML private Label patientLabel;

    private Stage stage;
    private Scene scene;
    private GestionPatients gestionPatients;
    private GestionMedecins gestionMedecins;

    @FXML
    void initialize() {
        // Affiche les détails du patient sélectionné dans le label
        listPatient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idLabel.setText(String.valueOf(newValue.getId()));
                patientLabel.setText(newValue.getNomComplet());
            } else {
                idLabel.setText("");
                patientLabel.setText("");
            }
        });
    }

    @FXML
    void ajouterPatient() throws IOException {
        // Redirection vers le formulaire d'ajout/édition d'un patient
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/patient.fxml"));
        Parent viewContent = fxmlLoader.load();
        stage.setScene(new Scene(viewContent));

        // Note: Assurez-vous d'avoir un contrôleur dédié pour la vue de saisie
    }

    @FXML
    void supprimerPatient(ActionEvent event) {
        Patient selected = listPatient.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionPatients.supprimerPatient(selected.getId()); // Supprime le patient via le service
            listPatient.getSelectionModel().clearSelection();
        }
    }

    // --- Navigation ---

    @FXML
    void retourAccueil(ActionEvent event) throws IOException {
        chargerVue("/fr/univartois/butinfo/sae/cabinet/view/accueil.fxml");
    }

    @FXML
    void deplacerDansMedecin(ActionEvent event) throws IOException {
        FXMLLoader loader = chargerVue("/fr/univartois/butinfo/sae/cabinet/view/medecins.fxml");
        MedecinController controller = loader.getController();
        controller.setMedecins(gestionMedecins.listerTousLesMedecins());
    }

    @FXML
    void deplacerDansOrdonnance(ActionEvent event) throws IOException {
        chargerVue("/fr/univartois/butinfo/sae/cabinet/view/ordonnances.fxml");
    }

    // Méthode utilitaire de chargement
    private FXMLLoader chargerVue(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        stage.setScene(new Scene(loader.load(), 800, 600));
        return loader;
    }

    public void bind(GestionPatients patients, GestionMedecins medecins) {
        this.gestionPatients = patients;
        this.gestionMedecins = medecins;
        listPatient.setItems(gestionPatients.listerTousLesPatients());
    }

    public void setStage(Stage stage) { this.stage = stage; }
}