package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.modele.LignePrescription;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Service de gestion des ordonnances médicales.
 */
public class GestionOrdonnances {

    private static final Logger log = LoggerFactory.getLogger(GestionOrdonnances.class);

    private final ObservableList<Ordonnance> ordonnances;

    /**
     * Constructeur : initialise la liste observable vide.
     */
    public GestionOrdonnances() {
        this.ordonnances = FXCollections.observableArrayList();
    }

    // -------------------------
    // Gestion des ordonnances
    // -------------------------

    /**
     * Crée une nouvelle ordonnance vide (sans prescriptions) et l'enregistre.
     * @return l'ordonnance créée
     */
    public Ordonnance creerOrdonnance(Medecin medecin, Patient patient,
                                      LocalDate dateValidite, String remarques) {
        Ordonnance ordo = new Ordonnance(medecin, patient, dateValidite, remarques);
        ordonnances.add(ordo);
        log.info("Ordonnance créée (ID={}) pour {} par Dr {}", ordo.getId(),
                patient.getNomComplet(), medecin.getNomComplet());
        return ordo;
    }

    /**
     * Ajoute une ligne de prescription à une ordonnance existante.
     */
    public void ajouterPrescription(int ordonnanceId, Medicament medicament, int quantite,
                                    String posologie, int dureeJours, String instructions) {
        Ordonnance ordo = rechercherParId(ordonnanceId);
        if (ordo == null) {
            log.error("ERREUR : Ordonnance ID={} introuvable.", ordonnanceId);
            return;
        }
        LignePrescription ligne = new LignePrescription(medicament, quantite, posologie, dureeJours, instructions);
        ordo.ajouterPrescription(ligne);
        log.info("Prescription ajoutée : {} → ordonnance ID={}", medicament.getNom(), ordonnanceId);
    }

    /**
     * Recherche une ordonnance par son ID.
     */
    public Ordonnance rechercherParId(int id) {
        for (Ordonnance o : ordonnances) {
            if (o.getId() == id) return o;
        }
        return null;
    }

    /**
     * Retourne toutes les ordonnances d'un patient sous forme d'ObservableList.
     */
    public ObservableList<Ordonnance> getOrdonnancesPatient(Patient patient) {
        ObservableList<Ordonnance> resultats = FXCollections.observableArrayList();
        for (Ordonnance o : ordonnances) {
            if (o.getPatient().equals(patient)) resultats.add(o);
        }
        return resultats;
    }

    /**
     * Retourne toutes les ordonnances émises par un médecin sous forme d'ObservableList.
     */
    public ObservableList<Ordonnance> getOrdonnancesMedecin(Medecin medecin) {
        ObservableList<Ordonnance> resultats = FXCollections.observableArrayList();
        for (Ordonnance o : ordonnances) {
            if (o.getMedecin().equals(medecin)) resultats.add(o);
        }
        return resultats;
    }

    /**
     * Retourne uniquement les ordonnances encore valides à ce jour sous forme d'ObservableList.
     */
    public ObservableList<Ordonnance> getOrdonnancesValides() {
        ObservableList<Ordonnance> resultats = FXCollections.observableArrayList();
        for (Ordonnance o : ordonnances) {
            if (o.estValide()) resultats.add(o);
        }
        return resultats;
    }

    /**
     * Retourne la liste complète des ordonnances.
     */
    public ObservableList<Ordonnance> listerToutesLesOrdonnances() {
        return ordonnances;
    }

    /**
     * Retourne le nombre total d'ordonnances.
     */
    public int getNombreOrdonnances() {
        return ordonnances.size();
    }
}
