package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import java.util.List;

/**
 * Interface définissant les opérations de la façade de gestion des patients.
 */
public interface IGestionPatients {
    void ajouterPatient(Patient patient);
    Patient rechercherParId(int id);
    Patient rechercherParNumeroSecu(String numeroSecu);
    List<Patient> rechercherParNom(String terme);
    void mettreAJourPatient(int id, String nouveauNom, String nouveauPrenom,
                            String nouveauTel, String nouveauEmail,
                            String nouvelleAdresse, double nouveauTauxRemboursement);
    void supprimerPatient(int id);
    List<Patient> listerTousLesPatients();
    int getNombrePatients();
}
