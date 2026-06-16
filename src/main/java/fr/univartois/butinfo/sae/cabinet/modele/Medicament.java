package fr.univartois.butinfo.sae.cabinet.modele;

import java.util.Objects;

/**
 * Classe représentant un médicament disponible dans le système.
 */
public class Medicament {

    private final int id;
    private String nom;
    private String principeActif;
    private double prix;

    private static int compteurId = 1;

    /**
     * Constructeur complet d'un Médicament.
     */
    public Medicament(String nom, String principeActif, double prix) {
        this.id = compteurId++;
        this.nom = nom;
        this.principeActif = principeActif;
        this.prix = prix;
    }

    // -------------------------
    // Getters et Setters
    // -------------------------

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrincipeActif() {
        return principeActif;
    }

    public void setPrincipeActif(String principeActif) {
        this.principeActif = principeActif;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * Deux médicaments sont égaux s'ils ont le même nom et le même principe actif.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Medicament autre = (Medicament) obj;
        return this.nom.equalsIgnoreCase(autre.nom) &&
                this.principeActif.equalsIgnoreCase(autre.principeActif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom.toLowerCase(), principeActif.toLowerCase());
    }

    /**
     * Permet un affichage propre et simple dans la ListView de JavaFX.
     */
    @Override
    public String toString() {
        return nom;
    }
}