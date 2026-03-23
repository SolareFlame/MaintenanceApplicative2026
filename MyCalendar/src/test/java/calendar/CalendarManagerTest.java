package calendar;

import calendar.event.Evenement;
import calendar.event.Periodique;
import calendar.event.RendezVous;
import calendar.event.Reunion;
import calendar.valueobject.DateEvenement;
import calendar.valueobject.TitreEvenement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {

    private CalendarManager calendar;

    private static final DateEvenement MON_9AM  = new DateEvenement(2025, 6, 9,  9,  0);
    private static final DateEvenement MON_10AM = new DateEvenement(2025, 6, 9,  10, 0);
    private static final DateEvenement TUE_9H   = new DateEvenement(2025, 6, 10, 9,  0);

    private static final TitreEvenement MEDECIN    = new TitreEvenement("Médecin");
    private static final TitreEvenement STAND_UP   = new TitreEvenement("Stand-up");
    private static final TitreEvenement SPRINT      = new TitreEvenement("Sprint");

    @BeforeEach
    public void setUp() {
        calendar = new CalendarManager();
    }

    @Test
    public void ajout_rdvPersonnel() {
        calendar.ajouter(new RendezVous(MEDECIN, "alice", MON_9AM, 60));
        assertEquals(1, calendar.events.size());
    }

    @Test
    public void ajout_reunion() {
        calendar.ajouter(new Reunion(new TitreEvenement("Sprint review"), "alice", MON_9AM, 90, "Salle A", List.of("bob", "carol")));
        assertEquals(1, calendar.events.size());
    }

    @Test
    public void ajout_periodique() {
        calendar.ajouter(new Periodique(STAND_UP, "alice", MON_9AM, 7));
        assertEquals(1, calendar.events.size());
    }

    @Test
    public void ajout_plusieurs() {
        calendar.ajouter(new RendezVous(new TitreEvenement("RDV 1"), "alice", MON_9AM,  60));
        calendar.ajouter(new RendezVous(new TitreEvenement("RDV 2"), "alice", TUE_9H,   60));
        calendar.ajouter(new Reunion(new TitreEvenement("Réu"),      "alice", MON_10AM, 30, "Salle B", List.of("bob")));
        assertEquals(3, calendar.events.size());
    }

    @Test
    public void periode_rdvDansLaPlage() {
        calendar.ajouter(new RendezVous(new TitreEvenement("Dans la plage"), "alice", MON_9AM, 60));
        DateEvenement debut = new DateEvenement(2025, 6, 1,  0, 0);
        DateEvenement fin   = new DateEvenement(2025, 6, 30, 23, 59);
        assertEquals(1, calendar.eventsDansPeriode(debut, fin).size());
    }

    @Test
    public void periode_rdvHorsPlage() {
        calendar.ajouter(new RendezVous(new TitreEvenement("Hors plage"), "alice", new DateEvenement(2025, 7, 1, 9, 0), 60));
        DateEvenement debut = new DateEvenement(2025, 6, 1,  0, 0);
        DateEvenement fin   = new DateEvenement(2025, 6, 30, 23, 59);
        assertTrue(calendar.eventsDansPeriode(debut, fin).isEmpty());
    }

    @Test
    public void periode_rdvSurBorneDebut() {
        DateEvenement debut = new DateEvenement(2025, 6, 1, 0, 0);
        calendar.ajouter(new RendezVous(new TitreEvenement("Borne début"), "alice", debut, 60));
        DateEvenement fin = new DateEvenement(2025, 6, 30, 23, 59);
        assertEquals(1, calendar.eventsDansPeriode(debut, fin).size());
    }

    @Test
    public void periode_rdvSurBorneFin() {
        DateEvenement fin = new DateEvenement(2025, 6, 30, 23, 59);
        calendar.ajouter(new RendezVous(new TitreEvenement("Borne fin"), "alice", fin, 60));
        DateEvenement debut = new DateEvenement(2025, 6, 1, 0, 0);
        assertEquals(1, calendar.eventsDansPeriode(debut, fin).size());
    }

    @Test
    public void periode_melangeInOut() {
        calendar.ajouter(new RendezVous(new TitreEvenement("Juin"),    "alice", MON_9AM,  60));
        calendar.ajouter(new Reunion(new TitreEvenement("Juin réu"),   "alice", MON_10AM, 30, "Salle A", List.of("bob")));
        calendar.ajouter(new RendezVous(new TitreEvenement("Juillet"), "alice", new DateEvenement(2025, 7, 1, 9, 0), 60));
        DateEvenement debut = new DateEvenement(2025, 6, 1,  0, 0);
        DateEvenement fin   = new DateEvenement(2025, 6, 30, 23, 59);
        assertEquals(2, calendar.eventsDansPeriode(debut, fin).size());
    }

    @Test
    public void periode_periodiqueAvecOccurrenceDansPlage() {
        calendar.ajouter(new Periodique(STAND_UP, "alice", new DateEvenement(2025, 6, 1, 9, 0), 7));
        DateEvenement debut = new DateEvenement(2025, 6, 1,  0, 0);
        DateEvenement fin   = new DateEvenement(2025, 6, 30, 23, 59);
        assertFalse(calendar.eventsDansPeriode(debut, fin).isEmpty());
    }

    @Test
    public void periode_periodiqueHorsPlage() {
        calendar.ajouter(new Periodique(new TitreEvenement("Stand-up tardif"), "alice", new DateEvenement(2025, 7, 1, 9, 0), 7));
        DateEvenement debut = new DateEvenement(2025, 6, 1,  0, 0);
        DateEvenement fin   = new DateEvenement(2025, 6, 30, 23, 59);
        assertTrue(calendar.eventsDansPeriode(debut, fin).isEmpty());
    }

    @Test
    public void conflit_chevauchement() {
        Evenement e1 = new RendezVous(new TitreEvenement("E1"), "alice", MON_9AM,  120);
        Evenement e2 = new RendezVous(new TitreEvenement("E2"), "alice", MON_10AM,  60);
        assertTrue(calendar.conflit(e1, e2));
    }

    @Test
    public void conflit_consecutifsSansChevauchement() {
        Evenement e1 = new RendezVous(new TitreEvenement("E1"), "alice", MON_9AM,  60);
        Evenement e2 = new RendezVous(new TitreEvenement("E2"), "alice", MON_10AM, 60);
        assertFalse(calendar.conflit(e1, e2));
    }

    @Test
    public void conflit_joursDifferents() {
        Evenement e1 = new RendezVous(new TitreEvenement("Lundi"), "alice", MON_9AM, 120);
        Evenement e2 = new RendezVous(new TitreEvenement("Mardi"), "alice", TUE_9H,   60);
        assertFalse(calendar.conflit(e1, e2));
    }

    @Test
    public void conflit_inclusionComplete() {
        Evenement e1 = new RendezVous(new TitreEvenement("Grand"), "alice", MON_9AM,  180);
        Evenement e2 = new RendezVous(new TitreEvenement("Petit"), "alice", MON_10AM,  30);
        assertTrue(calendar.conflit(e1, e2));
    }

    @Test
    public void conflit_symetrique() {
        Evenement e1 = new RendezVous(new TitreEvenement("E1"), "alice", MON_9AM,  120);
        Evenement e2 = new RendezVous(new TitreEvenement("E2"), "alice", MON_10AM,  60);
        assertEquals(calendar.conflit(e1, e2), calendar.conflit(e2, e1));
    }

    @Test
    public void conflit_reunionVsRdv() {
        Evenement reunion = new Reunion(SPRINT, "alice", MON_9AM, 120, "Salle A", List.of("bob"));
        Evenement rdv     = new RendezVous(new TitreEvenement("Urgent"), "alice", MON_10AM, 30);
        assertTrue(calendar.conflit(reunion, rdv));
    }

    @Test
    public void conflit_periodiqueRetourneFalse_bugConnu() {
        Evenement rdv        = new RendezVous(new TitreEvenement("RDV"), "alice", MON_9AM, 120);
        Evenement periodique = new Periodique(STAND_UP, "alice", MON_9AM, 7);
        assertFalse(calendar.conflit(rdv, periodique));
    }

    @Test
    public void description_rdvPersonnel() {
        Evenement e = new RendezVous(MEDECIN, "alice", MON_9AM, 60);
        assertTrue(e.description().contains("Médecin"));
        assertTrue(e.description().contains("2025"));
    }

    @Test
    public void description_reunion() {
        Evenement e = new Reunion(SPRINT, "alice", MON_9AM, 90, "Salle A", List.of("bob", "carol"));
        assertTrue(e.description().contains("Salle A"));
        assertTrue(e.description().contains("bob"));
    }

    @Test
    public void description_periodique() {
        Evenement e = new Periodique(STAND_UP, "alice", MON_9AM, 7);
        assertTrue(e.description().contains("Stand-up"));
        assertTrue(e.description().contains("7"));
    }

    @Test
    public void description_nonVide() {
        List<Evenement> events = List.of(
                new RendezVous(new TitreEvenement("RDV"),      "alice", MON_9AM, 60),
                new Reunion(new TitreEvenement("Réunion"),     "alice", MON_9AM, 60, "Salle", List.of("bob")),
                new Periodique(new TitreEvenement("Stand-up"), "alice", MON_9AM, 7)
        );
        for (Evenement e : events) {
            assertFalse(e.description().isBlank());
        }
    }
}