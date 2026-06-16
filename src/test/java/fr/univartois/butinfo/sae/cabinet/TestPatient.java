package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPatient {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(
                "Dupont", "Jean", "0600000000", "jean.dupont@test.fr",
                "10 rue de la Paix", LocalDate.of(2000, 1, 1), "1000101000001"
        );
    }

    @Test
    void testAge() {
        assertEquals(26, patient.getAge(), "Le calcul de l'âge devrait être de 26 ans.");
    }

    @Test
    void testTauxRemboursementParDefaut() {
        // Le constructeur simplifié applique un taux de 75 %.
        assertThat(patient.getTauxRemboursement()).isEqualTo(0.75);
    }

    @Test
    void testConstructeurComplet() {
        Patient p = new Patient("Martin", "Lea", "06", "lea@test.fr",
                "5 rue Verte", LocalDate.of(1995, 5, 5), "295055000001", 0.90);
        assertThat(p.getTauxRemboursement()).isEqualTo(0.90);
    }

    @Test
    void testGettersEtSetters() {
        patient.setAdresse("2 avenue du Centre");
        patient.setDateNaissance(LocalDate.of(1980, 6, 15));
        patient.setTauxRemboursement(0.80);

        assertThat(patient.getAdresse()).isEqualTo("2 avenue du Centre");
        assertThat(patient.getDateNaissance()).isEqualTo(LocalDate.of(1980, 6, 15));
        assertThat(patient.getTauxRemboursement()).isEqualTo(0.80);
        assertThat(patient.getNumeroSecu()).isEqualTo("1000101000001");
        assertThat(patient.getNomComplet()).isEqualTo("Jean Dupont");
    }

    @Test
    void testEquals() {
        Patient autrePatient = new Patient(
                "Autre", "Nom", "00", "test@test.fr",
                "Adresse", LocalDate.of(2000, 1, 1), "1000101000001"
        );
        // L'égalité s'appuie sur l'ID (unique) : deux patients distincts ne sont pas égaux.
        assertThat(patient).isNotEqualTo(autrePatient);
        assertThat(patient.equals(patient)).isTrue();
        assertThat(patient.equals(null)).isFalse();
        assertThat(patient.equals("texte")).isFalse();
    }

    @Test
    void testToString() {
        assertThat(patient.toString()).contains("Patient", "Jean", "Dupont");
    }
}
