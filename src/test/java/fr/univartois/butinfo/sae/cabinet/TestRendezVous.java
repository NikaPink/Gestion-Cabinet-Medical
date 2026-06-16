package fr.univartois.butinfo.sae.cabinet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.mockito.Mockito.when;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith( MockitoExtension.class)
public class TestRendezVous {

    @Mock
    private Medecin medecin;

    @Mock
    private Patient patient;

    @Test
    void testCalculPrixConsultationAvecMocks(){

        when(medecin.getTarifHoraire()).thenReturn(60.0);

        when(patient.getTauxRemboursement()).thenReturn(0.70);

        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 14, 0);
        RendezVous rdv = new RendezVous(patient, medecin, dateRdv, 30,
                "Consultation test");

        double prix = rdv.prixConsultation();

        assertThat(prix).isCloseTo(9.0, within(0.01));


    }


}
