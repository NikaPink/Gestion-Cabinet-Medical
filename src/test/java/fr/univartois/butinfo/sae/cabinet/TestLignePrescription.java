package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.modele.LignePrescription;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import org.junit.jupiter.api.Test;

class TestLignePrescription {

    @Test
    void testConstructeurCompletEtGetters() {
        Medicament medicament = new Medicament("Doliprane", "Paracétamol", 2.5);
        LignePrescription ligne = new LignePrescription(medicament, 3, "1 le matin", 7, "Avant repas");

        assertThat(ligne.getMedicament()).isEqualTo(medicament);
        assertThat(ligne.getQuantite()).isEqualTo(3);
        assertThat(ligne.getPosologie()).isEqualTo("1 le matin");
        assertThat(ligne.getDureeTraitement()).isEqualTo(7);
        assertThat(ligne.getInstructions()).isEqualTo("Avant repas");
    }

    @Test
    void testConstructeurSimplifie() {
        Medicament medicament = new Medicament("Doliprane", "Paracétamol", 2.5);
        LignePrescription ligne = new LignePrescription(medicament, "1 le soir", 10);

        assertThat(ligne.getQuantite()).isEqualTo(1);
        assertThat(ligne.getInstructions()).isEqualTo("Aucune instruction particulière");
    }

    @Test
    void testSetters() {
        Medicament medicament = new Medicament("Doliprane", "Paracétamol", 2.5);
        LignePrescription ligne = new LignePrescription(medicament, "1/j", 5);

        ligne.setQuantite(2);
        ligne.setPosologie("2 le matin");
        ligne.setDureeTraitement(14);
        ligne.setInstructions("Avec un verre d'eau");

        assertThat(ligne.getQuantite()).isEqualTo(2);
        assertThat(ligne.getPosologie()).isEqualTo("2 le matin");
        assertThat(ligne.getDureeTraitement()).isEqualTo(14);
        assertThat(ligne.getInstructions()).isEqualTo("Avec un verre d'eau");
    }

    @Test
    void testToString() {
        Medicament medicament = new Medicament("Doliprane", "Paracétamol", 2.5);
        LignePrescription ligne = new LignePrescription(medicament, 2, "1/j", 5, "RAS");
        assertThat(ligne.toString()).contains("Doliprane");
    }
}
