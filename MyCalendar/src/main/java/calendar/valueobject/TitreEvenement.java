package calendar.valueobject;

import java.util.Objects;

public record TitreEvenement(String valeur) {
    public TitreEvenement {
        if (valeur == null || valeur.isBlank())
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitreEvenement(String valeur1))) return false;
        return Objects.equals(valeur, valeur1);
    }

    @Override
    public String toString() {
        return valeur;
    }
}