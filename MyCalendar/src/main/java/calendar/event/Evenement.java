package calendar.event;

import calendar.valueobject.DateEvenement;

import java.time.LocalDateTime;

public abstract class Evenement {
    protected String title;
    protected String proprietaire;

    protected DateEvenement dateDebut;
    protected int dureeMinutes;

    protected Evenement(String title, String proprietaire, DateEvenement dateDebut, int dureeMinutes) {
        this.title = title;
        this.proprietaire = proprietaire;

        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
    }

    public String getTitle() {
        return title;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public DateEvenement getDateDebut() {
        return dateDebut;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public boolean estDansPeriode(DateEvenement debut, DateEvenement fin) {
        return !dateDebut.isBefore(debut) && !dateDebut.isAfter(fin);
    }

    public boolean chevauche(Evenement autre) {
        DateEvenement dateFin = dateDebut.plusMinutes(dureeMinutes);
        DateEvenement autreDateFin = autre.getDateDebut().plusMinutes(autre.getDureeMinutes());

        return dateDebut.isBefore(autreDateFin) && dateFin.isAfter(autre.getDateDebut());
    }

    public abstract String description();
}