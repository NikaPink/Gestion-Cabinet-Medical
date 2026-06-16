package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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

    private Stage stage;
    private Scene scene;
    private GestionMedecins gestionMedecins;

    /**
     * Initialise le contrôleur. Cette méthode est appelée automatiquement
     * après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        medecinListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            afficherDetailsMedecin(newValue);
        });

        Platform.runLater(() -> {
            TextField rechercheField = findRechercheField();
            if (rechercheField != null) {
                rechercheField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filtrerMedecins(newValue);
                });
            }
        });
    }

    private TextField findRechercheField() {
        if (medecinListView != null && medecinListView.getParent() instanceof VBox) {
            VBox vbox = (VBox) medecinListView.getParent();
            for (javafx.scene.Node node : vbox.getChildren()) {
                if (node instanceof TextField) {
                    return (TextField) node;
                }
            }
        }
        return null;
    }

    private void filtrerMedecins(String query) {
        if (query == null || query.trim().isEmpty()) {
            medecinListView.setItems(this.medecins);
        } else {
            ObservableList<Medecin> filtered = FXCollections.observableArrayList();
            String lower = query.toLowerCase();
            for (Medecin m : this.medecins) {
                if (m.getNom().toLowerCase().contains(lower) ||
                    m.getPrenom().toLowerCase().contains(lower)) {
                    filtered.add(m);
                }
            }
            medecinListView.setItems(filtered);
        }
    }

    /**
     * Lie le service de gestion des médecins au contrôleur et initialise la liste d'affichage.
     *
     * @param gestionMedecins Le service de gestion des médecins.
     */
    public void bind(GestionMedecins gestionMedecins) {
        this.gestionMedecins = gestionMedecins;
        this.medecins = FXCollections.observableArrayList(gestionMedecins.listerTousLesMedecins());
        medecinListView.setItems(this.medecins);

        medecinListView.setCellFactory(list -> {
            return new ListCell<>() {
                @Override
                protected void updateItem(Medecin medecin, boolean empty) {
                    super.updateItem(medecin, empty);
                    if (empty || (medecin == null)) {
                        setText(null);
                    } else {
                        setText(medecin.getNomComplet());
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
            specialiteLabel.setText(medecin.getSpecialite() != null ? medecin.getSpecialite().toString() : "");
            tarifLabel.setText(medecin.getTarifHoraire() + " €");
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

    @FXML
    void retourAccueil(ActionEvent event) {
        stage.setScene(scene);
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/medecin-edit.fxml"));
        Parent viewContent = fxmlLoader.load();

        Scene editScene = new Scene(viewContent, 800, 600);
        stage.setScene(editScene);

        MedecinEditController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(medecinListView.getScene());
        controller.bind(gestionMedecins, this.medecins);
        controller.setMedecin(null);
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        Medecin selected = medecinListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Pas de médecin sélectionné");
            alert.setContentText("Veuillez sélectionner un médecin dans la liste.");
            alert.showAndWait();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/medecin-edit.fxml"));
        Parent viewContent = fxmlLoader.load();

        Scene editScene = new Scene(viewContent, 800, 600);
        stage.setScene(editScene);

        MedecinEditController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(medecinListView.getScene());
        controller.bind(gestionMedecins, this.medecins);
        controller.setMedecin(selected);
    }

    @FXML
    void supprimer(ActionEvent event) {
        Medecin selected = medecinListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Pas de médecin sélectionné");
            alert.setContentText("Veuillez sélectionner un médecin dans la liste.");
            alert.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.initOwner(stage);
        confirm.setTitle("Confirmation de suppression");
        confirm.setHeaderText("Supprimer le médecin ?");
        confirm.setContentText("Êtes-vous sûr de vouloir supprimer le médecin " + selected.getNomComplet() + " ?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                gestionMedecins.supprimerMedecin(selected.getId());
                this.medecins.remove(selected);
                medecinListView.getSelectionModel().clearSelection();
            }
        });
    }

    /**
     * Stocke la liste des médecins à afficher et configure la ListView (méthode de fallback).
     *
     * @param medecinsList La liste des médecins à afficher.
     */
    public void setMedecins(List<Medecin> medecinsList) {
        if (medecinsList instanceof ObservableList) {
            this.medecins = (ObservableList<Medecin>) medecinsList;
        } else {
            this.medecins = FXCollections.observableArrayList(medecinsList);
        }
        medecinListView.setItems(this.medecins);

        medecinListView.setCellFactory(list -> {
            return new ListCell<>() {
                @Override
                protected void updateItem(Medecin medecin, boolean empty) {
                    super.updateItem(medecin, empty);
                    if (empty || (medecin == null)) {
                        setText(null);
                    } else {
                        setText(medecin.getNomComplet());
                    }
                }
            };
        });
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
