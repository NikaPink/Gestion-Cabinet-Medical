package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.controller.AccueilController;
import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.*;
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
        // Chargement du fichier FXML pour l'accueil
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/sae/cabinet/view/accueil.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 600);

        // Initialisation des services
        GestionMedecins gestionMedecins = new GestionMedecins();
        GestionPatients gestionPatients = new GestionPatients();
        GestionRendezVous gestionRdv = new GestionRendezVous();
        GestionMedicaments gestionMedicaments = new GestionMedicaments();
        GestionOrdonnances gestionOrdonnances = new GestionOrdonnances();

        // Ajout de quelques données initiales de test
        gestionMedecins.ajouterMedecin(new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                "ORD-001", Specialite.GENERALISTE, 30.0));
        gestionMedecins.ajouterMedecin(new Medecin("Martin", "Sophie", "0398765432", "s.martin@cabinet.fr",
                "ORD-002", Specialite.CARDIOLOGUE, 50.0));
        gestionMedecins.ajouterMedecin(new Medecin("Bernard", "Lucie", "0367891234", "l.bernard@cabinet.fr",
                "ORD-003", Specialite.PEDIATRE, 40.0));

        // Récupération du contrôleur associé et injection des dépendances
        AccueilController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        controller.setScene(scene);
        controller.setGestionMedecins(gestionMedecins);
        controller.setGestionPatients(gestionPatients);
        controller.setGestionRdv(gestionRdv);
        controller.setGestionMedicaments(gestionMedicaments);
        controller.setGestionOrdonnances(gestionOrdonnances);

        // Configuration de la scène et affichage de la fenêtre principale
        primaryStage.setTitle("Cabinet Médical");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
