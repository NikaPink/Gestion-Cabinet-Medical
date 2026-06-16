package fr.univartois.butinfo.sae.cabinet.utils;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Module de recherche pour patients et médecins.
 */
public class Recherche { 

    private static final Logger logger = LoggerFactory.getLogger(GestionPatients.class);
    private final GestionPatients gestionPatients;
    private final GestionMedecins gestionMedecins;

    /**
     * * @param gestionPatients Le service de gestion des patients.
     * @param gestionMedecins Le service de gestion des médecins.
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
     * * @param terme Le nom ou prénom recherché.
     * @return Une liste de patients correspondant au terme, ou une liste vide si aucun résultat.
     */
    public List<Patient> rechercherPatientParNom(String terme) {
        if (terme==null){
            logger.error("Le terme est nul");
            return null;
        }
        logger.info("Le patient à était trouvé");
        return gestionPatients.rechercherParNom(terme);
    }

    /**
     * Recherche un patient par son numéro de sécurité sociale.
     * * @param numeroSecu Le numéro de sécurité sociale du patient.
     * @return Le patient correspondant, ou {@code null} si aucun patient n'est trouvé.
     */
    public Patient rechercherPatientParSecu(String numeroSecu) {
        if (numeroSecu == null) {
            logger.error("Aucun patient trouvé avec ce numéro de sécu");
            return null;
        } else {
            logger.info("Le patient à était trouvé");
        }
        return gestionPatients.rechercherParNumeroSecu(numeroSecu);
    }

    // -------------------------
    // Recherche médecins
    // -------------------------

    /**
     * Recherche des médecins par nom ou prénom.
     * * @param terme Le nom ou prénom recherché.
     * @return Une liste de médecins correspondant au terme, ou une liste vide si aucun résultat.
     */
    public List<Medecin> rechercherMedecinParNom(String terme) {
        if (terme==null){
            logger.error("Le terme est null");
            return null;
        }
        logger.info("Le médecin à était trouvé");
        return gestionMedecins.rechercherParNom(terme);
    }

    /**
     * Recherche des médecins par spécialité.
     * * @param specialite La spécialité recherchée.
     * @return Une liste de médecins exerçant cette spécialité, ou une liste vide si aucun résultat.
     */
    public List<Medecin> rechercherMedecinParSpecialite(Specialite specialite) {
        if (specialite==null){
            logger.error("Aucune spécialité trouvé");
            return null;
        }
        else {
            logger.info("Un médecin à était touvé pour la spécialité : {} ", specialite);
        }
        return gestionMedecins.rechercherParSpecialite(specialite);
    }

    /**
     * Recherche un médecin par son numéro d'ordre.
     * * @param numeroOrdre Le numéro d'ordre du médecin.
     * @return Le médecin correspondant, ou {@code null} si aucun médecin n'est trouvé.
     */
    public Medecin rechercherMedecinParOrdre(String numeroOrdre) {
        if (numeroOrdre!=null) {
            logger.info("Un médecin à était trouvé avec son n° d'ordre : {}", numeroOrdre);
        } else {
            logger.error("Aucun trouvé avec le n° d'ordre");
            return null;
        }
        return gestionMedecins.rechercherParNumeroOrdre(numeroOrdre);
    }

    // -------------------------
    // Affichage des résultats
    // -------------------------

    /*private void afficherResultatsPatients(List<Patient> liste, String titre) {
        System.out.println("--- " + titre + " (" + liste.size() + " résultat(s)) ---");
        if (liste.isEmpty()) {
            System.out.println("  Aucun résultat.");
        } else {
            for (Patient p : liste) System.out.println("  " + p);
        }
    }

    private void afficherResultatsMedecins(List<Medecin> liste, String titre) {
        System.out.println("--- " + titre + " (" + liste.size() + " résultat(s)) ---");
        if (liste.isEmpty()) {
            System.out.println("  Aucun résultat.");
        } else {
            for (Medecin m : liste) System.out.println("  " + m);
        }
    }
     */

    /**
     * Retourne une représentation textuelle du module de recherche.
     * * @return Une chaîne de caractères indiquant le nombre de patients et de médecins gérés.
     */
    @Override
    public String toString() {
        return "Module Recherche { patients=" + gestionPatients.getNombrePatients() +
                ", médecins=" + gestionMedecins.getNombreMedecins() + " }";
    }
}