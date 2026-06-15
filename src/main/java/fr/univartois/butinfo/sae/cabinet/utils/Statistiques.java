package fr.univartois.butinfo.sae.cabinet.utils;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;

/**
 * Module de statistiques du cabinet médical.
 * Agrège des informations issues des différents services pour produire un tableau de bord.
 */
public class Statistiques {

    private final GestionPatients gestionPatients;
    private final GestionMedecins gestionMedecins;
    private final GestionMedicaments gestionMedicaments;
    private final GestionRendezVous gestionRendezVous;
    private final GestionOrdonnances gestionOrdonnances;

    /**
     * Constructeur : injecte tous les services nécessaires.
     */
    public Statistiques(GestionPatients gestionPatients,
                        GestionMedecins gestionMedecins,
                        GestionMedicaments gestionMedicaments,
                        GestionRendezVous gestionRendezVous,
                        GestionOrdonnances gestionOrdonnances) {
        this.gestionPatients = gestionPatients;
        this.gestionMedecins = gestionMedecins;
        this.gestionMedicaments = gestionMedicaments;
        this.gestionRendezVous = gestionRendezVous;
        this.gestionOrdonnances = gestionOrdonnances;
    }

    /**
     * Retourne un résumé complet des statistiques du cabinet sous forme de chaîne de caractères.
     */
    public String getStatistiques() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════╗\n");
        sb.append("║        TABLEAU DE BORD - CABINET MÉDICAL  ║\n");
        sb.append("╚══════════════════════════════════════════╝\n");

        // -- Chiffres globaux --
        sb.append("\n📋 CHIFFRES GLOBAUX :\n");
        sb.append("  • Patients enregistrés   : ").append(gestionPatients.getNombrePatients()).append("\n");
        sb.append("  • Médecins enregistrés   : ").append(gestionMedecins.getNombreMedecins()).append("\n");
        sb.append("  • Médicaments référencés : ").append(gestionMedicaments.getNombreMedicaments()).append("\n");
        sb.append("  • Rendez-vous total      : ").append(gestionRendezVous.getNombreRendezVous()).append("\n");
        sb.append("  • Ordonnances émises     : ").append(gestionOrdonnances.getNombreOrdonnances()).append("\n");

        // -- Détail des rendez-vous par statut --
        sb.append("\n📅 RENDEZ-VOUS PAR STATUT :\n");
        for (StatutRendezVous statut : StatutRendezVous.values()) {
            int nb = gestionRendezVous.getRendezVousParStatut(statut).size();
            sb.append("  • ").append(statut.getLibelle()).append(" : ").append(nb).append("\n");
        }

        // -- Médecins par spécialité --
        sb.append("\n🩺 MÉDECINS PAR SPÉCIALITÉ :\n");
        for (Specialite sp : Specialite.values()) {
            int nb = gestionMedecins.rechercherParSpecialite(sp).size();
            if (nb > 0) {
                sb.append("  • ").append(sp.getLibelle()).append(" : ").append(nb).append("\n");
            }
        }

        // -- Top médecins (par nombre de RDV) --
        sb.append("\n🏆 MÉDECINS PAR NOMBRE DE RENDEZ-VOUS :\n");
        for (Medecin m : gestionMedecins.listerTousLesMedecins()) {
            int nbRdv = gestionRendezVous.getRendezVousMedecin(m).size();
            int nbOrdo = gestionOrdonnances.getOrdonnancesMedecin(m).size();
            sb.append("  • Dr ").append(m.getNomComplet())
              .append(" (").append(m.getSpecialite().getLibelle()).append(")")
              .append(" → ").append(nbRdv).append(" RDV, ").append(nbOrdo).append(" ordonnances\n");
        }

        // -- Ordonnances valides --
        sb.append("\n💊 ORDONNANCES VALIDES (en cours) : ")
          .append(gestionOrdonnances.getOrdonnancesValides().size()).append("\n");

        sb.append("\n══════════════════════════════════════════════\n");
        return sb.toString();
    }

    // -------------------------
    // Méthodes utilitaires
    // -------------------------

    /** Retourne le nombre de rendez-vous pour un statut donné. */
    public int getNombreRendezVousParStatut(StatutRendezVous statut) {
        return gestionRendezVous.getRendezVousParStatut(statut).size();
    }

    /** Retourne le médecin ayant le plus de rendez-vous (ou null si aucun). */
    public Medecin getMedecinLePlusActif() {
        Medecin medecinMax = null;
        int max = 0;
        for (Medecin m : gestionMedecins.listerTousLesMedecins()) {
            int nb = gestionRendezVous.getRendezVousMedecin(m).size();
            if (nb > max) {
                max = nb;
                medecinMax = m;
            }
        }
        return medecinMax;
    }

    @Override
    public String toString() {
        return "Statistiques { " +
               "patients=" + gestionPatients.getNombrePatients() +
               ", médecins=" + gestionMedecins.getNombreMedecins() +
               ", médicaments=" + gestionMedicaments.getNombreMedicaments() +
               ", RDV=" + gestionRendezVous.getNombreRendezVous() +
               ", ordonnances=" + gestionOrdonnances.getNombreOrdonnances() + " }";
    }
}
