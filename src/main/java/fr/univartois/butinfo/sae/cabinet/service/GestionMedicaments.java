package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion des médicaments disponibles dans le système.
 */
public class GestionMedicaments {

    private final List<Medicament> medicaments;

    /**
     * Constructeur : initialise la liste vide.
     */
    public GestionMedicaments() {
        this.medicaments = new ArrayList<>();
    }

    // -------------------------
    // CRUD
    // -------------------------

    /**
     * Ajoute un médicament s'il n'existe pas déjà (même nom + principe actif).
     */
    public void ajouterMedicament(Medicament medicament) {
        if (medicaments.contains(medicament)) {
            System.out.println("ERREUR : Ce médicament existe déjà.");
            return;
        }
        medicaments.add(medicament);
        System.out.println("Médicament ajouté : " + medicament.getNom());
    }

    /**
     * Recherche un médicament par son ID.
     */
    public Medicament rechercherParId(int id) {
        for (Medicament m : medicaments) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    /**
     * Recherche des médicaments dont le nom contient le terme donné.
     */
    public List<Medicament> rechercherParNom(String terme) {
        List<Medicament> resultats = new ArrayList<>();
        String termeLower = terme.toLowerCase();
        for (Medicament m : medicaments) {
            if (m.getNom().toLowerCase().contains(termeLower)) {
                resultats.add(m);
            }
        }
        return resultats;
    }


    /**
     * Supprime un médicament par son ID.
     */
    public boolean supprimerMedicament(int id) {
        Medicament m = rechercherParId(id);
        if (m == null) {
            System.out.println("ERREUR : Médicament ID=" + id + " introuvable.");
            return false;
        }
        medicaments.remove(m);
        System.out.println("Médicament supprimé : " + m.getNom());
        return true;
    }

    /**
     * Retourne la liste complète des médicaments.
     */
    public List<Medicament> listerTousLesMedicaments() {
        return new ArrayList<>(medicaments);
    }

    /**
     * Retourne le nombre total de médicaments.
     */
    public int getNombreMedicaments() {
        return medicaments.size();
    }


}
