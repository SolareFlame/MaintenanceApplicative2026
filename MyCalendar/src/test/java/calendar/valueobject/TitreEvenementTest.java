package calendar.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TitreEvenementTest {

    @Test
    public void creation_valide() {
        TitreEvenement titre = new TitreEvenement("Réunion mensuelle");
        assertEquals("Réunion mensuelle", titre.valeur());
    }

    @Test
    public void null_interdit() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(null));
    }

    @Test
    public void vide_interdit() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(""));
    }

    @Test
    public void blancs_interdits() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement("   "));
    }

    @Test
    public void egalite_par_valeur() {
        assertEquals(new TitreEvenement("Stand-up"), new TitreEvenement("Stand-up"));
    }

    @Test
    public void inegalite() {
        assertNotEquals(new TitreEvenement("A"), new TitreEvenement("B"));
    }

    @Test
    public void toString_retourne_valeur() {
        assertEquals("Médecin", new TitreEvenement("Médecin").toString());
    }
}