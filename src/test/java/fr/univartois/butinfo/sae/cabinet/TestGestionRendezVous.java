package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestGestionRendezVous {

    @Mock
    private Patient patientMock;

    @Mock
    private Medecin medecinMock;

    @InjectMocks
    private GestionRendezVous gestionRdv;

    @Test
    void testPrendreRendezVousSucces() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);
        RendezVous rdv = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");

        assertThat(rdv).isNotNull();
        assertThat(rdv.getPatient()).isEqualTo(patientMock);
        assertThat(rdv.getMedecin()).isEqualTo(medecinMock);
        assertThat(rdv.getDateHeure()).isEqualTo(dateHeure);
        assertThat(rdv.getDureeMinutes()).isEqualTo(30);
        assertThat(rdv.getMotif()).isEqualTo("Routine");
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.PLANIFIE);
        assertThat(gestionRdv.getNombreRendezVous()).isEqualTo(1);
    }

    @Test
    void testPrendreRendezVousEchecMedecinIndisponible() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);

        // Prendre un premier rendez-vous
        RendezVous rdv1 = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");
        assertThat(rdv1).isNotNull();

        // Tenter d'en prendre un second au même créneau pour le même médecin
        Patient autrePatient = mock(Patient.class);
        RendezVous rdv2 = gestionRdv.prendreRendezVous(autrePatient, medecinMock, dateHeure, 30, "Urgence");

        assertThat(rdv2).isNull();
        assertThat(gestionRdv.getNombreRendezVous()).isEqualTo(1);
    }

    @Test
    void testAnnulerRendezVous() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);
        RendezVous rdv = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");

        gestionRdv.annulerRendezVous(rdv.getId());
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.ANNULE);
    }

    @Test
    void testConfirmerRendezVous() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);
        RendezVous rdv = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");

        gestionRdv.confirmerRendezVous(rdv.getId());
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.CONFIRME);
    }

    @Test
    void testTerminerRendezVous() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);
        RendezVous rdv = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");

        boolean success = gestionRdv.terminerRendezVous(rdv.getId());
        assertThat(success).isTrue();
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.TERMINE);
    }

    @Test
    void testTerminerRendezVousIntrouvable() {
        boolean success = gestionRdv.terminerRendezVous(999);
        assertThat(success).isFalse();
    }

    @Test
    void testRechercherParId() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);
        RendezVous rdv = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");

        assertThat(gestionRdv.rechercherParId(rdv.getId())).isEqualTo(rdv);
        assertThat(gestionRdv.rechercherParId(999)).isNull();
    }

    @Test
    void testGetRendezVousPatient() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);
        RendezVous rdv = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");

        List<RendezVous> rdvs = gestionRdv.getRendezVousPatient(patientMock);
        assertThat(rdvs).containsExactly(rdv);

        Patient autrePatient = mock(Patient.class);
        assertThat(gestionRdv.getRendezVousPatient(autrePatient)).isEmpty();
    }

    @Test
    void testGetRendezVousMedecin() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);
        RendezVous rdv = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");

        List<RendezVous> rdvs = gestionRdv.getRendezVousMedecin(medecinMock);
        assertThat(rdvs).containsExactly(rdv);

        Medecin autreMedecin = mock(Medecin.class);
        assertThat(gestionRdv.getRendezVousMedecin(autreMedecin)).isEmpty();
    }

    @Test
    void testGetRendezVousParStatut() {
        LocalDateTime dateHeure = LocalDateTime.of(2025, 9, 10, 9, 0);
        RendezVous rdv = gestionRdv.prendreRendezVous(patientMock, medecinMock, dateHeure, 30, "Routine");

        assertThat(gestionRdv.getRendezVousParStatut(StatutRendezVous.PLANIFIE)).containsExactly(rdv);
        assertThat(gestionRdv.getRendezVousParStatut(StatutRendezVous.CONFIRME)).isEmpty();
    }
}
