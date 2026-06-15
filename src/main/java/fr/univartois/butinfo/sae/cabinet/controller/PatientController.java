package fr.univartois.butinfo.sae.cabinet.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import fr.univartois.butinfo.sae.cabinet.modele.NewPatientController;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class PatientController { // Héritage supprimé

    private Stage stage;
    private Scene scene;

    @FXML private ListView<Patient> patientsList;
    @FXML private Label labelName;
    @FXML private Label descriptionLabel;
    @FXML private ImageView imageView;
    @FXML private Label sizeLabel;
    @FXML private Label weightLabel;

    private GestionPatients patients;

    public void setStage(Stage stage) { this.stage = stage; }
    public void setScene(Scene scene) { this.scene = scene; }

    public void bind(Patient patients) {
        this.patients = patients;
        patientsList.setItems(patients.getPatient());

        patientsList.getSelectionModel().selectedItemProperty().addListener((o, p, n) -> {
            if (n != null) {
                labelName.setText(n.getNom());
                imageView.setImage(new Image(getClass().getResource("/view/images/" + n.getPrenom().toLowerCase() + ".png").toExternalForm(), 250, 250, true, false));
            }
        });
    }

    @FXML
    void addPatient() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(""));
        Parent viewContent = fxmlLoader.load();
        Scene addPokemonScene = new Scene(viewContent, 800, 600);
        stage.setScene(addPokemonScene);

        NewPatientController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(scene);
        controller.bind(patients);
    }

    @FXML
    void removePatient() {
        Patient selected = patientsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            patients.removePatient(selected);
        }
    }
}