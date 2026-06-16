package fr.univartois.butinfo.sae.cabinet.controller;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Contrôleur pour la vue Master-Detail de gestion des rendez-vous.
 */
public class RendezVousController {
    @FXML
    private Label infos;

    @FXML
    private ListView<RendezVous> listRDV;

    @FXML
    private Label idLabel;

    @FXML
    private Label patientLabel;

    @FXML
    private Label medecinLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dureeLabel;

    @FXML
    private Label motifLabel;

    @FXML
    private Label statutLabel;

    @FXML
    private ListView<?> list;

    private Stage stage;

    private Scene scene;

    private GestionRendezVous gestionRendezVous;

    private GestionPatients gestionPatients;
    private GestionMedecins gestionMedecins;

    @FXML
    void initialize() {
        listRDV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                medecinLabel.setText(newValue.getMedecin().getNomComplet());
                idLabel.setText(String.valueOf(newValue.getId()));
                patientLabel.setText(newValue.getPatient().getNomComplet());
                dateLabel.setText(newValue.getDateHeure().toString());
                dureeLabel.setText(String.valueOf(newValue.getDureeMinutes()) + " minutes");
                motifLabel.setText(newValue.getMotif());
                statutLabel.setText(newValue.getStatut().toString());
            } else {
                medecinLabel.setText("");
                idLabel.setText("");
                patientLabel.setText("");
                dateLabel.setText("");
                dureeLabel.setText("");
                motifLabel.setText("");
                statutLabel.setText("");
            }
        });
    }

    @FXML
    void ajouter() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/rendezVousEdit-view.fxml"));
        Parent viewContent = fxmlLoader.load();

        Scene RendezVouseditScene = new Scene(viewContent);

        stage.setScene(RendezVouseditScene);

        RendezVousEditController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(scene);
        controller.bind(gestionRendezVous, gestionPatients, gestionMedecins);
    }

    @FXML
    void supprimer(ActionEvent event) {
        RendezVous selected = listRDV.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionRendezVous.listerTousLesRendezVous().remove(selected);
            listRDV.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void retourAccueil(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/accueil.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);
    }

    @FXML
    void deplacerDansMedecin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/medecins.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);

        MedecinController controller = fxmlLoader.getController();
        controller.setMedecins(gestionMedecins.listerTousLesMedecins());
    }

    @FXML
    void deplacerDansPatient(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/patients.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);
    }

    @FXML
    void deplacerDansRDV(ActionEvent event) {
    }

    @FXML
    void deplacerDansOrdonnance(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/ordonnances.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene newScene = new Scene(viewContent, 800, 600);
        stage.setScene(newScene);
    }

    public void bind(GestionRendezVous rdv, GestionPatients patients, GestionMedecins medecins) {
        this.gestionRendezVous = rdv;
        this.gestionPatients = patients;
        this.gestionMedecins = medecins;
        listRDV.setItems(rdv.listerTousLesRendezVous());
    }

    /**
     * Stocke la fenêtre de l'application.
     *
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
