
package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
public class PatientController {
    @FXML
    private Label adresseLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private ListView<Patient> patientListView;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label secuLabel;

    @FXML
    private Label tauxLabel;

    @FXML
    private Label telephoneLabel;

    private ObservableList<Patient> patients;

    private Stage stage;
    private Scene scene;
    private GestionPatients gestionPatients;

    /**
     * Initialise le contrôleur. Cette méthode est appelée automatiquement
     * après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        patientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                adresseLabel.setText(newValue.getAdresse());
                emailLabel.setText(newValue.getEmail());
                idLabel.setText(String.valueOf(newValue.getId()));
                nomLabel.setText(newValue.getNom());
                prenomLabel.setText(newValue.getPrenom());
                secuLabel.setText(newValue.getNumeroSecu());
                telephoneLabel.setText(newValue.getTelephone());
                tauxLabel.setText(String.format("%.2f %%", newValue.getTauxRemboursement() * 100));
            } else {
                adresseLabel.setText("");
                emailLabel.setText("");
                idLabel.setText("");
                nomLabel.setText("");
                prenomLabel.setText("");
                secuLabel.setText("");
                telephoneLabel.setText("");
                tauxLabel.setText("");
            }
        });

        Platform.runLater(() -> {
            TextField rechercheField = findRechercheField();
            if (rechercheField != null) {
                rechercheField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filtrerPatients(newValue);
                });
            }
        });
    }

    private TextField findRechercheField() {
        if (patientListView != null && patientListView.getParent() instanceof VBox) {
            VBox vbox = (VBox) patientListView.getParent();
            for (Node node : vbox.getChildren()) {
                if (node instanceof TextField) {
                    return (TextField) node;
                }
            }
        }
        return null;
    }

    private void filtrerPatients(String query) {
        if (query == null || query.trim().isEmpty()) {
            patientListView.setItems(this.patients);
        } else {
            ObservableList<Patient> filtered = FXCollections.observableArrayList();
            String lower = query.toLowerCase();
            for (Patient m : this.patients) {
                if (m.getNom().toLowerCase().contains(lower) ||
                        m.getPrenom().toLowerCase().contains(lower)) {
                    filtered.add(m);
                }
            }
            patientListView.setItems(filtered);
        }
    }

    /**
     * Lie le service de gestion des médecins au contrôleur et initialise la liste d'affichage.
     *
     * @param gestionPatients Le service de gestion des médecins.
     */
    public void bind(GestionPatients gestionPatients) {
        this.gestionPatients = gestionPatients;
        this.patients = gestionPatients.listerTousLesPatients();
        patientListView.setItems(this.patients);

        patientListView.setCellFactory(list -> {
            return new ListCell<>() {
                @Override
                protected void updateItem(Patient p, boolean empty) {
                    super.updateItem(p, empty);
                    if (empty || (p == null)) {
                        setText(null);
                    } else {
                        setText(p.getNomComplet());
                    }
                }
            };
        });
    }



    @FXML
    void retourAccueil(ActionEvent event) {
        stage.setScene(scene);
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/patient-edit.fxml"));
        Parent viewContent = fxmlLoader.load();

        Scene editScene = new Scene(viewContent, 800, 600);
        stage.setScene(editScene);

        PatientEditController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(patientListView.getScene());
        controller.bind(gestionPatients);
        controller.setPatients(null);
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        Patient selected = patientListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Pas de médecin sélectionné");
            alert.setContentText("Veuillez sélectionner un médecin dans la liste.");
            alert.showAndWait();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/patient-edit.fxml"));
        Parent viewContent = fxmlLoader.load();

        Scene editScene = new Scene(viewContent, 800, 600);
        stage.setScene(editScene);

        PatientEditController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(patientListView.getScene());
        controller.bind(gestionPatients);
        controller.setPatients(selected);
    }

    @FXML
    void supprimer(ActionEvent event) {
        Patient selected = patientListView.getSelectionModel().getSelectedItem();
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
                gestionPatients.supprimerPatient(selected.getId());
                patientListView.getSelectionModel().clearSelection();
            }
        });
    }

    /**
     * Stocke la liste des médecins à afficher et configure la ListView (méthode de fallback).
     *
     * @param patientsList La liste des médecins à afficher.
     */
    public void setPatients(List<Patient> patientsList) {
        if (patientsList instanceof ObservableList) {
            this.patients = (ObservableList<Patient>) patientsList;
        } else {
            this.patients = FXCollections.observableArrayList(patientsList);
        }
        patientListView.setItems(this.patients);

        patientListView.setCellFactory(list -> {
            return new ListCell<>() {
                @Override
                protected void updateItem(Patient p, boolean empty) {
                    super.updateItem(p, empty);
                    if (empty || (p == null)) {
                        setText(null);
                    } else {
                        setText(p.getNomComplet());
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
