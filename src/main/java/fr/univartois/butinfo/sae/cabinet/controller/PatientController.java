package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientController {

    @FXML
    private ListView<Patient> patientListView; // Liste principale de patients
    @FXML
    private Label idLabel;
    @FXML
    private Label patientLabel;
    @FXML
    private Label adresseLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label nomLabel;


    @FXML
    private Label prenomLabel;

    @FXML
    private Label secuLabel;

    @FXML
    private Label tauxLabel;

    @FXML
    private Label telephoneLabel;

    private Stage stage;
    private Scene scene;
    private GestionPatients gestionPatients;

    @FXML
    void initialize() {
        // Affiche les détails du patient sélectionné dans le label
        patientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idLabel.setText(String.valueOf(newValue.getId()));
                patientLabel.setText(newValue.getNomComplet());
                adresseLabel.setText(newValue.getAdresse());
                emailLabel.setText(newValue.getEmail());
                nomLabel.setText(newValue.getNom());
                prenomLabel.setText(newValue.getPrenom());
                secuLabel.setText(newValue.getNumeroSecu());
                tauxLabel.setText(String.valueOf(newValue.getTauxRemboursement()));
                telephoneLabel.setText(newValue.getTelephone());
            } else {
                idLabel.setText("");
                patientLabel.setText("");
                adresseLabel.setText("");
                emailLabel.setText("");
                nomLabel.setText("");
                prenomLabel.setText("");
                secuLabel.setText("");
                tauxLabel.setText("");
                telephoneLabel.setText("");
            }
        });
    }
    // --- Navigation ---

    @FXML
    void retourAccueil(ActionEvent event) throws IOException {
        stage.setScene(scene);
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/patient-edit.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene patienteditScene = new Scene(viewContent);
        stage.setScene(patienteditScene);

        PatientEditController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(scene);
        bind(gestionPatients);
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {

    }


    public void bind(GestionPatients patients) {
        this.gestionPatients = patients;
        patientListView.setItems(gestionPatients.listerTousLesPatients());
    }

    @FXML
    void supprimerPatient(ActionEvent event) {
        Patient selected = patientListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionPatients.supprimerPatient(selected.getId()); // Supprime le patient via le service
            patientListView.getSelectionModel().clearSelection();
        }
    }

    /**
     * Stocke la fenêtre de l'application.
     *listPatient= new ListView<>();
     * @param stage La fenêtre de l'application.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Stocke la scène de l'application.
     *
     * @param scene La scène de l'application.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}