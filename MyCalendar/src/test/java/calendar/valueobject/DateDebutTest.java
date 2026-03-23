package calendar.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DateDebutTest {

    @Test
    public void creation_valide() {
        DateDebut d = new DateDebut(2025, 6, 15, 9, 30);
        assertEquals(2025, d.annee());
        assertEquals(6,    d.mois());
        assertEquals(15,   d.jour());
        assertEquals(9,    d.heure());
        assertEquals(30,   d.minute());
    }

    @Test
    public void mois_invalide() {
        assertThrows(IllegalArgumentException.class, () -> new DateDebut(2025, 13, 1, 0, 0));
    }

    @Test
    public void heure_invalide() {
        assertThrows(IllegalArgumentException.class, () -> new DateDebut(2025, 1, 1, 24, 0));
    }

    @Test
    public void minute_invalide() {
        assertThrows(IllegalArgumentException.class, () -> new DateDebut(2025, 1, 1, 0, 60));
    }

    @Test
    public void egalite_par_valeur() {
        assertEquals(new DateDebut(2025, 3, 10, 14, 0), new DateDebut(2025, 3, 10, 14, 0));
    }

    @Test
    public void isBefore() {
        DateDebut avant = new DateDebut(2025, 1, 1, 8,  0);
        DateDebut apres = new DateDebut(2025, 1, 1, 10, 0);
        assertTrue(avant.isBefore(apres));
        assertFalse(apres.isBefore(avant));
    }

    @Test
    public void isAfter() {
        DateDebut avant = new DateDebut(2025, 1, 1, 8,  0);
        DateDebut apres = new DateDebut(2025, 1, 1, 10, 0);
        assertTrue(apres.isAfter(avant));
        assertFalse(avant.isAfter(apres));
    }

    @Test
    public void plusMinutes() {
        DateDebut debut = new DateDebut(2025, 1, 1, 23, 30);
        DateDebut fin   = debut.plusMinutes(60);
        assertEquals(new DateDebut(2025, 1, 2, 0, 30), fin);
    }

    @Test
    public void plusJours() {
        DateDebut debut = new DateDebut(2025, 6, 1, 9, 0);
        DateDebut apres = debut.plusJours(7);
        assertEquals(new DateDebut(2025, 6, 8, 9, 0), apres);
    }
}