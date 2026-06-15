package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface définissant les opérations de la façade de gestion des ordonnances.
 */
public interface IGestionOrdonnances {
    Ordonnance creerOrdonnance(Medecin medecin, Patient patient, LocalDate dateValidite, String remarques);
    void ajouterPrescription(int ordonnanceId, Medicament medicament, int quantite,
                            String posologie, int dureeJours, String instructions);
    Ordonnance rechercherParId(int id);
    List<Ordonnance> getOrdonnancesPatient(Patient patient);
    List<Ordonnance> getOrdonnancesMedecin(Medecin medecin);
    List<Ordonnance> getOrdonnancesValides();
    List<Ordonnance> listerToutesLesOrdonnances();
    int getNombreOrdonnances();
}
