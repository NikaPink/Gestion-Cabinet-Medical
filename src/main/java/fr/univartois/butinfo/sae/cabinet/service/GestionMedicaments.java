package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import javafx.collections.ObservableList;


/**
 * Service de gestion des médicaments disponibles dans le système.
 */
public class GestionMedicaments {

    private static final Logger log = LoggerFactory.getLogger(GestionMedicaments.class);

    private final ObservableList<Medicament> medicaments;

    /**
     * Constructeur : initialise la liste vide.
     */
    public GestionMedicaments() {
        this.medicaments= FXCollections.observableArrayList();
    }

    // -------------------------
    // CRUD
    // -------------------------

    /**
     * Ajoute un médicament s'il n'existe pas déjà (même nom + principe actif).
     */
    public void ajouterMedicament(Medicament medicament) {
        if (medicaments.contains(medicament)) {
            log.error("ERREUR : Ce médicament existe déjà.");
            return;
        }
        medicaments.addAll(medicament);
        log.info("Médicament ajouté : {}", medicament.getNom());
    }

    /**
     * Recherche un médicament par son ID.
     */
    public Medicament rechercherParId(int id) {
        for (Medicament m : medicaments) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    /**
     * Recherche des médicaments dont le nom contient le terme donné.
     */
    public ObservableList<Medicament> rechercherParNom(String terme) {
        ObservableList<Medicament> resultats = FXCollections.observableArrayList();
        String termeLower = terme.toLowerCase();
        for (Medicament m : medicaments) {
            if (m.getNom().toLowerCase().contains(termeLower)) {
                resultats.addAll(m);
            }
        }
        return resultats;
    }


    /**
     * Supprime un médicament par son ID.
     */
    public boolean supprimerMedicament(int id) {
        Medicament m = rechercherParId(id);
        if (m == null) {
            log.error("ERREUR : Médicament ID={} introuvable.", id);
            return false;
        }
        medicaments.removeAll(m);
        log.info("Médicament supprimé : {}", m.getNom());
        return true;
    }

    /**
     * Retourne la liste complète des médicaments.
     */
    public ObservableList<Medicament> listerTousLesMedicaments() {
        return FXCollections.observableArrayList(medicaments);
    }

    /**
     * Retourne le nombre total de médicaments.
     */
    public int getNombreMedicaments() {
        return medicaments.size();
    }


}
