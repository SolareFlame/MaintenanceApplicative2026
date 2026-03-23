package calendar.event;

import java.time.LocalDateTime;

public abstract class Evenement {
    protected String title;
    protected String proprietaire;

    protected LocalDateTime dateDebut;
    protected int dureeMinutes;

    protected Evenement(String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes) {
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

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public boolean estDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return !dateDebut.isBefore(debut) && !dateDebut.isAfter(fin);
    }

    public boolean chevauche(Evenement autre) {
        LocalDateTime dateFin = dateDebut.plusMinutes(dureeMinutes);
        LocalDateTime autreDateFin = autre.getDateDebut().plusMinutes(autre.getDureeMinutes());

        return dateDebut.isBefore(autreDateFin) && dateFin.isAfter(autre.getDateDebut());
    }

    public abstract String description();
}