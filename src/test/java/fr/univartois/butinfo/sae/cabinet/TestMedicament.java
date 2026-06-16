package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import org.junit.jupiter.api.Test;

class TestMedicament {

    @Test
    void testGetters() {
        Medicament m = new Medicament("Doliprane", "Paracétamol", 2.5);
        assertThat(m.getId()).isPositive();
        assertThat(m.getNom()).isEqualTo("Doliprane");
        assertThat(m.getPrincipeActif()).isEqualTo("Paracétamol");
        assertThat(m.getPrix()).isEqualTo(2.5);
    }

    @Test
    void testSetters() {
        Medicament m = new Medicament("Doliprane", "Paracétamol", 2.5);
        m.setNom("Efferalgan");
        m.setPrincipeActif("Paracétamol 1000");
        m.setPrix(3.0);

        assertThat(m.getNom()).isEqualTo("Efferalgan");
        assertThat(m.getPrincipeActif()).isEqualTo("Paracétamol 1000");
        assertThat(m.getPrix()).isEqualTo(3.0);
    }

    @Test
    void testEqualsEtHashCode() {
        Medicament m1 = new Medicament("Doliprane", "Paracétamol", 2.5);
        // Même nom et même principe actif (insensible à la casse) -> égaux.
        Medicament m2 = new Medicament("doliprane", "paracétamol", 9.9);
        Medicament autre = new Medicament("Aspirine", "Acide", 1.0);

        assertThat(m1).isEqualTo(m2);
        assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
        assertThat(m1.equals(m1)).isTrue();
        assertThat(m1).isNotEqualTo(autre);
        assertThat(m1.equals(null)).isFalse();
        assertThat(m1.equals("texte")).isFalse();
    }

    @Test
    void testToString() {
        Medicament m = new Medicament("Doliprane", "Paracétamol", 2.5);
        assertThat(m.toString()).isEqualTo("Doliprane");
    }
}
