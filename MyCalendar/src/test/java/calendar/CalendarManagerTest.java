package calendar;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {

    private CalendarManager calendar;

    private static final LocalDateTime MON_9AM = LocalDateTime.of(2025, 6, 9,  9,  0);
    private static final LocalDateTime MON_10AM = LocalDateTime.of(2025, 6, 9,  10, 0);

    private static final LocalDateTime TUE_9H  = LocalDateTime.of(2025, 6, 10, 9,  0);

    @BeforeEach
    public void setUp() {
        calendar = new CalendarManager();
    }


    @Test
    public void ajout_rdvPersonnel() {
        calendar.ajouterEvent("RDV_PERSONNEL", "Médecin", "alice", MON_9AM, 60, "", "", 0);
        assertEquals(1, calendar.events.size());
    }

    @Test
    public void ajout_reunion() {
        calendar.ajouterEvent("REUNION", "Sprint review", "alice", MON_9AM, 90, "Salle A", "bob,carol", 0);
        assertEquals(1, calendar.events.size());
    }

    @Test
    public void ajout_periodique() {
        calendar.ajouterEvent("PERIODIQUE", "Stand-up", "alice", MON_9AM, 0, "", "", 7);
        assertEquals(1, calendar.events.size());
    }

    @Test
    public void ajout_plusieurs() {
        calendar.ajouterEvent("RDV_PERSONNEL", "RDV 1", "alice", MON_9AM,  60, "", "", 0);
        calendar.ajouterEvent("RDV_PERSONNEL", "RDV 2", "alice", TUE_9H,  60, "", "", 0);
        calendar.ajouterEvent("REUNION",        "Réu",   "alice", MON_10AM, 30, "Salle B", "bob", 0);
        assertEquals(3, calendar.events.size());
    }


    @Test
    public void periode_rdvDansLaPlage() {
        calendar.ajouterEvent("RDV_PERSONNEL", "Dans la plage", "alice", MON_9AM, 60, "", "", 0);

        LocalDateTime debut = LocalDateTime.of(2025, 6, 1,  0, 0);
        LocalDateTime fin   = LocalDateTime.of(2025, 6, 30, 23, 59);

        List<Event> resultats = calendar.eventsDansPeriode(debut, fin);
        assertEquals(1, resultats.size());
    }

    @Test
    public void periode_rdvHorsPlage() {
        calendar.ajouterEvent("RDV_PERSONNEL", "Hors plage", "alice",
                LocalDateTime.of(2025, 7, 1, 9, 0), 60, "", "", 0);

        LocalDateTime debut = LocalDateTime.of(2025, 6, 1,  0, 0);
        LocalDateTime fin   = LocalDateTime.of(2025, 6, 30, 23, 59);

        assertTrue(calendar.eventsDansPeriode(debut, fin).isEmpty());
    }

    @Test
    public void periode_rdvSurBorneDebut() {
        LocalDateTime debut = LocalDateTime.of(2025, 6, 1, 0, 0);
        calendar.ajouterEvent("RDV_PERSONNEL", "Borne début", "alice", debut, 60, "", "", 0);

        LocalDateTime fin = LocalDateTime.of(2025, 6, 30, 23, 59);
        assertEquals(1, calendar.eventsDansPeriode(debut, fin).size());
    }

    @Test
    public void periode_rdvSurBorneFin() {
        LocalDateTime fin = LocalDateTime.of(2025, 6, 30, 23, 59);
        calendar.ajouterEvent("RDV_PERSONNEL", "Borne fin", "alice", fin, 60, "", "", 0);

        LocalDateTime debut = LocalDateTime.of(2025, 6, 1, 0, 0);
        assertEquals(1, calendar.eventsDansPeriode(debut, fin).size());
    }

    @Test
    public void periode_melangeInOut() {
        calendar.ajouterEvent("RDV_PERSONNEL", "Juin",    "alice", MON_9AM, 60, "", "", 0);
        calendar.ajouterEvent("REUNION",       "Juin réu","alice", MON_10AM,30, "Salle A", "bob", 0);
        calendar.ajouterEvent("RDV_PERSONNEL", "Juillet", "alice",
                LocalDateTime.of(2025, 7, 1, 9, 0), 60, "", "", 0);

        LocalDateTime debut = LocalDateTime.of(2025, 6, 1,  0, 0);
        LocalDateTime fin   = LocalDateTime.of(2025, 6, 30, 23, 59);

        assertEquals(2, calendar.eventsDansPeriode(debut, fin).size());
    }

    @Test
    public void periode_periodiqueAvecOccurrenceDansPlage() {
        // Commence le 1er juin, toutes les semaines → occurrence le 1, 8, 15, 22, 29 juin
        calendar.ajouterEvent("PERIODIQUE", "Stand-up", "alice",
                LocalDateTime.of(2025, 6, 1, 9, 0), 0, "", "", 7);

        LocalDateTime debut = LocalDateTime.of(2025, 6, 1,  0, 0);
        LocalDateTime fin   = LocalDateTime.of(2025, 6, 30, 23, 59);

        assertFalse(calendar.eventsDansPeriode(debut, fin).isEmpty());
    }

    @Test
    public void periode_periodiqueHorsPlage() {
        calendar.ajouterEvent("PERIODIQUE", "Stand-up tardif", "alice",
                LocalDateTime.of(2025, 7, 1, 9, 0), 0, "", "", 7);

        LocalDateTime debut = LocalDateTime.of(2025, 6, 1,  0, 0);
        LocalDateTime fin   = LocalDateTime.of(2025, 6, 30, 23, 59);

        assertTrue(calendar.eventsDansPeriode(debut, fin).isEmpty());
    }

    @Test
    public void conflit_chevauchement() {
        Event e1 = new Event("RDV_PERSONNEL", "E1", "alice", MON_9AM,  120, "", "", 0);
        Event e2 = new Event("RDV_PERSONNEL", "E2", "alice", MON_10AM,  60, "", "", 0);

        assertTrue(calendar.conflit(e1, e2));
    }

    @Test
    public void conflit_consecutifsSansChevauchement() {
        Event e1 = new Event("RDV_PERSONNEL", "E1", "alice", MON_9AM,  60, "", "", 0);
        Event e2 = new Event("RDV_PERSONNEL", "E2", "alice", MON_10AM, 60, "", "", 0);

        assertFalse(calendar.conflit(e1, e2));
    }

    @Test
    public void conflit_joursDifferents() {
        Event e1 = new Event("RDV_PERSONNEL", "Lundi", "alice", MON_9AM, 120, "", "", 0);
        Event e2 = new Event("RDV_PERSONNEL", "Mardi", "alice", TUE_9H,  60, "", "", 0);

        assertFalse(calendar.conflit(e1, e2));
    }

    @Test
    public void conflit_inclusionComplete() {
        Event e1 = new Event("RDV_PERSONNEL", "Grand", "alice", MON_9AM,  180, "", "", 0);
        Event e2 = new Event("RDV_PERSONNEL", "Petit", "alice", MON_10AM,  30, "", "", 0);

        assertTrue(calendar.conflit(e1, e2));
    }

    @Test
    public void conflit_symetrique() {
        Event e1 = new Event("RDV_PERSONNEL", "E1", "alice", MON_9AM,  120, "", "", 0);
        Event e2 = new Event("RDV_PERSONNEL", "E2", "alice", MON_10AM,  60, "", "", 0);

        assertEquals(calendar.conflit(e1, e2), calendar.conflit(e2, e1));
    }

    @Test
    public void conflit_reunionVsRdv() {
        Event reunion = new Event("REUNION",        "Sprint", "alice", MON_9AM,  120, "Salle A", "bob", 0);
        Event rdv     = new Event("RDV_PERSONNEL", "Urgent",  "alice", MON_10AM,  30, "", "", 0);

        assertTrue(calendar.conflit(reunion, rdv));
    }

    @Test
    public void conflit_periodiqueRetourneFalse_bugConnu() {

        Event rdv       = new Event("RDV_PERSONNEL", "RDV",      "alice", MON_9AM, 120, "", "", 0);
        Event periodique= new Event("PERIODIQUE",    "Stand-up", "alice", MON_9AM,  60, "", "", 7);

        assertFalse(calendar.conflit(rdv, periodique), "Bug connu : conflit avec PERIODIQUE retourne toujours false");
    }


    @Test
    public void description_rdvPersonnel() {
        Event e = new Event("RDV_PERSONNEL", "Médecin", "alice", MON_9AM, 60, "", "", 0);
        String desc = e.description();
        assertTrue(desc.contains("Médecin"), "La description doit contenir le titre");
        assertTrue(desc.contains("2025"),    "La description doit contenir l'année");
    }

    @Test
    public void description_reunion() {
        Event e = new Event("REUNION", "Sprint", "alice", MON_9AM, 90, "Salle A", "bob,carol", 0);
        String desc = e.description();
        assertTrue(desc.contains("Salle A"),   "La description doit contenir le lieu");
        assertTrue(desc.contains("bob,carol"), "La description doit contenir les participants");
    }

    @Test
    public void description_periodique() {
        Event e = new Event("PERIODIQUE", "Stand-up", "alice", MON_9AM, 0, "", "", 7);
        String desc = e.description();
        assertTrue(desc.contains("Stand-up"), "La description doit contenir le titre");
        assertTrue(desc.contains("7"),        "La description doit contenir la fréquence");
    }

    @Test
    public void description_nonVide() {
        List<Event> events = List.of(
                new Event("RDV_PERSONNEL", "RDV",      "alice", MON_9AM, 60, "", "", 0),
                new Event("REUNION",       "Réunion",  "alice", MON_9AM, 60, "Salle", "bob", 0),
                new Event("PERIODIQUE",    "Stand-up", "alice", MON_9AM, 0,  "", "", 7)
        );
        for (Event e : events) {
            assertFalse(e.description().isBlank());
        }
    }
}