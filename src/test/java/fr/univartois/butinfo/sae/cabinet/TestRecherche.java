package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.utils.Recherche;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestRecherche {

    private Recherche recherche;
    private Patient patient;
    private Medecin medecin;

    @BeforeEach
    void setUp() {
        GestionPatients gestionPatients = new GestionPatients();
        GestionMedecins gestionMedecins = new GestionMedecins();

        patient = new Patient("Durand", "Alice", "0600000000", "alice@mail.fr",
                "1 rue du Test", LocalDate.of(1990, 1, 1), "199010112345");
        gestionPatients.ajouterPatient(patient);

        medecin = new Medecin("Martin", "Bob", "0102030405", "bob@cabinet.fr",
                "ORD-1", Specialite.GENERALISTE);
        gestionMedecins.ajouterMedecin(medecin);

        recherche = new Recherche(gestionPatients, gestionMedecins);
    }

    @Test
    void testRecherchePatient() {
        assertThat(recherche.rechercherPatientParNom("durand")).contains(patient);
        assertThat(recherche.rechercherPatientParNom(null)).isNull();
        assertThat(recherche.rechercherPatientParSecu("199010112345")).isEqualTo(patient);
        assertThat(recherche.rechercherPatientParSecu(null)).isNull();
    }

    @Test
    void testRechercheMedecin() {
        assertThat(recherche.rechercherMedecinParNom("martin")).contains(medecin);
        assertThat(recherche.rechercherMedecinParNom(null)).isNull();
        assertThat(recherche.rechercherMedecinParSpecialite(Specialite.GENERALISTE)).contains(medecin);
        assertThat(recherche.rechercherMedecinParSpecialite(null)).isNull();
        assertThat(recherche.rechercherMedecinParOrdre("ORD-1")).isEqualTo(medecin);
        assertThat(recherche.rechercherMedecinParOrdre(null)).isNull();
    }

    @Test
    void testToString() {
        assertThat(recherche.toString()).contains("Recherche");
    }
}
