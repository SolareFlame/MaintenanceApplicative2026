package calendar.event;

import java.time.LocalDateTime;

public class RendezVous extends Evenement {
    public RendezVous(String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes) {
        super(title, proprietaire, dateDebut, dureeMinutes);
    }



    @Override
    public String description() {
        return "RDV : " + title + " à " + dateDebut.toString();
    }
}