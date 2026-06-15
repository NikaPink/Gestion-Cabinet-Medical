package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.controller.MedecinController;
import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

/**
 * Application JavaFX principale pour la gestion du cabinet médical.
 */
public class CabinetApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chargement du fichier FXML pour la gestion des médecins
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/medecins.fxml"));
        Parent root = fxmlLoader.load();

        // Récupération du contrôleur associé
        MedecinController controller = fxmlLoader.getController();

        // Création de quelques médecins pour le test (données initiales)
        List<Medecin> medecinsTest = List.of(
                new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                        "ORD-001", Specialite.GENERALISTE, 30.0),
                new Medecin("Martin", "Sophie", "0398765432", "s.martin@cabinet.fr",
                        "ORD-002", Specialite.CARDIOLOGUE, 50.0),
                new Medecin("Bernard", "Lucie", "0367891234", "l.bernard@cabinet.fr",
                        "ORD-003", Specialite.PEDIATRE, 40.0)
        );

        // Liaison de la liste de médecins avec le contrôleur
        controller.setMedecins(medecinsTest);

        // Configuration de la scène et affichage de la fenêtre principale
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Gestion des Médecins - Cabinet Médical");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
