package main.java.fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import main.java.fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Service de gestion des médecins du cabinet.
 */
public class GestionMedecins {

    private static final Logger log = LoggerFactory.getLogger(GestionMedecins.class);

    private final ObservableList<Medecin> medecins;

    /**
     * Constructeur : initialise la liste vide.
     */
    public GestionMedecins() {
        this.medecins = new ObservableList<Medecin>();
    }

    // -------------------------
    // CRUD
    // -------------------------

    /**
     * Enregistre un nouveau médecin (vérifie l'unicité du numéro d'ordre).
     */
    public void ajouterMedecin(Medecin medecin) {
        if (rechercherParNumeroOrdre(medecin.getNumeroOrdre()) != null) {
            log.error("ERREUR : Un médecin avec ce numéro d'ordre existe déjà.");
            return;
        }
        medecins.addAll(medecin);
        log.info("Médecin ajouté : Dr {}", medecin.getNomComplet());
    }

    /**
     * Recherche un médecin par son ID.
     */
    public Medecin rechercherParId(int id) {
        for (Medecin m : medecins) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    /**
     * Recherche un médecin par son numéro d'ordre.
     */
    public Medecin rechercherParNumeroOrdre(String numeroOrdre) {
        for (Medecin m : medecins) {
            if (m.getNumeroOrdre().equalsIgnoreCase(numeroOrdre)) return m;
        }
        return null;
    }

    /**
     * Recherche des médecins par nom ou prénom.
     */
    public ObservableList<Medecin> rechercherParNom(String terme) {
        ObservableList<Medecin> resultats = new ObservableList<Medecin>();
        String termeLower = terme.toLowerCase();
        for (Medecin m : medecins) {
            if (m.getNom().toLowerCase().contains(termeLower) ||
                m.getPrenom().toLowerCase().contains(termeLower)) {
                resultats.addAll(m);
            }
        }
        return resultats;
    }

    /**
     * Retourne tous les médecins d'une spécialité donnée.
     */
    public ObservableList<Medecin> rechercherParSpecialite(Specialite specialite) {
        ObservableList<Medecin> resultats = new ObservableList<Medecin>();
        for (Medecin m : medecins) {
            if (m.getSpecialite() == specialite) {
                resultats.addAll(m);
            }
        }
        return resultats;
    }

    /**
     * Met à jour les informations d'un médecin.
     */
    public void mettreAJourMedecin(int id, String nouveauNom, String nouveauPrenom, double nouveauTarifConsultation,
                                       String nouveauTel, String nouveauEmail,
                                       Specialite nouvelleSpecialite) {
        Medecin m = rechercherParId(id);
        if (m == null) {
            log.error("ERREUR : Médecin ID={} introuvable.", id);
            return;
        }
        m.setNom(nouveauNom);
        m.setPrenom(nouveauPrenom);
        m.setTarifHoraire(nouveauTarifConsultation);
        m.setTelephone(nouveauTel);
        m.setEmail(nouveauEmail);
        m.setSpecialite(nouvelleSpecialite);
        log.info("Médecin mis à jour : Dr {}", m.getNomComplet());
    }

    /**
     * Supprime un médecin par son ID.
     */
    public boolean supprimerMedecin(int id) {
        Medecin m = rechercherParId(id);
        if (m == null) {
            log.error("ERREUR : Médecin ID={} introuvable.", id);
            return false;
        }
        medecins.remove(m);
        log.info("Médecin supprimé : Dr {}", m.getNomComplet());
        return true;
    }

    /**
     * Retourne la liste complète des médecins.
     */
    public List<Medecin> listerTousLesMedecins() {
        return new ArrayList<>(medecins);
    }

    /**
     * Retourne le nombre total de médecins.
     */
    public int getNombreMedecins() {
        return medecins.size();
    }

}
