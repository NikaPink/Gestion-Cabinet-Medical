package test.java.fr.univartois.butinfo.sae.cabinet;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
class TestOrdonnance {

    @Mock
    private Medecin medecin;

    @Mock
    private Patient patient;

    @InjectMocks
    private Ordonnance ordonnance;

    @BeforeAll
    void CreerOrdonnance(){
        ordonnance = new Ordonnance(medecin, patient);
    }

    @Test
    void testCreerOrdonnance(){
        int prix=0;
        int p=ordonnance.getPrixTotal();
        assertThat(p.equals(prix));
    }

    @Test
    void testEquals(){
        Ordonnance o= new Ordonnance(medecin, patient);
        assertThat(o.equals(ordonnance));
    }

    @Test
    void testValidite(){
        ordonnance.setDateValidite(LocalDateTime.of(2025, 6, 10, 14, 0));
        assertThat(ordonnance).isFalse();
    }
}
