package trivia;

import trivia.game.Player;
import trivia.game.Question;
import trivia.game.Category;

import java.util.ArrayList;

// REFACTOR ME
public class Game implements IGame {

   // CONFIGURATION DU JEU
   private static final int QUESTION_COUNT = 50;
   private static final int WINNING_GOLD_COINS = 6;


   ArrayList<Player> players = new ArrayList<>();
   ArrayList<Question> questions = new ArrayList<>();
   int currentPlayerIndex = 0;
   boolean isGettingOutOfPenaltyBox;

   boolean asStarted = false;

   public Game() {
      for (Category category : Category.values()) {
         for (int i = 0; i < QUESTION_COUNT; i++) {
            questions.add(new Question(category + " Question " + i, category));
         }
      }
   }

   public boolean addPlayer(String name) {
      if(asStarted) return false;

      //System.out.println("test");
      players.add(new Player(name));

      System.out.println(name + " was added");
      System.out.println("They are player number " + players.size());

      return true;
   }

   private Player currentPlayer() {
      return players.get(currentPlayerIndex);
   }

   private boolean canStart() {
      return players.size() >= 2;
   }

   public void roll(int roll) {
      if(!canStart()) throw new IllegalStateException("Need at least 2 players to start the game");
      if(!asStarted) asStarted = true;

      Player player = currentPlayer();

      System.out.println(player + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (player.isInPenaltyBox()) {
         handlePenaltyBoxRoll(player, roll);
      } else {
         moveAndAsk(player, roll);
      }
   }

   private void handlePenaltyBoxRoll(Player player, int roll) {
      if (roll % 2 != 0) {
         isGettingOutOfPenaltyBox = true;
         System.out.println(player + " is getting out of the penalty box");
         moveAndAsk(player, roll);
      } else {
         System.out.println(player + " is not getting out of the penalty box");
         isGettingOutOfPenaltyBox = false;
      }
   }

   private void moveAndAsk(Player player, int roll) {
      player.move(roll);
      System.out.println("The category is " + currentCategory());
      askQuestion();
   }

   private void askQuestion() {
      Category category = currentCategory();

      for (Question q : questions) {
         if (q.getCategory() == category) {
            questions.remove(q);
            System.out.println(q);
            return;
         }
      }

      throw new RuntimeException("No question found for category: " + category); // nouveau log
   }


   public Category currentCategory() {
      int cat = (currentPlayer().getPlace() - 1) % Category.values().length;
      return Category.values()[cat];
   }


   public boolean handleCorrectAnswer() {
      Player player = currentPlayer();

      //skip le player pénalisé
      if (player.isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
         nextPlayer();
         return true;
      }

      System.out.println("Answer was corrent!!!!");
      player.reward(1);

      if (isGettingOutOfPenaltyBox) {
         player.outOfPenaltyBox();
      }

      boolean winner = didPlayerWin();
      nextPlayer();

      return !winner; //patch au reste du coté pas propre
   }

   public boolean wrongAnswer() {
      Player player = currentPlayer();

      System.out.println("Question was incorrectly answered");
      player.goToPenaltyBox();

      nextPlayer();

      return true;
   }

   private void nextPlayer() {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
   }

   private boolean didPlayerWin() {
      return currentPlayer().getPurse() >= WINNING_GOLD_COINS;
   }
}
