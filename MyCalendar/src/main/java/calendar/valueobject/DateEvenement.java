package calendar.valueobject;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public final class DateEvenement {

    private final LocalDateTime valeur;

    public DateEvenement(int annee, int mois, int jour, int heure, int minute) {
        if (mois < 1 || mois > 12) throw new IllegalArgumentException("Mois invalide : " + mois);
        if (heure < 0 || heure > 23) throw new IllegalArgumentException("Heure invalide : " + heure);
        if (minute < 0 || minute > 59) throw new IllegalArgumentException("Minute invalide : " + minute);
        this.valeur = LocalDateTime.of(annee, mois, jour, heure, minute);
    }

    private DateEvenement(LocalDateTime valeur) {
        this.valeur = valeur;
    }

    public static DateEvenement depuis(LocalDateTime ldt) {
        return new DateEvenement(ldt);
    }

    public int annee() {
        return valeur.getYear();
    }

    public int mois() {
        return valeur.getMonthValue();
    }

    public int jour() {
        return valeur.getDayOfMonth();
    }

    public int heure() {
        return valeur.getHour();
    }

    public int minute() {
        return valeur.getMinute();
    }

    public boolean isBefore(DateEvenement autre) {
        return valeur.isBefore(autre.valeur);
    }

    public boolean isAfter(DateEvenement autre) {
        return valeur.isAfter(autre.valeur);
    }

    public DateEvenement plusMinutes(int minutes) {
        return new DateEvenement(valeur.plusMinutes(minutes));
    }

    public DateEvenement plusJours(int jours) {
        return new DateEvenement(valeur.plusDays(jours));
    }

    public DateEvenement plusMois(int mois) {
        return new DateEvenement(valeur.plusMonths(mois));
    }

    public DateEvenement plusAnnees(int annees) {
        return new DateEvenement(valeur.plusYears(annees));
    }

    public DateEvenement moinsMinutes(int minutes) {
        return new DateEvenement(valeur.minusMinutes(minutes));
    }

    public DateEvenement moinsJours(int jours) {
        return new DateEvenement(valeur.minusDays(jours));
    }

    public DateEvenement moinsMois(int mois) {
        return new DateEvenement(valeur.minusMonths(mois));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateEvenement d)) return false;
        return Objects.equals(valeur, d.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }

    @Override
    public String toString() {
        return valeur.toString();
    }
}