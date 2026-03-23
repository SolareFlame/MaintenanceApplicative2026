package calendar;

import calendar.event.Evenement;
import calendar.valueobject.DateEvenement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    public List<Evenement> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    public void ajouter(Evenement e) {
        events.add(e);
    }

    public List<Evenement> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        List<Evenement> result = new ArrayList<>();

        for (Evenement e : events) {
            if (e.estDansPeriode(debut, fin)) result.add(e);
        }

        return result;
    }

    public boolean conflit(Evenement e1, Evenement e2) {
        return e1.chevauche(e2);
    }

    public void afficherEvenements() {
        for (Evenement e : events) {
            System.out.println(e.description());
        }
    }
}