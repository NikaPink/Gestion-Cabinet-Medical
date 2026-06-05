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
     * Affiche un résumé complet des statistiques du cabinet.
     */
    public void afficherTableauDeBord() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║        TABLEAU DE BORD - CABINET MÉDICAL  ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // -- Chiffres globaux --
        System.out.println("\n📋 CHIFFRES GLOBAUX :");
        System.out.println("  • Patients enregistrés   : " + gestionPatients.getNombrePatients());
        System.out.println("  • Médecins enregistrés   : " + gestionMedecins.getNombreMedecins());
        System.out.println("  • Médicaments référencés : " + gestionMedicaments.getNombreMedicaments());
        System.out.println("  • Rendez-vous total      : " + gestionRendezVous.getNombreRendezVous());
        System.out.println("  • Ordonnances émises     : " + gestionOrdonnances.getNombreOrdonnances());

        // -- Détail des rendez-vous par statut --
        System.out.println("\n📅 RENDEZ-VOUS PAR STATUT :");
        for (StatutRendezVous statut : StatutRendezVous.values()) {
            int nb = gestionRendezVous.getRendezVousParStatut(statut).size();
            System.out.println("  • " + statut.getLibelle() + " : " + nb);
        }

        // -- Médecins par spécialité --
        System.out.println("\n🩺 MÉDECINS PAR SPÉCIALITÉ :");
        for (Specialite sp : Specialite.values()) {
            int nb = gestionMedecins.rechercherParSpecialite(sp).size();
            if (nb > 0) {
                System.out.println("  • " + sp.getLibelle() + " : " + nb);
            }
        }

        // -- Top médecins (par nombre de RDV) --
        System.out.println("\n🏆 MÉDECINS PAR NOMBRE DE RENDEZ-VOUS :");
        for (Medecin m : gestionMedecins.listerTousLesMedecins()) {
            int nbRdv = gestionRendezVous.getRendezVousMedecin(m).size();
            int nbOrdo = gestionOrdonnances.getOrdonnancesMedecin(m).size();
            System.out.println("  • Dr " + m.getNomComplet() +
                               " (" + m.getSpecialite().getLibelle() + ")" +
                               " → " + nbRdv + " RDV, " + nbOrdo + " ordonnances");
        }

        // -- Ordonnances valides --
        System.out.println("\n💊 ORDONNANCES VALIDES (en cours) : " +
                           gestionOrdonnances.getOrdonnancesValides().size());

        System.out.println("\n══════════════════════════════════════════════\n");
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
