package fr.univartois.butinfo.sae.cabinet.controller;
import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur pour la vue Master-Detail de gestion des ordonnances.
 */
public class OrdonnanceController {

    @FXML
    private ListView<Ordonnance> ordonnancelisteview;

    @FXML
    private Label idLabel;

    @FXML
    private Label medecinLabel;

    @FXML
    private Label patientLabel;

    @FXML
    private Label dateEmissionLabel;

    @FXML
    private Label dateValiditeLabel;

    @FXML
    private Label prescriptionsLabel;

    @FXML
    private Label remarquesLabel;

    private GestionOrdonnances ordonnances;




    /**
     * Met à jour les labels d'information avec les données de l'ordonnance sélectionné.
     *
     * @param ordonnance L'ordonnance sélectionné, ou null si aucun.

    private void afficherDetailsOrdonnance(Ordonnance ordonnance) {
        if (ordonnance != null) {
            idLabel.setText(String.valueOf(ordonnance.getId()));
            medecinLabel.setText(ordonnance.getMedecin());
            patientLabel.setText(ordonnance.getPatient());
            dateEmissionLabel.setText(ordonnance.getDateEmission());
            dateValiditeLabel.setText(ordonnance.getDateValidite());
            prescriptionsLabel.setText(ordonnance.getPrescriptions()  != null ? ordonnance.getPrescriptions() : "");
            remarquesLabel.setText(ordonnance.getRemarques() != null ? ordonnance.getRemarques() : "");
        } else {
            idLabel.setText("");
            medecinLabel.setText("");
            patientLabel.setText("");
            dateEmissionLabel.setText("");
            dateValiditeLabel.setText("");
            prescriptionsLabel.setText("");
            remarquesLabel.setText("");
        }
    }*/

    private Stage stage;
    private Scene scene;
    private GestionPatients gestionPatients;
    private GestionMedecins gestionMedecins;

    /**
     * Initialise le contrôleur. Cette méthode est appelée automatiquement
     * après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        // Affiche les détails du patient sélectionné dans le label
        ordonnancelisteview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idLabel.setText(String.valueOf(newValue.getId()));
                medecinLabel.setText(newValue.getMedecin());
                patientLabel.setText(newValue.getPatient());
                dateEmissionLabel.setText(newValue.getDateEmission());
                dateValiditeLabel.setText(newValue.getDateValidite());
                prescriptionsLabel.setText(newValue.getPrescriptions()  != null ? newValue.getPrescriptions() : "");
                remarquesLabel.setText(newValue.getRemarques() != null ? newValue.getRemarques() : "");
            } else {
                idLabel.setText("");
                medecinLabel.setText("");
                patientLabel.setText("");
                dateEmissionLabel.setText("");
                dateValiditeLabel.setText("");
                prescriptionsLabel.setText("");
                remarquesLabel.setText("");
            }
        });
    }
    /*
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
        Ordonnance selected = ordonnancelisteview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionPatients.supprimerPatient(selected.getId()); // Supprime le patient via le service
            ordonnancelisteview.getSelectionModel().clearSelection();
        }
    }*/

    // --- Navigation ---

    @FXML
    void retourAccueil(ActionEvent event) throws IOException {
        chargerVue("/fr/univartois/butinfo/sae/cabinet/view/accueil.fxml");
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
        ordonnancelisteview.setItems(ordonnances.listerToutesLesOrdonnances());
    }

    public void setStage(Stage stage) { this.stage = stage; }
}
