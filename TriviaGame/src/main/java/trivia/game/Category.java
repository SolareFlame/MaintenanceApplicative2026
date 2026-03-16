package trivia.game;

public enum Category {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock"),
    GEO("Geographie");

    public final String label;

    Category(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}