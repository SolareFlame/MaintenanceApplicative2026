package trivia.game;

public class Player {

    private final String name;

    private int place;
    private int purse;

    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;

        this.place = 1;
        this.purse = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    public void move(int steps) {
        this.place = (place + steps - 1) % 12 + 1;
        System.out.println(this + "'s new location is " + place);
    }

    public void reward(int amount) {
        this.purse += amount;
        System.out.println(this + " now has " + purse + " Gold Coins.");
    }

    public int getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }


    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void goToPenaltyBox() {
        this.inPenaltyBox = true;
        System.out.println(this + " was sent to the penalty box");
    }

    public void outOfPenaltyBox() {
        this.inPenaltyBox = false;
    }
}
