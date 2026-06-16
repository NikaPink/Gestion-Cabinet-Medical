/*package fr.univartois.butinfo.sae.cabinet.modele;

import fr.univartois.butinfo.sae.cabinet.Controller.PatientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PatientAppplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(""));
        Parent viewContent = fxmlLoader.load();

        Scene scene = new Scene(viewContent, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Liste des patients");

        PatientController controller = fxmlLoader.getController();
        Patient patients = Patient.defaultPatient();
        controller.bind(patients);
        controller.setStage(stage);
        controller.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
} */

//je tenais à dire que on doit supprimer cette classe. C'est juste une seule App.