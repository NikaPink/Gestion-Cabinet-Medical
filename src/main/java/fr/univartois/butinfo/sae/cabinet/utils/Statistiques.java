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
    public String afficherTableauDeBord() {
        StringBuilder sb = new StringBuilder("\n╔══════════════════════════════════════════╗");
        sb.append("║        TABLEAU DE BORD - CABINET MÉDICAL  ║");
        sb.append("╚══════════════════════════════════════════╝");

        // -- Chiffres globaux --
        sb.append("\n📋 CHIFFRES GLOBAUX :");
        sb.append("\n  • Patients enregistrés   : " );
        sb.append(gestionPatients.getNombrePatients());
        sb.append("\n  • Médecins enregistrés   : ");
        sb.append(gestionMedecins.getNombreMedecins());
        sb.append("\n • Médicaments référencés : ");
        sb.append(gestionMedicaments.getNombreMedicaments());
        sb.append("\n  • Rendez-vous total      : ");
        sb.append(gestionRendezVous.getNombreRendezVous());
        sb.append("\n  • Ordonnances émises     : ");
        sb.append( gestionOrdonnances.getNombreOrdonnances());
        String s;
        // -- Détail des rendez-vous par statut --
        sb.append("\n📅 RENDEZ-VOUS PAR STATUT :");
        for (StatutRendezVous statut : StatutRendezVous.values()) {
            int nb = gestionRendezVous.getRendezVousParStatut(statut).size();
            s="  • " + statut.getLibelle() + " : " + nb+"\n";
            sb.append(s);
        }

        // -- Médecins par spécialité --
        sb.append("\n🩺 MÉDECINS PAR SPÉCIALITÉ :\n");
        for (Specialite sp : Specialite.values()) {
            int nb = gestionMedecins.rechercherParSpecialite(sp).size();
            if (nb > 0) {
                s="  • " + sp.getLibelle() + " : " + nb+"\n";
                sb.append(s);
            }
        }

        // -- Top médecins (par nombre de RDV) --
        System.out.println("\n🏆 MÉDECINS PAR NOMBRE DE RENDEZ-VOUS :");
        for (Medecin m : gestionMedecins.listerTousLesMedecins()) {
            int nbRdv = gestionRendezVous.getRendezVousMedecin(m).size();
            int nbOrdo = gestionOrdonnances.getOrdonnancesMedecin(m).size();
            s="  • Dr " + m.getNomComplet() +
                    " (" + m.getSpecialite().getLibelle() + ")" +
                    " → " + nbRdv + " RDV, " + nbOrdo + " ordonnances\n";
            sb.append(s);
        }

        // -- Ordonnances valides --
        sb.append("\n💊 ORDONNANCES VALIDES (en cours) : ");
        sb.append(gestionOrdonnances.getOrdonnancesValides().size());
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
