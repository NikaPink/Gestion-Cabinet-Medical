package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contrôleur pour l'écran d'accueil de l'application.
 * Gère la navigation principale entre les différentes vues.
 */
public class AccueilController {

    private Stage stage;
    private Scene scene;

    private GestionMedecins gestionMedecins;
    private GestionPatients gestionPatients;
    private GestionRendezVous gestionRdv;
    private GestionMedicaments gestionMedicaments;
    private GestionOrdonnances gestionOrdonnances;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setGestionMedecins(GestionMedecins gestionMedecins) {
        this.gestionMedecins = gestionMedecins;
    }

    public void setGestionPatients(GestionPatients gestionPatients) {
        this.gestionPatients = gestionPatients;
    }

    public void setGestionRdv(GestionRendezVous gestionRdv) {
        this.gestionRdv = gestionRdv;
    }

    public void setGestionMedicaments(GestionMedicaments gestionMedicaments) {
        this.gestionMedicaments = gestionMedicaments;
    }

    public void setGestionOrdonnances(GestionOrdonnances gestionOrdonnances) {
        this.gestionOrdonnances = gestionOrdonnances;
    }

    @FXML
    void medecin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/medecins.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);

        MedecinController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        controller.bind(gestionMedecins);
    }

    @FXML
    void ordonnance(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/ordonnances.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);
    }

    @FXML
    void patient(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/patients.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);

        /*PatientController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene); // Référence à l'accueil
        controller.bind(gestionPatients);*/
    }

    @FXML
    void rdv(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/rendezVous-view.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);

        RendezVousController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        controller.bind(gestionRdv, gestionPatients, gestionMedecins);
    }

    @FXML
    void medicament(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/medicaments.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);
    }
}