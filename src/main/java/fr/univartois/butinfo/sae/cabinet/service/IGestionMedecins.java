package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import java.util.List;

/**
 * Interface définissant les opérations de la façade de gestion des médecins.
 */
public interface IGestionMedecins {
    void ajouterMedecin(Medecin medecin);
    Medecin rechercherParId(int id);
    Medecin rechercherParNumeroOrdre(String numeroOrdre);
    List<Medecin> rechercherParNom(String terme);
    List<Medecin> rechercherParSpecialite(Specialite specialite);
    void mettreAJourMedecin(int id, String nouveauNom, String nouveauPrenom, double nouveauTarifConsultation,
                            String nouveauTel, String nouveauEmail, Specialite nouvelleSpecialite);
    boolean supprimerMedecin(int id);
    List<Medecin> listerTousLesMedecins();
    int getNombreMedecins();
}
