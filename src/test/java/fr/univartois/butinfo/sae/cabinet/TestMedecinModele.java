package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import org.junit.jupiter.api.Test;

/**
 * Tests du modèle Medecin (et, par héritage, de la classe Personne).
 */
class TestMedecinModele {

    @Test
    void testGettersEtNomComplet() {
        Medecin m = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.CARDIOLOGUE, 50.0);

        assertThat(m.getId()).isPositive();
        assertThat(m.getNom()).isEqualTo("Curie");
        assertThat(m.getPrenom()).isEqualTo("Marie");
        assertThat(m.getTelephone()).isEqualTo("0102030405");
        assertThat(m.getEmail()).isEqualTo("m.curie@cabinet.fr");
        assertThat(m.getNumeroOrdre()).isEqualTo("ORD-1");
        assertThat(m.getSpecialite()).isEqualTo(Specialite.CARDIOLOGUE);
        assertThat(m.getTarifHoraire()).isEqualTo(50.0);
        assertThat(m.getNomComplet()).isEqualTo("Marie Curie");
    }

    @Test
    void testConstructeurTarifParDefaut() {
        Medecin m = new Medecin("Martin", "Bob", "06", "bob@cabinet.fr",
                "ORD-2", Specialite.PEDIATRE);
        assertThat(m.getTarifHoraire()).isEqualTo(30.0);
    }

    @Test
    void testSetters() {
        Medecin m = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.CARDIOLOGUE, 50.0);

        m.setNom("Durand");
        m.setPrenom("Alice");
        m.setTelephone("0700000000");
        m.setEmail("alice@cabinet.fr");
        m.setSpecialite(Specialite.DERMATOLOGUE);
        m.setTarifHoraire(60.0);

        assertThat(m.getNom()).isEqualTo("Durand");
        assertThat(m.getPrenom()).isEqualTo("Alice");
        assertThat(m.getTelephone()).isEqualTo("0700000000");
        assertThat(m.getEmail()).isEqualTo("alice@cabinet.fr");
        assertThat(m.getSpecialite()).isEqualTo(Specialite.DERMATOLOGUE);
        assertThat(m.getTarifHoraire()).isEqualTo(60.0);
    }

    @Test
    void testEqualsEtHashCode() {
        Medecin m = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.CARDIOLOGUE, 50.0);
        Medecin autre = new Medecin("Martin", "Bob", "06", "bob@cabinet.fr",
                "ORD-2", Specialite.PEDIATRE);

        assertThat(m.equals(m)).isTrue();
        assertThat(m.equals(null)).isFalse();
        assertThat(m.equals("texte")).isFalse();
        // IDs différents -> médecins différents.
        assertThat(m).isNotEqualTo(autre);
        assertThat(m.hashCode()).isEqualTo("ORD-1".hashCode());
    }

    @Test
    void testToString() {
        Medecin m = new Medecin("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "ORD-1", Specialite.CARDIOLOGUE, 50.0);
        assertThat(m.toString()).contains("Médecin", "Marie", "Cardiologie", "ORD-1");
    }
}
