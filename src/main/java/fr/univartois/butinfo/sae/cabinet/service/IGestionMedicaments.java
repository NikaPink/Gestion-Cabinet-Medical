package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import java.util.List;

/**
 * Interface définissant les opérations de la façade de gestion des médicaments.
 */
public interface IGestionMedicaments {
    void ajouterMedicament(Medicament medicament);
    Medicament rechercherParId(int id);
    List<Medicament> rechercherParNom(String terme);
    boolean supprimerMedicament(int id);
    List<Medicament> listerTousLesMedicaments();
    int getNombreMedicaments();
}
