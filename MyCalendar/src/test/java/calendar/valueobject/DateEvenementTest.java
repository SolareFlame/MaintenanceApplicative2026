package calendar.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DateEvenementTest {

    @Test
    public void creation_valide() {
        DateEvenement d = new DateEvenement(2025, 6, 15, 9, 30);
        assertEquals(2025, d.annee());
        assertEquals(6,    d.mois());
        assertEquals(15,   d.jour());
        assertEquals(9,    d.heure());
        assertEquals(30,   d.minute());
    }

    @Test
    public void mois_invalide() {
        assertThrows(IllegalArgumentException.class, () -> new DateEvenement(2025, 13, 1, 0, 0));
    }

    @Test
    public void heure_invalide() {
        assertThrows(IllegalArgumentException.class, () -> new DateEvenement(2025, 1, 1, 24, 0));
    }

    @Test
    public void minute_invalide() {
        assertThrows(IllegalArgumentException.class, () -> new DateEvenement(2025, 1, 1, 0, 60));
    }

    @Test
    public void egalite_par_valeur() {
        assertEquals(new DateEvenement(2025, 3, 10, 14, 0), new DateEvenement(2025, 3, 10, 14, 0));
    }

    @Test
    public void isBefore() {
        DateEvenement avant = new DateEvenement(2025, 1, 1, 8,  0);
        DateEvenement apres = new DateEvenement(2025, 1, 1, 10, 0);
        assertTrue(avant.isBefore(apres));
        assertFalse(apres.isBefore(avant));
    }

    @Test
    public void isAfter() {
        DateEvenement avant = new DateEvenement(2025, 1, 1, 8,  0);
        DateEvenement apres = new DateEvenement(2025, 1, 1, 10, 0);
        assertTrue(apres.isAfter(avant));
        assertFalse(avant.isAfter(apres));
    }

    @Test
    public void plusMinutes() {
        DateEvenement debut = new DateEvenement(2025, 1, 1, 23, 30);
        DateEvenement fin   = debut.plusMinutes(60);
        assertEquals(new DateEvenement(2025, 1, 2, 0, 30), fin);
    }

    @Test
    public void plusJours() {
        DateEvenement debut = new DateEvenement(2025, 6, 1, 9, 0);
        DateEvenement apres = debut.plusJours(7);
        assertEquals(new DateEvenement(2025, 6, 8, 9, 0), apres);
    }
}