package trivia.game;

public class Question {
    private final String label;
    private final Category category;

    public Question(String label, Category category) {
        this.label = label;
        this.category = category;
    }

    @Override
    public String toString() {
        return label;
    }

    public Category getCategory() {
        return category;
    }
}
