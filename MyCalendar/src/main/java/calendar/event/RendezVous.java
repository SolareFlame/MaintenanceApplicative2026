package calendar.event;

import calendar.valueobject.DateEvenement;
import calendar.valueobject.TitreEvenement;

import java.time.LocalDateTime;

public class RendezVous extends Evenement {
    public RendezVous(TitreEvenement title, String proprietaire, DateEvenement dateDebut, int dureeMinutes) {
        super(title, proprietaire, dateDebut, dureeMinutes);
    }



    @Override
    public String description() {
        return "RDV : " + title + " à " + dateDebut.toString();
    }
}