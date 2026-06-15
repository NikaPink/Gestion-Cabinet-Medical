package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestGestionPatients {

    @Mock
    private Patient patientMock;

    @InjectMocks
    private GestionPatients gestionPatients;

    @Test
    void testAjoutPatientSucces() {
        when(patientMock.getNumeroSecu()).thenReturn("123456789");
        when(patientMock.getNomComplet()).thenReturn("John Doe");

        gestionPatients.ajouterPatient(patientMock);

        assertThat(gestionPatients.getNombrePatients()).isEqualTo(1);
        assertThat(gestionPatients.listerTousLesPatients()).containsExactly(patientMock);
    }

    @Test
    void testAjoutPatientEchecCarExisteDeja() {
        when(patientMock.getNumeroSecu()).thenReturn("123456789");
        when(patientMock.getNomComplet()).thenReturn("John Doe");

        gestionPatients.ajouterPatient(patientMock);

        Patient autreMock = mock(Patient.class);
        when(autreMock.getNumeroSecu()).thenReturn("123456789");

        gestionPatients.ajouterPatient(autreMock);

        assertThat(gestionPatients.getNombrePatients()).isEqualTo(1);
        assertThat(gestionPatients.listerTousLesPatients()).containsExactly(patientMock);
    }

    @Test
    void testRechercherParId() {
        when(patientMock.getNumeroSecu()).thenReturn("123456789");
        when(patientMock.getId()).thenReturn(42);
        when(patientMock.getNomComplet()).thenReturn("John Doe");

        gestionPatients.ajouterPatient(patientMock);

        assertThat(gestionPatients.rechercherParId(42)).isEqualTo(patientMock);
        assertThat(gestionPatients.rechercherParId(999)).isNull();
    }

    @Test
    void testRechercherParNumeroSecu() {
        when(patientMock.getNumeroSecu()).thenReturn("123456789");
        when(patientMock.getNomComplet()).thenReturn("John Doe");

        gestionPatients.ajouterPatient(patientMock);

        assertThat(gestionPatients.rechercherParNumeroSecu("123456789")).isEqualTo(patientMock);
        assertThat(gestionPatients.rechercherParNumeroSecu("987654321")).isNull();
    }

    @Test
    void testRechercherParNom() {
        when(patientMock.getNumeroSecu()).thenReturn("123456789");
        when(patientMock.getNom()).thenReturn("Leblanc");
        when(patientMock.getPrenom()).thenReturn("Paul");
        when(patientMock.getNomComplet()).thenReturn("Paul Leblanc");

        gestionPatients.ajouterPatient(patientMock);

        List<Patient> resultats = gestionPatients.rechercherParNom("leblanc");
        assertThat(resultats).contains(patientMock);

        List<Patient> resultatsPrenom = gestionPatients.rechercherParNom("paul");
        assertThat(resultatsPrenom).contains(patientMock);

        List<Patient> resultatsAucun = gestionPatients.rechercherParNom("Moreau");
        assertThat(resultatsAucun).isEmpty();
    }

    @Test
    void testMettreAJourPatientSucces() {
        Patient realPatient = new Patient("Leblanc", "Paul", "0612345678", "p.leblanc@mail.fr",
                "12 rue des Lilas, Arras", LocalDate.of(1985, 3, 22), "123456789");

        gestionPatients.ajouterPatient(realPatient);

        gestionPatients.mettreAJourPatient(realPatient.getId(), "Leblanc", "Paul-Henri",
                "0699887766", "p.leblanc@newmail.fr", "20 rue des Roses, Arras", 0.80);

        Patient updated = gestionPatients.rechercherParId(realPatient.getId());
        assertThat(updated).isNotNull();
        assertThat(updated.getPrenom()).isEqualTo("Paul-Henri");
        assertThat(updated.getTelephone()).isEqualTo("0699887766");
        assertThat(updated.getEmail()).isEqualTo("p.leblanc@newmail.fr");
        assertThat(updated.getAdresse()).isEqualTo("20 rue des Roses, Arras");
        assertThat(updated.getTauxRemboursement()).isEqualTo(0.80);
    }

    @Test
    void testMettreAJourPatientEchecCarIntrouvable() {
        Patient realPatient = new Patient("Leblanc", "Paul", "0612345678", "p.leblanc@mail.fr",
                "12 rue des Lilas, Arras", LocalDate.of(1985, 3, 22), "123456789");

        gestionPatients.ajouterPatient(realPatient);

        // Modification d'un ID inexistant
        gestionPatients.mettreAJourPatient(999, "Leblanc", "Paul-Henri",
                "0699887766", "p.leblanc@newmail.fr", "20 rue des Roses, Arras", 0.80);

        // On vérifie que le patient existant n'a pas été altéré
        Patient updated = gestionPatients.rechercherParId(realPatient.getId());
        assertThat(updated).isNotNull();
        assertThat(updated.getPrenom()).isEqualTo("Paul");
    }

    @Test
    void testSupprimerPatient() {
        Patient realPatient = new Patient("Leblanc", "Paul", "0612345678", "p.leblanc@mail.fr",
                "12 rue des Lilas, Arras", LocalDate.of(1985, 3, 22), "123456789");

        gestionPatients.ajouterPatient(realPatient);
        assertThat(gestionPatients.getNombrePatients()).isEqualTo(1);

        gestionPatients.supprimerPatient(realPatient.getId());
        assertThat(gestionPatients.getNombrePatients()).isEqualTo(0);
    }

    @Test
    void testSupprimerPatientEchecCarIntrouvable() {
        Patient realPatient = new Patient("Leblanc", "Paul", "0612345678", "p.leblanc@mail.fr",
                "12 rue des Lilas, Arras", LocalDate.of(1985, 3, 22), "123456789");

        gestionPatients.ajouterPatient(realPatient);
        assertThat(gestionPatients.getNombrePatients()).isEqualTo(1);

        // Suppression d'un ID inexistant
        gestionPatients.supprimerPatient(999);
        assertThat(gestionPatients.getNombrePatients()).isEqualTo(1);
    }

    @Test
    void testListerTousLesPatientsCopieDefensive() {
        when(patientMock.getNumeroSecu()).thenReturn("123456789");
        when(patientMock.getNomComplet()).thenReturn("John Doe");

        gestionPatients.ajouterPatient(patientMock);

        List<Patient> liste = gestionPatients.listerTousLesPatients();
        assertThat(liste).containsExactly(patientMock);

        // Tenter de modifier la liste retournée ne doit pas affecter la gestion interne
        try {
            liste.clear();
        } catch (UnsupportedOperationException e) {
            // Si la liste retournée est immuable, c'est aussi correct
        }

        assertThat(gestionPatients.getNombrePatients()).isEqualTo(1);
        assertThat(gestionPatients.listerTousLesPatients()).containsExactly(patientMock);
    }
}
