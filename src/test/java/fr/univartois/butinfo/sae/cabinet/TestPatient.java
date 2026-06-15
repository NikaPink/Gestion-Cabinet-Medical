package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testEquals() {
        Patient memePatient = new Patient(
                "Autre", "Nom", "00", "test@test.fr",
                "Adresse", LocalDate.of(2000, 1, 1), "1000101000001"
        );
        assertEquals(patient, memePatient, "Deux patients avec le même numéro de sécu devraient être égaux.");
    }

}
