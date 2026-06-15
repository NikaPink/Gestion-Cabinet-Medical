package fr.univartois.butinfo.sae.cabinet.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires courts pour GestionMedicaments.
 */
class TestGestionMedicament {

    private GestionMedicaments gestion;

    @BeforeEach
    void setUp() {
        gestion = new GestionMedicaments();
    }

    @Test
    void testAjouterEtRechercherId() {
        Medicament m = mock(Medicament.class);
        when(m.getId()).thenReturn(1);
        when(m.getNom()).thenReturn("Doliprane");

        gestion.ajouterMedicament(m);

        assertEquals(1, gestion.getNombreMedicaments());
        assertEquals(m, gestion.rechercherParId(1));
        assertNull(gestion.rechercherParId(999)); // ID inconnu
    }

    @Test
    void testPasDeDoublon() {
        Medicament m = mock(Medicament.class);
        when(m.getNom()).thenReturn("Doliprane");

        gestion.ajouterMedicament(m);
        gestion.ajouterMedicament(m); // Même objet -> bloqué par le .contains()

        assertEquals(1, gestion.getNombreMedicaments());
    }

    @Test
    void testRechercherParNom() {
        Medicament m = mock(Medicament.class);
        when(m.getNom()).thenReturn("Doliprane");

        gestion.ajouterMedicament(m);

        // Test de la recherche insensible à la casse ("doli" pour "Doliprane")
        List<Medicament> resultats = gestion.rechercherParNom("doli");
        assertEquals(1, resultats.size());
        assertTrue(resultats.contains(m));

        // Test recherche sans résultat
        assertTrue(gestion.rechercherParNom("Xanax").isEmpty());
    }

    @Test
    void testSupprimer() {
        Medicament m = mock(Medicament.class);
        when(m.getId()).thenReturn(1);

        gestion.ajouterMedicament(m);

        assertTrue(gestion.supprimerMedicament(1));  // Suppression réussie
        assertFalse(gestion.supprimerMedicament(1)); // Déjà supprimé ou inexistant
        assertEquals(0, gestion.getNombreMedicaments());
    }
}