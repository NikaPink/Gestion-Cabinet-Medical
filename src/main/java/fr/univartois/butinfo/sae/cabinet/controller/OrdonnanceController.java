package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.LignePrescription;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
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

public class OrdonnanceController {

    @FXML
    private ListView<Ordonnance> ordonnanceListView;

    @FXML
    private Label idLabel;

    @FXML
    private Label medecinLabel;

    @FXML
    private Label patientLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ListView<LignePrescription> prescriptionListView;

    @FXML
    private Label remarquesLabel;

    private GestionOrdonnances ordonnances;
    private GestionPatients gestionPatients;
    private GestionMedecins gestionMedecins;

    private Stage stage;
    private Scene scene;

    @FXML
    public void initialize() {

        ordonnanceListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue != null) {

                        idLabel.setText(String.valueOf(newValue.getId()));
                        medecinLabel.setText(newValue.getMedecin().getNomComplet());
                        patientLabel.setText(newValue.getPatient().getNomComplet());

                        dateLabel.setText(newValue.getDateValidite().toString());

                        prescriptionListView.getItems().setAll(newValue.getPrescriptions());

                        remarquesLabel.setText(newValue.getRemarques());


                    } else {
                        idLabel.setText("");
                        medecinLabel.setText("");
                        patientLabel.setText("");
                        dateLabel.setText("");

                        prescriptionListView.getItems().clear();

                        remarquesLabel.setText("");
                    }
                });
    }

    @FXML
    void retourAccueil(ActionEvent event) throws IOException {
        stage.setScene(scene);
    }

    @FXML
    void creerOrdonnance(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/ordonnance-edit.fxml"));
        Parent viewContent = fxmlLoader.load();

        Scene RendezVouseditScene = new Scene(viewContent);

        stage.setScene(RendezVouseditScene);

        OrdonnanceEditController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(scene);
        controller.bind(ordonnances, gestionPatients, gestionMedecins);
    }

    private FXMLLoader chargerVue(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        stage.setScene(new Scene(loader.load(), 800, 600));
        return loader;
    }

    public void bind(GestionOrdonnances ordonnances,
                     GestionPatients patients,
                     GestionMedecins medecins) {

        this.ordonnances = ordonnances;
        this.gestionPatients = patients;
        this.gestionMedecins = medecins;

        ordonnanceListView.setItems(ordonnances.listerToutesLesOrdonnances());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}