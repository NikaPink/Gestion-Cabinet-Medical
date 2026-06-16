package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGestionOrdonnances {

    private GestionOrdonnances gestion;
    private Medecin medecin;
    private Patient patient;

    @BeforeEach
    void setUp() {
        gestion = new GestionOrdonnances();
        medecin = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.GENERALISTE);
        patient = new Patient("Doe", "John", "0600000000", "john@mail.fr",
                "1 rue du Test", LocalDate.of(1990, 1, 1), "199010112345");
    }

    @Test
    void testCreerOrdonnance() {
        Ordonnance ordo = gestion.creerOrdonnance(medecin, patient, LocalDate.now().plusDays(10), "RAS");

        assertThat(ordo).isNotNull();
        assertThat(gestion.getNombreOrdonnances()).isEqualTo(1);
        assertThat(gestion.rechercherParId(ordo.getId())).isEqualTo(ordo);
        assertThat(gestion.rechercherParId(9999)).isNull();
    }

    @Test
    void testAjouterPrescription() {
        Ordonnance ordo = gestion.creerOrdonnance(medecin, patient, LocalDate.now().plusDays(10), "RAS");
        Medicament medicament = new Medicament("Doliprane", "Paracétamol", 2.5);

        gestion.ajouterPrescription(ordo.getId(), medicament, 2, "1/j", 5, "RAS");
        assertThat(ordo.getPrescriptions()).hasSize(1);

        // Ordonnance introuvable : ne fait rien, ne plante pas.
        gestion.ajouterPrescription(9999, medicament, 1, "x", 1, "");
        assertThat(ordo.getPrescriptions()).hasSize(1);
    }

    @Test
    void testListesParPatientMedecinEtValidite() {
        Ordonnance valide = gestion.creerOrdonnance(medecin, patient, LocalDate.now().plusDays(10), "valide");
        Ordonnance expiree = gestion.creerOrdonnance(medecin, patient, LocalDate.now().minusDays(1), "expirée");

        assertThat(gestion.getOrdonnancesPatient(patient)).contains(valide, expiree);
        assertThat(gestion.getOrdonnancesMedecin(medecin)).contains(valide, expiree);
        assertThat(gestion.getOrdonnancesValides()).contains(valide).doesNotContain(expiree);
        assertThat(gestion.listerToutesLesOrdonnances()).hasSize(2);
    }
}
