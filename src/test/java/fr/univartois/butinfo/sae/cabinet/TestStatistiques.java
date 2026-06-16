package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import fr.univartois.butinfo.sae.cabinet.utils.Statistiques;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestStatistiques {

    private Statistiques statistiques;
    private Medecin medecin;

    @BeforeEach
    void setUp() {
        GestionPatients gestionPatients = new GestionPatients();
        GestionMedecins gestionMedecins = new GestionMedecins();
        GestionMedicaments gestionMedicaments = new GestionMedicaments();
        GestionRendezVous gestionRendezVous = new GestionRendezVous();
        GestionOrdonnances gestionOrdonnances = new GestionOrdonnances();

        Patient patient = new Patient("Doe", "John", "0600000000", "john@mail.fr",
                "1 rue du Test", LocalDate.of(1990, 1, 1), "199010112345");
        gestionPatients.ajouterPatient(patient);

        medecin = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.GENERALISTE);
        gestionMedecins.ajouterMedecin(medecin);

        gestionMedicaments.ajouterMedicament(new Medicament("Doliprane", "Paracétamol", 2.5));

        RendezVous rdv = gestionRendezVous.prendreRendezVous(patient, medecin,
                LocalDateTime.of(2026, 6, 10, 10, 0), 30, "Suivi");
        gestionRendezVous.confirmerRendezVous(rdv.getId());

        gestionOrdonnances.creerOrdonnance(medecin, patient, LocalDate.now().plusMonths(1), "RAS");

        statistiques = new Statistiques(gestionPatients, gestionMedecins,
                gestionMedicaments, gestionRendezVous, gestionOrdonnances);
    }

    @Test
    void testGetStatistiques() {
        String resume = statistiques.getStatistiques();
        assertThat(resume).contains("TABLEAU DE BORD", "Marie", "Médecin généraliste");
    }

    @Test
    void testGetNombreRendezVousParStatut() {
        assertThat(statistiques.getNombreRendezVousParStatut(StatutRendezVous.CONFIRME)).isEqualTo(1);
        assertThat(statistiques.getNombreRendezVousParStatut(StatutRendezVous.ANNULE)).isZero();
    }

    @Test
    void testGetMedecinLePlusActif() {
        assertThat(statistiques.getMedecinLePlusActif()).isEqualTo(medecin);
    }

    @Test
    void testGetMedecinLePlusActifAucun() {
        Statistiques vide = new Statistiques(new GestionPatients(), new GestionMedecins(),
                new GestionMedicaments(), new GestionRendezVous(), new GestionOrdonnances());
        assertThat(vide.getMedecinLePlusActif()).isNull();
    }

    @Test
    void testToString() {
        assertThat(statistiques.toString()).contains("Statistiques");
    }
}
