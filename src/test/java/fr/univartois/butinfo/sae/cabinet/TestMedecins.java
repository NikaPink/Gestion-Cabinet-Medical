package fr.univartois.butinfo.sae.cabinet.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import main.java.fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import main.java.fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestMedecins {

    private GestionMedecins gestionMedecins;

    @BeforeEach
    void setUp() {
        gestionMedecins = new GestionMedecins();
    }

    @Test
    void testAjouterMedecinSucces() {
        Medecin medecin = mock(Medecin.class);
        when(medecin.getNumeroOrdre()).thenReturn("12345");
        when(medecin.getNomComplet()).thenReturn("Jean Dupont");

        gestionMedecins.ajouterMedecin(medecin);

        assertEquals(1, gestionMedecins.getNombreMedecins());
        assertEquals(medecin, gestionMedecins.listerTousLesMedecins().get(0));
    }

    @Test
    void testAjouterMedecinDoublonNumeroOrdre() {
        Medecin m1 = mock(Medecin.class);
        when(m1.getNumeroOrdre()).thenReturn("12345");

        Medecin m2 = mock(Medecin.class);
        when(m2.getNumeroOrdre()).thenReturn("12345"); // Même numéro d'ordre

        gestionMedecins.ajouterMedecin(m1);
        gestionMedecins.ajouterMedecin(m2); // Devrait être rejeté

        assertEquals(1, gestionMedecins.getNombreMedecins(), "Le doublon ne doit pas être ajouté");
    }

    @Test
    void testRechercherParId() {
        Medecin medecin = mock(Medecin.class);
        when(medecin.getNumeroOrdre()).thenReturn("111");
        when(medecin.getId()).thenReturn(42);

        gestionMedecins.ajouterMedecin(medecin);

        assertNull(gestionMedecins.rechercherParId(999)); // ID inexistant
        assertEquals(medecin, gestionMedecins.rechercherParId(42)); // ID existant
    }

    @Test
    void testRechercherParNumeroOrdre() {
        Medecin medecin = mock(Medecin.class);
        when(medecin.getNumeroOrdre()).thenReturn("67890-A");

        gestionMedecins.ajouterMedecin(medecin);

        assertEquals(medecin, gestionMedecins.rechercherParNumeroOrdre("67890-a"));
        assertNull(gestionMedecins.rechercherParNumeroOrdre("INCONNU"));
    }

    @Test
    void testRechercherParNom() {
        Medecin m1 = mock(Medecin.class);
        when(m1.getNumeroOrdre()).thenReturn("1");
        when(m1.getNom()).thenReturn("Dupont");
        when(m1.getPrenom()).thenReturn("Pierre");

        Medecin m2 = mock(Medecin.class);
        when(m2.getNumeroOrdre()).thenReturn("2");
        when(m2.getNom()).thenReturn("Martin");
        when(m2.getPrenom()).thenReturn("Jean-Pierre");

        gestionMedecins.ajouterMedecin(m1);
        gestionMedecins.ajouterMedecin(m2);

        List<Medecin> resultats = gestionMedecins.rechercherParNom("pierre");
        assertEquals(2, resultats.size());
        assertTrue(resultats.contains(m1));
        assertTrue(resultats.contains(m2));

        List<Medecin> resultatsSpecifiques = gestionMedecins.rechercherParNom("dupo");
        assertEquals(1, resultatsSpecifiques.size());
        assertTrue(resultatsSpecifiques.contains(m1));
    }

    @Test
    void testRechercherParSpecialite() {
        Medecin m1 = mock(Medecin.class);
        when(m1.getNumeroOrdre()).thenReturn("1");
        when(m1.getSpecialite()).thenReturn(Specialite.GENERALISTE);

        Medecin m2 = mock(Medecin.class);
        when(m2.getNumeroOrdre()).thenReturn("2");
        when(m2.getSpecialite()).thenReturn(Specialite.CARDIOLOGUE);

        gestionMedecins.ajouterMedecin(m1);
        gestionMedecins.ajouterMedecin(m2);

        List<Medecin> generalistes = gestionMedecins.rechercherParSpecialite(Specialite.GENERALISTE);
        assertEquals(1, generalistes.size());
        assertTrue(generalistes.contains(m1));
    }

    @Test
    void testMettreAJourMedecin() {
        Medecin medecin = mock(Medecin.class);
        when(medecin.getNumeroOrdre()).thenReturn("123");
        when(medecin.getId()).thenReturn(10);

        gestionMedecins.ajouterMedecin(medecin);

        gestionMedecins.mettreAJourMedecin(10, "Zidane", "Zinédine", 75.0, "0606", "zizou@artois.fr", Specialite.GENERALISTE);

        verify(medecin).setNom("Zidane");
        verify(medecin).setPrenom("Zinédine");
        verify(medecin).setTarifHoraire(75.0);
        verify(medecin).setTelephone("0606");
        verify(medecin).setEmail("zizou@artois.fr");
        verify(medecin).setSpecialite(Specialite.GENERALISTE);
    }

    @Test
    void testSupprimerMedecin() {
        Medecin medecin = mock(Medecin.class);
        when(medecin.getNumeroOrdre()).thenReturn("999");
        when(medecin.getId()).thenReturn(5);

        gestionMedecins.ajouterMedecin(medecin);

        assertFalse(gestionMedecins.supprimerMedecin(888));
        assertEquals(1, gestionMedecins.getNombreMedecins());

        assertTrue(gestionMedecins.supprimerMedecin(5));
        assertEquals(0, gestionMedecins.getNombreMedecins());
    }
}