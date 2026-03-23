package calendar.event;

import calendar.valueobject.DateEvenement;

import java.time.LocalDateTime;

public class Periodique extends Evenement {
    private int frequenceJours;

    public Periodique(String title, String proprietaire, DateEvenement dateDebut, int frequenceJours) {
        super(title, proprietaire, dateDebut, 0);
        this.frequenceJours = frequenceJours;
    }

    public int getFrequenceJours() {
        return frequenceJours;
    }

    @Override
    public boolean estDansPeriode(DateEvenement debut, DateEvenement fin) {
        DateEvenement temp = dateDebut;

        while (temp.isBefore(debut)) {
            temp = temp.plusJours(frequenceJours);
        }
        return !temp.isAfter(fin);
    }

    @Override
    public String description() {
        return "Événement périodique : " + title + " tous les " + frequenceJours + " jours";
    }
}