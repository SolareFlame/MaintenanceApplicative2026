package calendar.event;

import calendar.valueobject.DateEvenement;
import calendar.valueobject.TitreEvenement;

import java.time.LocalDateTime;
import java.util.List;

public class Reunion extends Evenement {
    private String lieu;
    private List<String> participants;

    public Reunion(TitreEvenement title, String proprietaire, DateEvenement dateDebut, int dureeMinutes, String lieu, List<String> participants) {
        super(title, proprietaire, dateDebut, dureeMinutes);

        this.lieu = lieu;
        this.participants = participants;
    }

    public String getLieu() {
        return lieu;
    }

    public List<String> getParticipants() {
        return participants;
    }

    @Override
    public String description() {
        return "Réunion : " + title + " à " + lieu + " avec " + String.join(", ", participants);
    }
}