package fr.univartois.butinfo.sae.cabinet.modele;

import java.time.LocalDate;
import java.time.Period;

/**
 * Classe représentant un patient du cabinet médical.
 * Hérite de la classe abstraite Personne.
 * Un patient est caractérisé par son adresse, sa date de naissance,
 * son numéro de sécurité sociale et son taux de remboursement.
 *
 * @author Youssef Pirmiet
 * @version 1.0
 */
public class Patient extends Personne {

    /**
     * Adresse postale du patient.
     */
    private String adresse;

    /**
     * Date de naissance du patient.
     */
    private LocalDate dateNaissance;

    /**
     * Numéro de sécurité sociale (identifiant médical unique et immuable).
     */
    private final String numeroSecu;

    /**
     * Taux de remboursement par la sécurité sociale (compris entre 0.0 et 1.0).
     */
    private double tauxRemboursement;

    /**
     * Constructeur complet d'un Patient.
     *
     * @param nom le nom du patient
     * @param prenom le prénom du patient
     * @param telephone le numéro de téléphone du patient
     * @param email l'adresse électronique du patient
     * @param adresse l'adresse postale du patient
     * @param dateNaissance la date de naissance du patient
     * @param numeroSecu le numéro de sécurité sociale du patient
     * @param tauxRemboursement le taux de remboursement du patient
     */
    public Patient(String nom, String prenom, String telephone, String email,
                   String adresse, LocalDate dateNaissance, String numeroSecu, double tauxRemboursement) {
        super(nom, prenom, telephone, email);
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.numeroSecu = numeroSecu;
        this.tauxRemboursement = tauxRemboursement;
    }

    /**
     * Constructeur simplifié d'un Patient avec un taux de remboursement par défaut (75%).
     *
     * @param nom le nom du patient
     * @param prenom le prénom du patient
     * @param telephone le numéro de téléphone du patient
     * @param email l'adresse électronique du patient
     * @param adresse l'adresse postale du patient
     * @param dateNaissance la date de naissance du patient
     * @param numeroSecu le numéro de sécurité sociale du patient
     */
    public Patient(String nom, String prenom, String telephone, String email,
                   String adresse, LocalDate dateNaissance, String numeroSecu) {
        this(nom, prenom, telephone, email, adresse, dateNaissance, numeroSecu, .75);
    }

    // -------------------------
    // Getters et Setters
    // -------------------------

    /**
     * Retourne l'adresse postale du patient.
     *
     * @return l'adresse du patient
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Modifie l'adresse postale du patient.
     *
     * @param adresse la nouvelle adresse du patient
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Retourne la date de naissance du patient.
     *
     * @return la date de naissance
     */
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Modifie la date de naissance du patient.
     *
     * @param dateNaissance la nouvelle date de naissance
     */
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Retourne le numéro de sécurité sociale du patient.
     *
     * @return le numéro de sécu
     */
    public String getNumeroSecu() {
        return numeroSecu;
    }

    /**
     * Retourne le taux de remboursement du patient.
     *
     * @return le taux de remboursement (entre 0.0 et 1.0)
     */
    public double getTauxRemboursement() {
        return tauxRemboursement;
    }

    /**
     * Modifie le taux de remboursement du patient.
     *
     * @param tauxRemboursement le nouveau taux de remboursement
     */
    public void setTauxRemboursement(double tauxRemboursement) {
        this.tauxRemboursement = tauxRemboursement;
    }

    /**
     * Calcule et retourne l'âge actuel du patient.
     *
     * @return l'âge du patient en années
     */
    public int getAge() {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    /**
     * Vérifie l'égalité entre ce patient et un autre objet.
     * Deux patients sont égaux s'ils possèdent le même numéro de sécurité sociale
     * et s'ils respectent les critères d'égalité de la superclasse Personne.
     *
     * @param obj l'objet à comparer
     * @return {@code true} si les patients sont égaux, {@code false} sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Patient autre = (Patient) obj;
        return this.numeroSecu.equals(autre.numeroSecu);
    }

    /**
     * Calcule le code de hachage du patient.
     *
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return super.hashCode() * 31 + numeroSecu.hashCode();
    }

    /**
     * Retourne une représentation textuelle du patient.
     *
     * @return une chaîne décrivant le patient
     */
    @Override
    public String toString() {
        return "Patient  { " + super.toString() +
               " | Naissance: " + dateNaissance +
               " (" + getAge() + " ans)" +
               " | Adresse: " + adresse +
               " | Sécu: " + numeroSecu +
               " | Taux: " + tauxRemboursement + " }";
    }
}
