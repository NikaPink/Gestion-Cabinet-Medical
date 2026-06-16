package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests du modèle RendezVous : getters/setters, transitions de statut et égalité.
 */
class TestRendezVousModele {

    private Patient patient;
    private Medecin medecin;
    private LocalDateTime date;

    @BeforeEach
    void setUp() {
        patient = new Patient("Doe", "John", "0600000000", "john@mail.fr",
                "1 rue du Test", LocalDate.of(1990, 1, 1), "199010112345");
        medecin = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.GENERALISTE);
        date = LocalDateTime.of(2026, 6, 10, 15, 0);
    }

    @Test
    void testGettersEtStatutInitial() {
        RendezVous rdv = new RendezVous(patient, medecin, date, 45, "Consultation");

        assertThat(rdv.getId()).isPositive();
        assertThat(rdv.getPatient()).isEqualTo(patient);
        assertThat(rdv.getMedecin()).isEqualTo(medecin);
        assertThat(rdv.getDateHeure()).isEqualTo(date);
        assertThat(rdv.getDureeMinutes()).isEqualTo(45);
        assertThat(rdv.getMotif()).isEqualTo("Consultation");
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.PLANIFIE);
    }

    @Test
    void testConstructeurDureeParDefaut() {
        RendezVous rdv = new RendezVous(patient, medecin, date, "Consultation");
        assertThat(rdv.getDureeMinutes()).isEqualTo(30);
    }

    @Test
    void testSetters() {
        RendezVous rdv = new RendezVous(patient, medecin, date, 30, "Motif");
        LocalDateTime nouvelleDate = date.plusDays(1);

        rdv.setDateHeure(nouvelleDate);
        rdv.setDureeMinutes(60);
        rdv.setMotif("Nouveau motif");
        rdv.setStatut(StatutRendezVous.CONFIRME);

        assertThat(rdv.getDateHeure()).isEqualTo(nouvelleDate);
        assertThat(rdv.getDureeMinutes()).isEqualTo(60);
        assertThat(rdv.getMotif()).isEqualTo("Nouveau motif");
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.CONFIRME);
    }

    @Test
    void testConfirmer() {
        RendezVous rdv = new RendezVous(patient, medecin, date, 30, "Motif");
        rdv.confirmer();
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.CONFIRME);
    }

    @Test
    void testAnnuler() {
        RendezVous rdv = new RendezVous(patient, medecin, date, 30, "Motif");
        rdv.annuler();
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.ANNULE);

        // Confirmer un RDV annulé ne change rien (il n'est plus PLANIFIE).
        rdv.confirmer();
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.ANNULE);
    }

    @Test
    void testTerminerPuisAnnulerNeChangeRien() {
        RendezVous rdv = new RendezVous(patient, medecin, date, 30, "Motif");
        rdv.terminer();
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.TERMINE);

        // On ne peut pas annuler un RDV déjà terminé.
        rdv.annuler();
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.TERMINE);
    }

    @Test
    void testEqualsEtToString() {
        RendezVous rdv = new RendezVous(patient, medecin, date, 30, "Motif");
        RendezVous meme = new RendezVous(patient, medecin, date, 60, "Autre motif");
        RendezVous autre = new RendezVous(patient, medecin, date.plusHours(1), 30, "Motif");

        // Égalité fondée sur patient + médecin + date/heure.
        assertThat(rdv).isEqualTo(meme);
        assertThat(rdv.equals(rdv)).isTrue();
        assertThat(rdv.equals(null)).isFalse();
        assertThat(rdv.equals("texte")).isFalse();
        assertThat(rdv).isNotEqualTo(autre);
        assertThat(rdv.toString()).contains("RDV", "John");
    }
}
