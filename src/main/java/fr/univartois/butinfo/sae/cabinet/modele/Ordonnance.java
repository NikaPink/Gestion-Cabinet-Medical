package fr.univartois.butinfo.sae.cabinet.modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une ordonnance médicale. Une ordonnance est émise par un médecin pour un
 * patient et contient une liste de lignes de prescription.
 *
 * @author Nathan Miont
 * @version 1.0
 */
public class Ordonnance {

    /**
     * L'identifiant unique de l'ordonnance.
     */
    private final int id;

    /**
     * Le médecin ayant émis l'ordonnance.
     */
    private final Medecin medecin;

    /**
     * Le patient à qui est destinée l'ordonnance.
     */
    private final Patient patient;

    /**
     * La date d'émission de l'ordonnance.
     */
    private final LocalDate dateEmission;

    /**
     * La date de validité de l'ordonnance.
     */
    private LocalDate dateValidite;

    /**
     * La liste des lignes de prescription contenues dans l'ordonnance.
     */
    private final List<LignePrescription> prescriptions;

    /**
     * Les remarques ou consignes particulières liées à l'ordonnance.
     */
    private String remarques;

    /**
     * Le compteur statique permettant de générer des identifiants uniques.
     */
    private static int compteurId = 1;

    /**
     * Constructeur complet d'une Ordonnance. La liste des prescriptions est initialisée vide.
     *
     * @param medecin      Le médecin émetteur.
     * @param patient      Le patient concerné.
     * @param dateValidite La date jusqu'à laquelle l'ordonnance est valable.
     * @param remarques    Les consignes textuelles additionnelles.
     */
    public Ordonnance(Medecin medecin, Patient patient, LocalDate dateValidite, String remarques) {
        this.id = compteurId++;
        this.medecin = medecin;
        this.patient = patient;
        this.dateEmission = LocalDate.now();
        this.dateValidite = dateValidite;
        this.prescriptions = new ArrayList<>();
        this.remarques = remarques;
    }

    public Ordonnance(Medecin medecin, Patient patient) {
        this(medecin, patient, LocalDate.now().plusMonths(3), "");
    }

    // -------------------------
    // Gestion des prescriptions
    // -------------------------

    /**
     * Ajoute une ligne de prescription à l'ordonnance.
     *
     * @param ligne La ligne de prescription à ajouter.
     */
    public void ajouterPrescription(LignePrescription ligne) {
        prescriptions.add(ligne);
    }

    /**
     * Supprime une ligne de prescription de l'ordonnance en fonction du médicament spécifié.
     *
     * @param medicament Le médicament à retirer de l'ordonnance.
     * @return {@code true} si une ligne a été supprimée, {@code false} sinon.
     */
    public boolean supprimerPrescription(Medicament medicament) {
        return prescriptions.removeIf(l -> l.getMedicament().equals(medicament));
    }

    // -------------------------
    // Getters et Setters
    // -------------------------

    /**
     * Retourne l'identifiant unique de l'ordonnance.
     *
     * @return L'identifiant de l'ordonnance.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le médecin ayant prescrit l'ordonnance.
     *
     * @return Le médecin de l'ordonnance.
     */
    public Medecin getMedecin() {
        return medecin;
    }

    /**
     * Retourne le patient lié à l'ordonnance.
     *
     * @return Le patient bénéficiaire.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Retourne la date à laquelle l'ordonnance a été créée.
     *
     * @return La date d'émission.
     */
    public LocalDate getDateEmission() {
        return dateEmission;
    }

    /**
     * Retourne la date limite de validité de l'ordonnance.
     *
     * @return La date de validité.
     */
    public LocalDate getDateValidite() {
        return dateValidite;
    }

    /**
     * Modifie la date de validité de l'ordonnance.
     *
     * @param dateValidite La nouvelle date de validité.
     */
    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    /**
     * Retourne une copie de la liste des prescriptions afin de préserver l'encapsulation.
     *
     * @return Une copie défensive de la liste des lignes de prescription.
     */
    public List<LignePrescription> getPrescriptions() {
        return new ArrayList<>(prescriptions);
    }

    /**
     * Retourne les remarques associées à l'ordonnance.
     *
     * @return Les remarques textuelles.
     */
    public String getRemarques() {
        return remarques;
    }

    /**
     * Modifie les remarques associées à l'ordonnance.
     *
     * @param remarques Les nouvelles remarques.
     */
    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    /**
     * Vérifie si l'ordonnance est encore valide à la date d'aujourd'hui.
     *
     * @return {@code true} si la date actuelle n'a pas dépassé la date de validité, {@code false} sinon.
     */
    public boolean estValide() {
        return !LocalDate.now().isAfter(dateValidite);
    }

    /**
     * Compare l'ordonnance courante avec un autre objet.
     * Deux ordonnances sont considérées comme égales si elles partagent le même identifiant.
     *
     * @param obj L'objet à comparer avec l'ordonnance.
     * @return {@code true} si les objets sont égaux, {@code false} sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Ordonnance o = (Ordonnance) obj;
        return id == o.id;
    }

    /**
     * Génère le code de hachage de l'ordonnance basé sur son identifiant.
     *
     * @return Le hashcode de l'identifiant.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Calcul du prix total de l'ordonnance en fonction des prescriptions.
     * @return Le prix total de l'ordonnance représentant le reste à payer pour le patient.
     */
    public double getPrixTotal() {
        double totalPrestations = 0.0;

        for (int i = 0; i < prescriptions.size(); i++) {
            LignePrescription lp = prescriptions.get(i);

            double prixUnitaire = lp.getMedicament().getPrix();
            int quantite = lp.getQuantite();

            totalPrestations += prixUnitaire * quantite;
        }

        return totalPrestations * (1.0 - patient.getTauxRemboursement());
    }

    /**
     *
     * @return Une chaîne de caractères représentant l'ordonnance.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ORDONNANCE N°").append(id).append(" ===\n");
        sb.append("  Médecin  : Dr ").append(medecin.getNomComplet())
                .append(" (").append(medecin.getSpecialite().getLibelle()).append(")\n");
        sb.append("  Patient  : ").append(patient.getNomComplet()).append("\n");
        sb.append("  Émise le : ").append(dateEmission).append("\n");
        sb.append("  Valide jusqu'au : ").append(dateValidite).append("\n");
        sb.append("  Médicaments prescrits :\n");
        for (LignePrescription lp : prescriptions) {
            sb.append(lp.toString()).append("\n");
        }
        sb.append("  Prix total estimé : ").append(String.format("%.2f €", getPrixTotal())).append("\n");
        if (!remarques.isEmpty()) {
            sb.append("  Remarques : ").append(remarques).append("\n");
        }
        return sb.toString();
    }
}