package calendar;

import java.time.LocalDateTime;

public class Event {
    private String type;
    private String title;
    private String proprietaire;
    private LocalDateTime dateDebut;
    private int dureeMinutes;
    private String lieu;
    private String participants;
    private int frequenceJours;

    public Event(String type, String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                 String lieu, String participants, int frequenceJours) {
        this.type = type;
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
        this.lieu = lieu;
        this.participants = participants;
        this.frequenceJours = frequenceJours;
    }

    public String getType()             { return type; }
    public String getTitle()            { return title; }
    public String getProprietaire()     { return proprietaire; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public int getDureeMinutes()        { return dureeMinutes; }
    public String getLieu()             { return lieu; }
    public String getParticipants()     { return participants; }
    public int getFrequenceJours()      { return frequenceJours; }

    public String description() {
        String desc = "";
        if (type.equals("RDV_PERSONNEL")) {
            desc = "RDV : " + title + " à " + dateDebut.toString();
        } else if (type.equals("REUNION")) {
            desc = "Réunion : " + title + " à " + lieu + " avec " + participants;
        } else if (type.equals("PERIODIQUE")) {
            desc = "Événement périodique : " + title + " tous les " + frequenceJours + " jours";
        }
        return desc;
    }
}