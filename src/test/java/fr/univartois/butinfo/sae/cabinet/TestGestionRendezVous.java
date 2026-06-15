package test.java.fr.univartois.butinfo.sae.cabinet;


@ExtendWith(MockitoExtension.class)
public class TestGestionRendezVous {
    @Mock
    private Medecin medecin;
    @Mock
    private Patient patient;
    // GestionRendezVous est une classe de service
    // C'est une classe concréte à tester
    @InjectMocks
    private GestionRendezVous gestionRendezVous;

    @Test
    void testPrendreRendezVousSucces() {
        when(medecin.getNomComplet()).thenReturn("Jean Dupont");
        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 15, 0);
        // On teste la création
        RendezVous rdv = gestionRendezVous.prendreRendezVous(
                patient, medecin, dateRdv, 20, "Suivi");

        assertThat(rdv).isNotNull();
        assertThat(rdv.getMedecin()).isEqualTo(medecin);
        assertThat(rdv.getPatient()).isEqualTo(patient);
        assertThat(gestionRendezVous.getNombreRendezVous()).isEqualTo(1);
    }

    @Test
    void testPrendreRendezVousEchecSiIndisponible() {
        when(medecin.getNomComplet()).thenReturn("Jean Dupont");

        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 16, 0);

        // On ajoute un premier RDV
        gestionRendezVous.prendreRendezVous(patient, medecin, dateRdv, 30, "RDV 1");

        // On essaie d'ajouter un second RDV au même moment pour le meme médecin
        RendezVous rdv2 = gestionRendezVous.prendreRendezVous(patient, medecin, dateRdv, 30, "RDV 2");

        assertThat(rdv2).isNull();
        assertThat(gestionRendezVous.getNombreRendezVous()).isEqualTo(1);
    }

    @Test
    void testTerminerRendezVousEchec() {
        when(medecin.getNomComplet()).thenReturn("Jean Dupont");
        // On teste la suppression
        boolean echec = gestionRendezVous.terminerRendezVous(50);

        assertThat(echec).isFalse();
    }

    @Test
    void testTerminerRendezVousEchecSucces() {
        when(medecin.getNomComplet()).thenReturn("Jean Dupont");

        // On ajoute un premier RDV
        boolean succes=gestionRendezVous.terminerRendezVous(1);

        assertThat(succces).isTrue();
    }



}
