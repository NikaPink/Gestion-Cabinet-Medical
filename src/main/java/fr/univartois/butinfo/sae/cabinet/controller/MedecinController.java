package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

import java.util.List;

/**
 * Contrôleur pour la vue Master-Detail de gestion des médecins.
 */
public class MedecinController {

    @FXML
    private ListView<Medecin> medecinListView;

    @FXML
    private Label idLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label telephoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label ordreLabel;

    @FXML
    private Label specialiteLabel;

    @FXML
    private Label tarifLabel;

    private ObservableList<Medecin> medecins;

    /**
     * Initialise le contrôleur. Cette méthode est appelée automatiquement
     * après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        medecinListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            afficherDetailsMedecin(newValue);
        });
    }

    /**
     * Stocke la liste des médecins à afficher et configure la ListView.
     *
     * @param medecinsList La liste des médecins à afficher.
     */
    public void setMedecins(List<Medecin> medecinsList) {
        this.medecins = FXCollections.observableArrayList(medecinsList);
        medecinListView.setItems(this.medecins);

        medecinListView.setCellFactory(list -> {
            return new ListCell<>() {
                @Override
                protected void updateItem(Medecin medecin, boolean empty) {
                    super.updateItem(medecin, empty);
                    if (empty || (medecin == null)) {
                        setText(null);
                    } else {
                        setText(medecin.getNom());
                    }
                }
            };
        });
    }

    /**
     * Met à jour les labels d'information avec les données du médecin sélectionné.
     *
     * @param medecin Le médecin sélectionné, ou null si aucun.
     */
    private void afficherDetailsMedecin(Medecin medecin) {
        if (medecin != null) {
            idLabel.setText(String.valueOf(medecin.getId()));
            nomLabel.setText(medecin.getNom());
            prenomLabel.setText(medecin.getPrenom());
            telephoneLabel.setText(medecin.getTelephone());
            emailLabel.setText(medecin.getEmail());
            ordreLabel.setText(medecin.getNumeroOrdre());
            specialiteLabel.setText(medecin.getSpecialite() != null ? medecin.getSpecialite().getLibelle() : "");
            tarifLabel.setText(String.format("%.2f €", medecin.getTarifHoraire()));
        } else {
            idLabel.setText("");
            nomLabel.setText("");
            prenomLabel.setText("");
            telephoneLabel.setText("");
            emailLabel.setText("");
            ordreLabel.setText("");
            specialiteLabel.setText("");
            tarifLabel.setText("");
        }
    }
}
