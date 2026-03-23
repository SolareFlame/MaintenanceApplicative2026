package calendar.valueobject;

import java.time.LocalDateTime;
import java.util.Objects;

public final class DateDebut {

    private final LocalDateTime valeur;

    public DateDebut(int annee, int mois, int jour, int heure, int minute) {

        if (mois < 1 || mois > 12) throw new IllegalArgumentException("Mois invalide : " + mois);
        if (heure < 0 || heure > 23) throw new IllegalArgumentException("Heure invalide : " + heure);
        if (minute < 0 || minute > 59) throw new IllegalArgumentException("Minute invalide : " + minute);

        this.valeur = LocalDateTime.of(annee, mois, jour, heure, minute);
    }

    private DateDebut(LocalDateTime valeur) {
        this.valeur = valeur;
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

    public boolean isBefore(DateDebut autre) {
        return valeur.isBefore(autre.valeur);
    }

    public boolean isAfter(DateDebut autre) {
        return valeur.isAfter(autre.valeur);
    }

    public DateDebut plusMinutes(int minutes) {
        return new DateDebut(valeur.plusMinutes(minutes));
    }

    public DateDebut plusJours(int jours) {
        return new DateDebut(valeur.plusDays(jours));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateDebut d)) return false;
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