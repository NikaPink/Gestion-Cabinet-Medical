package fr.univartois.butinfo.sae.cabinet.modele;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;

/**
 * Représente un médecin exerçant dans le cabinet médical.
 * <p>
 * Un médecin est une personne identifiée de manière unique par son numéro
 * d'inscription au Conseil de l'Ordre des Médecins. Il possède également
 * une spécialité médicale et un tarif horaire de consultation.
 * </p>
 *
 * @author Lucille Coquerelle
 * @version 1.0
 */
public class Medecin extends Personne {

  /**
   * Numéro d'identification du médecin auprès du Conseil de l'Ordre.
   */
  private final String numeroOrdre;

  /**
   * Spécialité médicale du médecin.
   */
  private Specialite specialite;

  /**
   * Tarif horaire appliqué par le médecin pour une consultation.
   */
  private double tarifHoraire;

  /**
   * Construit un médecin en initialisant l'ensemble de ses informations.
   *
   * @param nom le nom du médecin
   * @param prenom le prénom du médecin
   * @param telephone le numéro de téléphone du médecin
   * @param email l'adresse électronique du médecin
   * @param numeroOrdre le numéro d'inscription au Conseil de l'Ordre
   * @param specialite la spécialité médicale exercée
   * @param tarifHoraire le tarif horaire des consultations
   */
  public Medecin(String nom, String prenom, String telephone, String email,
                 String numeroOrdre, Specialite specialite, double tarifHoraire) {
    super(nom, prenom, telephone, email);
    this.numeroOrdre = numeroOrdre;
    this.specialite = specialite;
    this.tarifHoraire = tarifHoraire;
  }

  /**
   * Construit un médecin avec un tarif horaire par défaut fixé à 30,00 €.
   *
   * @param nom le nom du médecin
   * @param prenom le prénom du médecin
   * @param telephone le numéro de téléphone du médecin
   * @param email l'adresse électronique du médecin
   * @param numeroOrdre le numéro d'inscription au Conseil de l'Ordre
   * @param specialite la spécialité médicale exercée
   */
  public Medecin(String nom, String prenom, String telephone, String email,
                 String numeroOrdre, Specialite specialite) {
    this(nom, prenom, telephone, email, numeroOrdre, specialite, 30.00);
  }

  /**
   * Retourne le numéro d'inscription au Conseil de l'Ordre.
   *
   * @return le numéro d'ordre du médecin
   */
  public String getNumeroOrdre() {
    return numeroOrdre;
  }

  /**
   * Retourne la spécialité du médecin.
   *
   * @return la spécialité médicale
   */
  public Specialite getSpecialite() {
    return specialite;
  }

  /**
   * Modifie la spécialité du médecin.
   *
   * @param specialite la nouvelle spécialité
   */
  public void setSpecialite(Specialite specialite) {
    this.specialite = specialite;
  }

  /**
   * Retourne le tarif horaire du médecin.
   *
   * @return le tarif horaire de consultation
   */
  public double getTarifHoraire() {
    return tarifHoraire;
  }

  /**
   * Modifie le tarif horaire du médecin.
   *
   * @param tarifHoraire le nouveau tarif horaire
   */
  public void setTarifHoraire(double tarifHoraire) {
    this.tarifHoraire = tarifHoraire;
  }

  /**
   * Compare ce médecin à un autre medecin.
   * <p>
   * Deux médecins sont considérés comme égaux s'ils possèdent le même
   * numéro d'ordre et si les critères d'égalité définis dans la classe
   * {@code Personne} sont respectés.
   * </p>
   *
   * @param obj l'objet à comparer
   * @return {@code true} si les objets sont égaux, {@code false} sinon
   */
  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    Medecin autre = (Medecin) obj;
    return this.numeroOrdre.equals(autre.numeroOrdre);
  }

  /**
   * Calcule le code de hachage du médecin à partir de son numéro d'ordre.
   *
   * @return le code de hachage associé au médecin
   */
  @Override
  public int hashCode() {
    return numeroOrdre.hashCode();
  }

  /**
   * Retourne une représentation textuelle du médecin.
   *
   * @return une chaîne contenant les informations principales du médecin
   */
  @Override
  public String toString() {
    return "Médecin { " + super.toString()
            + " | Spécialité: " + specialite.getLibelle()
            + " | N° Ordre: " + numeroOrdre
            + " | tarif: " + tarifHoraire + " }";
  }
}