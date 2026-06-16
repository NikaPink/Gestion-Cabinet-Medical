package fr.univartois.butinfo.sae.cabinet.controller;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;

public class MedicamentController {

    @FXML
    private ListView<Medicament> medicamentListView;

    @FXML
    private Label idLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label principeLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private TextField recherche;

    private GestionMedicaments gestionMedicaments;

    private Stage stage;
    private Scene scene;

    @FXML
    void initialize() {
        medicamentListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        idLabel.setText(String.valueOf(newValue.getId()));
                        nomLabel.setText(newValue.getNom());
                        principeLabel.setText(newValue.getPrincipeActif());
                        prixLabel.setText(newValue.getPrix() + " €");
                    } else {
                        idLabel.setText("");
                        nomLabel.setText("");
                        principeLabel.setText("");
                        prixLabel.setText("");
                    }
                });
    }

    @FXML
    void supprimer(ActionEvent event) {
        Medicament selected = medicamentListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionMedicaments.listerTousLesMedicaments().remove(selected);
            medicamentListView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void ajouter(ActionEvent event) {
        TextInputDialog dNom = new TextInputDialog();
        dNom.setTitle("Nouveau");
        dNom.setHeaderText("Étape 1/3");
        dNom.setContentText("Entrez le nom :");
        dNom.showAndWait();
        String nom = dNom.getResult();

        if (nom != null && !nom.trim().isEmpty()) {

            TextInputDialog dPrincipe = new TextInputDialog();
            dPrincipe.setTitle("Nouveau");
            dPrincipe.setHeaderText("Étape 2/3");
            dPrincipe.setContentText("Entrez le principe actif :");
            dPrincipe.showAndWait();
            String principe = dPrincipe.getResult();

            if (principe != null && !principe.trim().isEmpty()) {

                TextInputDialog dPrix = new TextInputDialog("10.0");
                dPrix.setTitle("Nouveau");
                dPrix.setHeaderText("Étape 3/3");
                dPrix.setContentText("Entrez le prix :");
                dPrix.showAndWait();
                String prixString = dPrix.getResult();

                if (prixString != null && !prixString.trim().isEmpty()) {
                    double prix = Double.parseDouble(prixString.trim());

                    Medicament nouveau = new Medicament(nom.trim(), principe.trim(), prix);
                    gestionMedicaments.listerTousLesMedicaments().add(nouveau);
                }
            }
        }
    }

    @FXML
    void retourAccueil(ActionEvent event) throws IOException {
        stage.setScene(scene);

    }

    public void bind(GestionMedicaments medicaments) {
        this.gestionMedicaments = medicaments;
        medicamentListView.setItems(medicaments.listerTousLesMedicaments());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}