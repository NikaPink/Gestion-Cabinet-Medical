package fr.univartois.butinfo.sae.cabinet.utils;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Module de recherche transversale pour patients et médecins.
 * Regroupe les fonctionnalités de recherche avancée.
 */
public class Recherche {

    private static final Logger log = LoggerFactory.getLogger(Recherche.class);

    private final GestionPatients gestionPatients;
    private final GestionMedecins gestionMedecins;

    /**
     * Constructeur : injecte les services nécessaires.
     */
    public Recherche(GestionPatients gestionPatients, GestionMedecins gestionMedecins) {
        this.gestionPatients = gestionPatients;
        this.gestionMedecins = gestionMedecins;
    }

    // -------------------------
    // Recherche patients
    // -------------------------

    /**
     * Recherche des patients par nom ou prénom.
     */
    public List<Patient> rechercherPatientParNom(String terme) {
        if (terme == null || terme.isBlank()) {
            log.error("Erreur de recherche : le terme de recherche est invalide.");
            return List.of();
        }
        return gestionPatients.rechercherParNom(terme);
    }

    /**
     * Recherche un patient par son numéro de sécurité sociale.
     */
    public Patient rechercherPatientParSecu(String numeroSecu) {
        if (numeroSecu == null || numeroSecu.isBlank()) {
            log.error("Erreur de recherche : le numéro de sécurité sociale fourni est invalide.");
            return null;
        }
        Patient p = gestionPatients.rechercherParNumeroSecu(numeroSecu);
        if (p == null) {
            log.warn("Aucun patient trouvé pour le numéro de sécu : {}", numeroSecu);
        }
        return p;
    }

    // -------------------------
    // Recherche médecins
    // -------------------------

    /**
     * Recherche des médecins par nom ou prénom.
     */
    public List<Medecin> rechercherMedecinParNom(String terme) {
        if (terme == null || terme.isBlank()) {
            log.error("Erreur de recherche : le terme de recherche est invalide.");
            return List.of();
        }
        return gestionMedecins.rechercherParNom(terme);
    }

    /**
     * Recherche des médecins par spécialité.
     */
    public List<Medecin> rechercherMedecinParSpecialite(Specialite specialite) {
        if (specialite == null) {
            log.error("Erreur de recherche : la spécialité fournie est nulle.");
            return List.of();
        }
        return gestionMedecins.rechercherParSpecialite(specialite);
    }

    /**
     * Recherche un médecin par son numéro d'ordre.
     */
    public Medecin rechercherMedecinParOrdre(String numeroOrdre) {
        if (numeroOrdre == null || numeroOrdre.isBlank()) {
            log.error("Erreur de recherche : le numéro d'ordre fourni est invalide.");
            return null;
        }
        Medecin m = gestionMedecins.rechercherParNumeroOrdre(numeroOrdre);
        if (m == null) {
            log.warn("Aucun médecin trouvé pour le n° d'ordre : {}", numeroOrdre);
        }
        return m;
    }

    @Override
    public String toString() {
        return "Module Recherche { patients=" + gestionPatients.getNombrePatients() +
               ", médecins=" + gestionMedecins.getNombreMedecins() + " }";
    }
}
