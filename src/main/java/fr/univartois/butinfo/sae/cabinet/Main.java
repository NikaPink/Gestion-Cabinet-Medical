package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import fr.univartois.butinfo.sae.cabinet.utils.Recherche;
import fr.univartois.butinfo.sae.cabinet.utils.Statistiques;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe principale de démonstration du système de gestion de cabinet médical.
 * Exécutez cette classe pour tester toutes les fonctionnalités.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║   SYSTÈME DE GESTION DE CABINET MÉDICAL   ║");
        System.out.println("╚═══════════════════════════════════════════╝\n");

        // ============================================================
        // 1. Initialisation des services
        // ============================================================
        GestionPatients gestionPatients       = new GestionPatients();
        GestionMedecins gestionMedecins       = new GestionMedecins();
        GestionMedicaments gestionMedicaments = new GestionMedicaments();
        GestionRendezVous gestionRdv          = new GestionRendezVous();
        GestionOrdonnances gestionOrdonnances = new GestionOrdonnances();
        Recherche recherche                   = new Recherche(gestionPatients, gestionMedecins);
        Statistiques stats                    = new Statistiques(
                gestionPatients, gestionMedecins, gestionMedicaments, gestionRdv, gestionOrdonnances);

        // ============================================================
        // 2. Création de médecins
        // ============================================================
        System.out.println("=== AJOUT DES MÉDECINS ===");
        Medecin dr1 = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                                   "ORD-001", Specialite.GENERALISTE, 30.0);
        Medecin dr2 = new Medecin("Martin", "Sophie", "0398765432", "s.martin@cabinet.fr",
                                   "ORD-002", Specialite.CARDIOLOGUE, 50.0);
        Medecin dr3 = new Medecin("Bernard", "Lucie", "0367891234", "l.bernard@cabinet.fr",
                                   "ORD-003", Specialite.PEDIATRE, 40.0);
        gestionMedecins.ajouterMedecin(dr1);
        gestionMedecins.ajouterMedecin(dr2);
        gestionMedecins.ajouterMedecin(dr3);

        // ============================================================
        // 3. Création de patients
        // ============================================================
        System.out.println("\n=== AJOUT DES PATIENTS ===");
        Patient p1 = new Patient("Leblanc", "Paul", "0612345678", "p.leblanc@mail.fr",
                                  "12 rue des Lilas, Arras",
                                  LocalDate.of(1985, 3, 22), "185036562012345", .50);
        Patient p2 = new Patient("Moreau", "Claire", "0687654321", "c.moreau@mail.fr",
                                  "8 avenue Foch, Lens",
                                  LocalDate.of(1995, 7, 15), "295076210054321");
        Patient p3 = new Patient("Petit", "Antoine", "0655443322", "a.petit@mail.fr",
                                  "3 impasse du Moulin, Douai",
                                  LocalDate.of(2010, 11, 5), "210116210011122", .6);
        gestionPatients.ajouterPatient(p1);
        gestionPatients.ajouterPatient(p2);
        gestionPatients.ajouterPatient(p3);

        // ============================================================
        // 4. Création de médicaments
        // ============================================================
        System.out.println("\n=== AJOUT DES MÉDICAMENTS ===");
        Medicament med1 = new Medicament("Doliprane 1000mg",
                                          "Antalgique et antipyrétique", 5.5);
        Medicament med2 = new Medicament("Amoxicilline 500mg",
                                          "Antibiotique à large spectre", 13.25);
        Medicament med3 = new Medicament("Ibuprofène 400mg",
                                          "Anti-inflammatoire non stéroïdien", 43.12);
        Medicament med4 = new Medicament("Lisinopril 10mg",
                                          "Inhibiteur de l'enzyme de conversion", 24.56);
        gestionMedicaments.ajouterMedicament(med1);
        gestionMedicaments.ajouterMedicament(med2);
        gestionMedicaments.ajouterMedicament(med3);
        gestionMedicaments.ajouterMedicament(med4);

        // ============================================================
        // 5. Prise de rendez-vous
        // ============================================================
        System.out.println("\n=== PRISE DE RENDEZ-VOUS ===");
        LocalDateTime rdv1Date = LocalDateTime.of(2025, 9, 10, 9, 0);
        LocalDateTime rdv2Date = LocalDateTime.of(2025, 9, 10, 10, 30);
        LocalDateTime rdv3Date = LocalDateTime.of(2025, 9, 11, 14, 0);

        RendezVous rdv1 = gestionRdv.prendreRendezVous(p1, dr1, rdv1Date, 30, "Consultation de routine");
        RendezVous rdv2 = gestionRdv.prendreRendezVous(p2, dr2, rdv2Date, 45, "Bilan cardiaque");
        RendezVous rdv3 = gestionRdv.prendreRendezVous(p3, dr3, rdv3Date, 20, "Vaccination enfant");

        // Confirmation du premier RDV
        if (rdv1 != null) gestionRdv.confirmerRendezVous(rdv1.getId());

        // Annulation du troisième RDV
        if (rdv3 != null) gestionRdv.annulerRendezVous(rdv3.getId());

        // ============================================================
        // 6. Création d'ordonnances
        // ============================================================
        System.out.println("\n=== CRÉATION D'ORDONNANCES ===");

        Ordonnance ordo1 = gestionOrdonnances.creerOrdonnance(
                dr1, p1, LocalDate.now().plusMonths(3), "Renouveler si nécessaire");
        gestionOrdonnances.ajouterPrescription(
                ordo1.getId(), med1, 4,"2 comprimés par jour", 7, "À prendre avec de l'eau");
        gestionOrdonnances.ajouterPrescription(
                ordo1.getId(), med2, 3,"1 comprimé 3 fois/jour", 10, "Ne pas dépasser 3g/jour");

        Ordonnance ordo2 = gestionOrdonnances.creerOrdonnance(
                dr2, p2, LocalDate.now().plusMonths(6), "Contrôle dans 1 mois");
        gestionOrdonnances.ajouterPrescription(
                ordo2.getId(), med4, 1,"1 comprimé le matin", 30, "Surveillance tension artérielle");

        System.out.println("\n--- Détail des ordonnances ---");
        System.out.println(ordo1);
        System.out.println(ordo2);

        // ============================================================
        // 7. Module de recherche
        // ============================================================
        System.out.println("\n=== MODULE DE RECHERCHE ===");

        System.out.println("\n>> Recherche patient par nom 'leblanc':");
        List<Patient> patientsTrouves = recherche.rechercherPatientParNom("leblanc");
        logResultatsPatients(patientsTrouves, "Patients trouvés pour \"leblanc\"");

        System.out.println("\n>> Recherche médecin par spécialité CARDIOLOGUE:");
        List<Medecin> medecinsSpec = recherche.rechercherMedecinParSpecialite(Specialite.CARDIOLOGUE);
        logResultatsMedecins(medecinsSpec, "Médecins de spécialité \"Cardiologie\"");

        System.out.println("\n>> Recherche médecin par nom 'martin':");
        List<Medecin> medecinsNom = recherche.rechercherMedecinParNom("martin");
        logResultatsMedecins(medecinsNom, "Médecins trouvés pour \"martin\"");

        System.out.println("\n>> Recherche patient par n° sécu:");
        Patient pTrouve = recherche.rechercherPatientParSecu("185036562012345");
        if (pTrouve != null) {
            log.info("Patient trouvé : {}", pTrouve);
        }

        // ============================================================
        // 8. Module de statistiques
        // ============================================================
        System.out.println("\n=== MODULE DE STATISTIQUES ===");
        log.info(stats.getStatistiques());

        Medecin actif = stats.getMedecinLePlusActif();
        if (actif != null) {
            System.out.println("Médecin le plus actif : Dr " + actif.getNomComplet());
        } else {
            System.out.println("Aucun médecin actif trouvé.");
        }

        // ============================================================
        // 9. Mise à jour et suppression (CRUD)
        // ============================================================
        System.out.println("\n=== MISE À JOUR ET SUPPRESSION ===");

        // Mise à jour d'un médecin
        gestionMedecins.mettreAJourMedecin(dr1.getId(),
                "Dupont", "Jean", 33.45, "0312345678",
            "j.dupont@cabinet.fr",  Specialite.GENERALISTE);

        // Mise à jour d'un patient
        gestionPatients.mettreAJourPatient(p1.getId(),
                "Leblanc", "Paul-Henri", "0699887766", "p.leblanc@newmail.fr",
                "20 rue des Roses, Arras", .80);

        // Suppression d'un patient
        gestionPatients.supprimerPatient(p3.getId());

        // Affichage final
        System.out.println("\n=== ÉTAT FINAL ===");
        List<Patient> patients = gestionPatients.listerTousLesPatients();
        if (patients.isEmpty()) {
            System.out.println("Aucun patient enregistré.");
            return;
        }
        System.out.println("=== LISTE DES PATIENTS (" + patients.size() + ") ===");
        for (Patient p : patients) {
            System.out.println(p);
        }
        List<Medecin> medecins = gestionMedecins.listerTousLesMedecins();
        if (medecins.isEmpty()) {
            System.out.println("Aucun médecin enregistré.");
            return;
        }
        System.out.println("=== LISTE DES MÉDECINS (" + medecins.size() + ") ===");
        for (Medecin m : medecins) {
            System.out.println(m);
        }
        List<Medicament> medicaments = gestionMedicaments.listerTousLesMedicaments();
        if (medicaments.isEmpty()) {
            System.out.println("Aucun médicament enregistré.");
            return;
        }
        System.out.println("=== LISTE DES MÉDICAMENTS (" + medicaments.size() + ") ===");
        for (Medicament m : medicaments) {
            System.out.println(m);
        }
        List<RendezVous> rendezVous = gestionRdv.listerTousLesRendezVous();
        if (rendezVous.isEmpty()) {
            System.out.println("Aucun rendez-vous enregistré.");
            return;
        }
        System.out.println("=== LISTE DES RENDEZ-VOUS (" + rendezVous.size() + ") ===");
        for (RendezVous rdv : rendezVous) {
            System.out.println(rdv);
        }
    }

    private static void logResultatsPatients(List<Patient> liste, String titre) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(titre).append(" (").append(liste.size()).append(" résultat(s)) ---");
        if (liste.isEmpty()) {
            sb.append("\n  Aucun résultat.");
        } else {
            for (Patient p : liste) {
                sb.append("\n  ").append(p);
            }
        }
        log.info(sb.toString());
    }

    private static void logResultatsMedecins(List<Medecin> liste, String titre) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(titre).append(" (").append(liste.size()).append(" résultat(s)) ---");
        if (liste.isEmpty()) {
            sb.append("\n  Aucun résultat.");
        } else {
            for (Medecin m : liste) {
                sb.append("\n  ").append(m);
            }
        }
        log.info(sb.toString());
    }
}
