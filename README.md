# Application de gestion d'un cabinet médical

## Description du projet et des fonctionnalités implémentées

**À COMPLÉTER**

## Membres du groupe

- Lucille Coquerelle 
- Nathan Miont
- Ethan Lagaisse-Brodowski
- Youssef Pirmiet

## Répartition du travail

**TABLEAU À COMPLÉTER**

| Membre du groupe          | Tâches réalisées                                                              | Ticket associé |
|---------------------------|-------------------------------------------------------------------------------|----------------|
| @youssef_pirmiet          | configuration initial pour les loggers                                        | #2             |
| @youssef_pirmiet          | configuration initial pour les loggers                                        | #3             |
| @youssef_pirmiet          | Modification de la méthode stat pour s'adapter aux logger                     | #4             |
| @lucille_coquerelle       | modification de la classe Statistiques                                        | #4             |
| @ethan_lagaisse-brodowski | modification de la classe Recherche                                           | #5             |
| @youssef_pirmiet          | adaptation de la classe recherche et de la classe main aux logger             | #5             |
| @ethan_lagaisse-brodowski | Ticket 6, modification du fichier build.gradle pour utiliser JUnit5, JUnit... | #6             |
| @nathan_miont             | creation de la methode getPrixTotal() dans la classe Ordonnance               | #9             |
| @nathan_miont             | creation de la méthode getPrixTotal() dans la classe RendezVous               | #10            |
| @nathan_miont             | Ajoute de la javaDoc dans la classe Ordonnance                                | #8             |
| @lucille_coquerelle       | faire les tests unitaires de la classe ordonnance                             | #11            |
| @ethan_lagaisse-brodowski | Test unitaire de la classe patient.                                           | #13            |
| @nathan_miont             | faire les tests sur la classe medecins                                        | #14            |
| @lucille_coquerelle       | faire des test Ordonnances                                                    | #12            |
| @youssef_pirmiet          | ajout des tests de GestionRendezVous                                          | #21            |
| @nathan_miont             | creation de la classe TestGestionMédicament                                   | #15            |
| @lucille_coquerelle       | Ajoute de la javaDoc dans la classe Medecin                                   | #8             |
| @lucille_coquerelle       | Remplacer les List par des ObservableList                                     | #31            |
| @youssef_pirmiet          | ajout du TestGestionPatients                                                  | #7             |
| @nathan_miont             | Remplacer les List par des ObservableList                                     | #31            |
| @lucille_coquerelle       | faire la vue                                                                  | #23            |
| @ethan_lagaisse-brodowski | Modification de la classe recherche                                           | #5             |
| @ethan_lagaisse-brodowski | Modification du build.gradle                                                  | #6             |
| @ethan_lagaisse-brodowski | JavaDocs de patient                                                           | #8             |
| @ethan_lagaisse-brodowski | Test unitaire pour patient                                                    | #13            |
| @youssef_pirmiet          | decoupage pour JavaFX                                                         | #32            |
| @nathan_miont             | README.md                                                                     | #33            |
| @lucille_coquerelle       | faire le controller ordonnance                                                | #39            |
| @nathan_miont             | faire le controller de medicament                                             | #37            |
| @nathan_miont             | faire le controller de MedicamentEditController                               | #35            |
| @ethan_lagaisse-brodowski | faire le controller de PatientController                                      | #38            |
| @youssef_pirmiet          | ajout de la vue pour rendez-vous                                              | #21            |
| @youssef_pirmiet          | faire la javaDoc de la classe Patient                                         | #8             |
|                           |                                                                               |                |
|                           |                                                                               |                |
|                           |                                                                               |                |
|                           |                                                                               |                |


## Présentation du code de base

### Diagramme des classes du modèle

```plantuml
@startuml

hide empty members

class Personne {
    - nom : String
    - prenom : String
    - dateNaissance : Date
    - adresse : String
    - telephone : String
}

class Patient {
    - numeroSecuriteSociale : String
    - dateEmbauche : Date
    - tauxRemboursement : double
}

class Medecin {
    - specialite : Specialite
    - numeroOrdre : String
    - tarifHoraire : double
}

class Medicament {
    - nom : String
    - description : String
    - prix : double
}

class Ordonnance {
    - dateEmission : Date
    - dateValidite : Date
    - remarques : String
}

class LignePrescription {
    - quantite : int
    - posologie : String
    - dureeTraitement : int
    - instruction : String
}

class RendezVous {
    - dateHeure : Date
    - dureeMinutes : int
    - motif : String
    - statut : StatutRendezVous
} 

enum Specialite {
    Cardiologie,
    Dermatologie,
    Neurologie,
    Pediatrie,
    Psychiatrie
}

enum StatutRendezVous {
    Planifie,
    Confirme,
    Termine,
    Annule
}

Personne <|-- Patient
Personne <|-- Medecin
Medecin "1" *-- "0..*" RendezVous : consulte
Patient "1" *-- "0..*" RendezVous : demande
Patient "1" *-- "0..*" Ordonnance : prescrite
Medecin "1" *-- "0..*" Ordonnance : délivre
Ordonnance "1" *-- "0..*" LignePrescription : contient
Medicament "1" *-- "0..*"  LignePrescription : prescrit
RendezVous --> StatutRendezVous
Medecin --> Specialite
@enduml
```

### Diagramme des classes "services"

```plantuml
@startuml

top to bottom direction


class GestionMedecins {
  - medecins: List<Medecin>
  + rechercherParSpecialite(Specialite): List<Medecin>
  + ajouterMedecin(Medecin): void
  + rechercherParId(int): Medecin
  + mettreAJourMedecin(int, String, String, double, String, String, Specialite): void
  + getNombreMedecins(): int
  + afficherTousLesMedecins(): void
  + rechercherParNumeroOrdre(String): Medecin
  + listerTousLesMedecins(): List<Medecin>
  + rechercherParNom(String): List<Medecin>
  + supprimerMedecin(int): boolean
}
class GestionMedicaments {
  - medicaments: List<Medicament>
  + rechercherParId(int): Medicament
  + supprimerMedicament(int): boolean
  + ajouterMedicament(Medicament): void
  + listerTousLesMedicaments(): List<Medicament>
  + afficherTousLesMedicaments(): void
  + rechercherParNom(String): List<Medicament>
  + getNombreMedicaments(): int
}
class GestionOrdonnances {
  - ordonnances: List<Ordonnance>
  + listerToutesLesOrdonnances(): List<Ordonnance>
  + ajouterPrescription(int, Medicament, int, String, int, String): void
  + creerOrdonnance(Medecin, Patient, LocalDate, String): Ordonnance
  + getOrdonnancesMedecin(Medecin): List<Ordonnance>
  + getOrdonnancesValides(): List<Ordonnance>
  + getOrdonnancesPatient(Patient): List<Ordonnance>
  + afficherToutesLesOrdonnances(): void
  + rechercherParId(int): Ordonnance
  + getNombreOrdonnances(): int
}
class GestionPatients {
  - patients: List<Patient>
  + rechercherParNumeroSecu(String): Patient
  + mettreAJourPatient(int, String, String, String, String, String, double): void
  + afficherTousLesPatients(): void
  + supprimerPatient(int): void
  + ajouterPatient(Patient): void
  + listerTousLesPatients(): List<Patient>
  + rechercherParId(int): Patient
  + getNombrePatients(): int
  + rechercherParNom(String): List<Patient>
}
class GestionRendezVous {
  - rendezVous: List<RendezVous>
  + prendreRendezVous(Patient, Medecin, LocalDateTime, int, String): RendezVous
  + confirmerRendezVous(int): void
  + getRendezVousParStatut(StatutRendezVous): List<RendezVous>
  + terminerRendezVous(int): boolean
  + getRendezVousPatient(Patient): List<RendezVous>
  + rechercherParId(int): RendezVous
  + annulerRendezVous(int): void
  + getRendezVousMedecin(Medecin): List<RendezVous>
  + estDisponible(Medecin, LocalDateTime): boolean
  + getNombreRendezVous(): int
  + afficherTousLesRendezVous(): void
  + listerTousLesRendezVous(): List<RendezVous>
}
class Main {
  + main(String[]): void
}


Main                -[#595959,dashed]->  GestionMedecins    : "«create»"
Main                -[#595959,dashed]->  GestionMedicaments : "«create»"
Main                -[#595959,dashed]->  GestionOrdonnances : "«create»"
Main                -[#595959,dashed]->  GestionPatients    : "«create»"
Main                -[#595959,dashed]->  GestionRendezVous  : "«create»"
@enduml
```
