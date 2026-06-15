package fr.univartois.butinfo.sae.cabinet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.application.Application;
import javafx.stage.Stage;


public class CabinetApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent viewContent = fxmlLoader.load();

        // Ensuite, on la place dans une Scene...
        Scene scene = new Scene(viewContent, 1000, 800);
        // que l'on place elle-même dans la fenêtre.
        stage.setScene(scene);

        // On peut ensuite donner un titre à la fenêtre.
        stage.setTitle("Gestion");
        Controller controlleur=fxmlLoader.getController();
        controlleur.setScene(scene);
        // Enfin, on affiche la fenêtre.
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
