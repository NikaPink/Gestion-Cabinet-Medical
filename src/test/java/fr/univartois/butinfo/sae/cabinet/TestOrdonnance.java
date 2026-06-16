package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.LignePrescription;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestOrdonnance {

    private Medecin medecin;
    private Patient patient;
    private Ordonnance ordonnance;

    @BeforeEach
    void setUp() {
        medecin = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.GENERALISTE);
        // Le constructeur simplifié fixe un taux de remboursement de 75 %.
        patient = new Patient("Doe", "John", "0600000000", "john.doe@mail.fr",
                "1 rue du Test", LocalDate.of(1990, 1, 1), "199010112345");
        ordonnance = new Ordonnance(medecin, patient, LocalDate.now().plusMonths(1), "Repos");
    }

    @Test
    void testGetters() {
        assertThat(ordonnance.getId()).isPositive();
        assertThat(ordonnance.getMedecin()).isEqualTo(medecin);
        assertThat(ordonnance.getPatient()).isEqualTo(patient);
        assertThat(ordonnance.getDateEmission()).isEqualTo(LocalDate.now());
        assertThat(ordonnance.getRemarques()).isEqualTo("Repos");
    }

    @Test
    void testConstructeurParDefaut() {
        Ordonnance ordo = new Ordonnance(medecin, patient);
        assertThat(ordo.getRemarques()).isEmpty();
        assertThat(ordo.getDateValidite()).isEqualTo(LocalDate.now().plusMonths(3));
    }

    @Test
    void testSetters() {
        ordonnance.setRemarques("Nouvelle remarque");
        assertThat(ordonnance.getRemarques()).isEqualTo("Nouvelle remarque");

        LocalDate nouvelleDate = LocalDate.now().plusDays(5);
        ordonnance.setDateValidite(nouvelleDate);
        assertThat(ordonnance.getDateValidite()).isEqualTo(nouvelleDate);
    }

    @Test
    void testAjouterEtSupprimerPrescription() {
        Medicament medicament = new Medicament("Doliprane", "Paracétamol", 10.0);
        LignePrescription ligne = new LignePrescription(medicament, 2, "1 le matin", 5, "");

        ordonnance.ajouterPrescription(ligne);
        assertThat(ordonnance.getPrescriptions()).hasSize(1);

        assertThat(ordonnance.supprimerPrescription(medicament)).isTrue();
        assertThat(ordonnance.getPrescriptions()).isEmpty();

        // Supprimer un médicament absent ne fait rien.
        assertThat(ordonnance.supprimerPrescription(medicament)).isFalse();
    }

    @Test
    void testGetPrixTotal() {
        Medicament medicament = new Medicament("Doliprane", "Paracétamol", 10.0);
        ordonnance.ajouterPrescription(new LignePrescription(medicament, 2, "1/j", 5, ""));

        // 10 € x 2 = 20 €, taux de remboursement 75 % -> reste à payer 5 €.
        assertThat(ordonnance.getPrixTotal()).isCloseTo(5.0, within(0.001));
    }

    @Test
    void testValidite() {
        ordonnance.setDateValidite(LocalDate.now().plusDays(1));
        assertThat(ordonnance.estValide()).isTrue();

        ordonnance.setDateValidite(LocalDate.now().minusDays(1));
        assertThat(ordonnance.estValide()).isFalse();
    }

    @Test
    void testEquals() {
        assertThat(ordonnance.equals(ordonnance)).isTrue();
        assertThat(ordonnance.equals(null)).isFalse();
        assertThat(ordonnance.equals("autre chose")).isFalse();

        Ordonnance autre = new Ordonnance(medecin, patient);
        assertThat(ordonnance.equals(autre)).isFalse();
        assertThat(ordonnance.hashCode()).isEqualTo(Integer.hashCode(ordonnance.getId()));
    }

    @Test
    void testToString() {
        assertThat(ordonnance.toString()).contains("ORDONNANCE", "Marie", "John");
    }
}
