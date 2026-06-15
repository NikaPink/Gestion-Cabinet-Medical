package fr.univartois.butinfo.sae.cabinet.modele;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewPatientController {

    private Stage stage;
    private Scene mainScene;

    @FXML private TextField name;
    @FXML private TextField description;

    private Patient patients;

    public void setStage(Stage stage) { this.stage = stage; }
    public void setMainScene(Scene mainScene) { this.mainScene = mainScene; }
    public void bind(Patient patients) { this.patients = patients; }

    @FXML
    void cancel() {
        stage.setScene(mainScene);
    }

    @FXML
    void validate() {
        if (!name.getText().isEmpty()) {
            patients.addPatient(new Patient(name.getText(), description.getText()));
            stage.setScene(mainScene);
        }
    }
}