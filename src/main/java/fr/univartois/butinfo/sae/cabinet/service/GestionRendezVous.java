package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


/**
 * Service de gestion des rendez-vous médicaux.
 */
public class GestionRendezVous {

    private static final Logger log = LoggerFactory.getLogger(GestionRendezVous.class);

    private final ObservableList<RendezVous> rendezVous;

    /**
     * Constructeur : initialise la liste vide.
     */
    public GestionRendezVous() {
        this.rendezVous = FXCollections.observableArrayList();
    }

    // -------------------------
    // Gestion des RDV
    // -------------------------

    /**
     * Prend un rendez-vous si le créneau du médecin est libre.
     * @return le rendez-vous créé, ou null si le créneau est pris
     */
    public RendezVous prendreRendezVous(Patient patient, Medecin medecin,
                                         LocalDateTime dateHeure, int dureeMinutes, String motif) {
        // Vérification de disponibilité du médecin
        if (!estDisponible(medecin, dateHeure)) {
            log.error("ERREUR : Le Dr {} n'est pas disponible à ce créneau.", medecin.getNomComplet());
            return null;
        }
        RendezVous rdv = new RendezVous(patient, medecin, dateHeure, dureeMinutes, motif);
        rendezVous.addAll(rdv);
        log.info("Rendez-vous créé : {}", rdv);
        return rdv;
    }

    /**
     * Annule un rendez-vous par son ID.
     */
    public void annulerRendezVous(int id) {
        RendezVous rdv = rechercherParId(id);
        if (rdv == null) {
            log.error("ERREUR : Rendez-vous ID={} introuvable.", id);
            return;
        }
        rdv.annuler();
        log.info("Rendez-vous annulé : ID={}", id);
    }

    /**
     * Confirme un rendez-vous par son ID.
     */
    public void confirmerRendezVous(int id) {
        RendezVous rdv = rechercherParId(id);
        if (rdv == null) {
            log.error("ERREUR : Rendez-vous ID={} introuvable.", id);
            return;
        }
        rdv.confirmer();
        log.info("Rendez-vous confirmé : ID={}", id);
    }

    /**
     * Marque un rendez-vous comme terminé.
     */
    public boolean terminerRendezVous(int id) {
        RendezVous rdv = rechercherParId(id);
        if (rdv == null) {
            log.error("ERREUR : Rendez-vous ID={} introuvable.", id);
            return false;
        }
        rdv.terminer();
        log.info("Rendez-vous terminé : ID={}", id);
        return true;
    }

    // -------------------------
    // Recherche
    // -------------------------

    /**
     * Recherche un rendez-vous par son ID.
     */
    public RendezVous rechercherParId(int id) {
        for (RendezVous rdv : rendezVous) {
            if (rdv.getId() == id) return rdv;
        }
        return null;
    }

    /**
     * Retourne tous les rendez-vous d'un patient donné.
     */
    public ObservableList<RendezVous> getRendezVousPatient(Patient patient) {
        ObservableList<RendezVous> resultats = FXCollections.observableArrayList();
        for (RendezVous rdv : rendezVous) {
            if (rdv.getPatient().equals(patient)) resultats.addAll(rdv);
        }
        return resultats;
    }

    /**
     * Retourne tous les rendez-vous d'un médecin donné.
     */
    public ObservableList<RendezVous> getRendezVousMedecin(Medecin medecin) {
        ObservableList<RendezVous> resultats = FXCollections.observableArrayList();
        for (RendezVous rdv : rendezVous) {
            if (rdv.getMedecin().equals(medecin)) resultats.addAll(rdv);
        }
        return resultats;
    }

    /**
     * Retourne les rendez-vous filtrés par statut.
     */
    public ObservableList<RendezVous> getRendezVousParStatut(StatutRendezVous statut) {
        ObservableList<RendezVous> resultats = FXCollections.observableArrayList();
        for (RendezVous rdv : rendezVous) {
            if (rdv.getStatut() == statut) resultats.addAll(rdv);
        }
        return resultats;
    }

    /**
     * Vérifie si un médecin est disponible à une date/heure donnée.
     * Un médecin est indisponible si un RDV non annulé existe à la même heure.
     */
    public boolean estDisponible(Medecin medecin, LocalDateTime dateHeure) {
        for (RendezVous rdv : rendezVous) {
            if (rdv.getMedecin().equals(medecin) &&
                rdv.getDateHeure().equals(dateHeure) &&
                rdv.getStatut() != StatutRendezVous.ANNULE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retourne la liste complète des rendez-vous.
     */
    public ObservableList<RendezVous> listerTousLesRendezVous() {
        return FXCollections.observableArrayList(rendezVous);
    }

    /**
     * Retourne le nombre total de rendez-vous.
     */
    public int getNombreRendezVous() {
        return rendezVous.size();
    }

}
