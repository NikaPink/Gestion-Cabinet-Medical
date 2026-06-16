package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MedicamentController {

    @FXML
    private ListView<Medicament> listMedicaments;

    @FXML
    private Label idLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label prixLabel;

    private GestionMedicaments gestionMedicaments;

    private Stage stage;

    private Scene scene;

    @FXML
    void initialize() {
        listMedicaments.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        idLabel.setText(String.valueOf(newValue.getId()));
                        nomLabel.setText(newValue.getNom());
                        descriptionLabel.setText(newValue.getDescription());
                        prixLabel.setText(newValue.getPrix() + " €");
                    } else {
                        idLabel.setText("");
                        nomLabel.setText("");
                        descriptionLabel.setText("");
                        prixLabel.setText("");
                    }
                });
    }

    @FXML
    void supprimer(ActionEvent event) {
        Medicament selected = listMedicaments.getSelectionModel().getSelectedItem();

        if (selected != null) {
            gestionMedicaments.listerTousLesMedicaments().remove(selected);
            listMedicaments.getSelectionModel().clearSelection();
        }
    }

    public void bind(GestionMedicaments medicaments) {
        this.gestionMedicaments = medicaments;
        listMedicaments.setItems(medicaments.listerTousLesMedicaments());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}