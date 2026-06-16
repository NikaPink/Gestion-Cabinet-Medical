package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGestionRendezVous {

    private GestionRendezVous gestion;
    private Patient patient;
    private Medecin medecin;
    private LocalDateTime date;

    @BeforeEach
    void setUp() {
        gestion = new GestionRendezVous();
        patient = new Patient("Doe", "John", "0600000000", "john@mail.fr",
                "1 rue du Test", LocalDate.of(1990, 1, 1), "199010112345");
        medecin = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.GENERALISTE);
        date = LocalDateTime.of(2026, 6, 10, 15, 0);
    }

    @Test
    void testPrendreRendezVousSucces() {
        RendezVous rdv = gestion.prendreRendezVous(patient, medecin, date, 20, "Suivi");

        assertThat(rdv).isNotNull();
        assertThat(rdv.getMedecin()).isEqualTo(medecin);
        assertThat(rdv.getPatient()).isEqualTo(patient);
        assertThat(gestion.getNombreRendezVous()).isEqualTo(1);
    }

    @Test
    void testPrendreRendezVousEchecSiIndisponible() {
        gestion.prendreRendezVous(patient, medecin, date, 30, "RDV 1");
        RendezVous rdv2 = gestion.prendreRendezVous(patient, medecin, date, 30, "RDV 2");

        assertThat(rdv2).isNull();
        assertThat(gestion.getNombreRendezVous()).isEqualTo(1);
    }

    @Test
    void testConfirmerEtAnnulerRendezVous() {
        RendezVous rdv = gestion.prendreRendezVous(patient, medecin, date, 30, "RDV");

        gestion.confirmerRendezVous(rdv.getId());
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.CONFIRME);

        gestion.annulerRendezVous(rdv.getId());
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.ANNULE);
    }

    @Test
    void testActionsSurRendezVousIntrouvable() {
        // Aucun rendez-vous : les actions ne doivent pas planter et terminer renvoie false.
        gestion.confirmerRendezVous(999);
        gestion.annulerRendezVous(999);
        assertThat(gestion.terminerRendezVous(999)).isFalse();
    }

    @Test
    void testTerminerRendezVousSucces() {
        RendezVous rdv = gestion.prendreRendezVous(patient, medecin, date, 30, "RDV");

        assertThat(gestion.terminerRendezVous(rdv.getId())).isTrue();
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.TERMINE);
    }

    @Test
    void testRecherchesEtDisponibilite() {
        RendezVous rdv = gestion.prendreRendezVous(patient, medecin, date, 30, "RDV");

        assertThat(gestion.rechercherParId(rdv.getId())).isEqualTo(rdv);
        assertThat(gestion.rechercherParId(999)).isNull();
        assertThat(gestion.getRendezVousPatient(patient)).contains(rdv);
        assertThat(gestion.getRendezVousMedecin(medecin)).contains(rdv);
        assertThat(gestion.getRendezVousParStatut(StatutRendezVous.PLANIFIE)).contains(rdv);
        assertThat(gestion.listerTousLesRendezVous()).hasSize(1);

        assertThat(gestion.estDisponible(medecin, date)).isFalse();
        assertThat(gestion.estDisponible(medecin, date.plusHours(2))).isTrue();
    }
}
