package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface définissant les opérations de la façade de gestion des rendez-vous.
 */
public interface IGestionRendezVous {
    RendezVous prendreRendezVous(Patient patient, Medecin medecin, LocalDateTime dateHeure, int dureeMinutes, String motif);
    void annulerRendezVous(int id);
    void confirmerRendezVous(int id);
    boolean terminerRendezVous(int id);
    RendezVous rechercherParId(int id);
    List<RendezVous> getRendezVousPatient(Patient patient);
    List<RendezVous> getRendezVousMedecin(Medecin medecin);
    List<RendezVous> getRendezVousParStatut(StatutRendezVous statut);
    boolean estDisponible(Medecin medecin, LocalDateTime dateHeure);
    List<RendezVous> listerTousLesRendezVous();
    int getNombreRendezVous();
}
