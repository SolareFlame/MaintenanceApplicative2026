package trivia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NewGameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.addPlayer("Ryan");
        game.addPlayer("Navier Xiel");
    }

    @Test
    void shouldAddPlayer() {
        Game g = new Game();
        g.addPlayer("Ryan");
        assertEquals(1, g.players.size());
    }

    @Test
    void shouldAddMultiplePlayers() {
        assertEquals(2, game.players.size());
    }

    @Test
    void shouldMovePlayerForward() {
        game.roll(3);
        assertEquals(4, game.players.get(0).getPlace());
    }

    @Test
    void shouldWrapAroundBoard() {
        game.roll(12);
        assertEquals(1, game.players.get(0).getPlace());
    }

    @Test
    void shouldWrapAroundBoardPartial() {
        game.roll(11);
        assertEquals(12, game.players.get(0).getPlace());
    }

    @Test
    void shouldBePopOnPlace1() {
        game.roll(0);
        assertEquals("Pop", game.currentCategory().toString());
    }

    @Test
    void shouldBeScienceOnPlace2() {
        game.roll(1);
        assertEquals("Science", game.currentCategory().toString());
    }

    @Test
    void shouldBeSportsOnPlace3() {
        game.roll(2);
        assertEquals("Sports", game.currentCategory().toString());
    }

    @Test
    void shouldBeRockOnPlace4() {
        game.roll(3);
        assertEquals("Rock", game.currentCategory().toString());
    }

    @Test
    void shouldBeGeoOnPlace5() {
        game.roll(4);
        assertEquals("Geographie", game.currentCategory().toString());
    }

    @Test
    void shouldSwitchToNextPlayerAfterCorrectAnswer() {
        game.roll(1);
        game.handleCorrectAnswer();
        assertEquals(1, game.currentPlayerIndex);
    }

    @Test
    void shouldSwitchToNextPlayerAfterWrongAnswer() {
        game.roll(1);
        game.wrongAnswer();
        assertEquals(1, game.currentPlayerIndex);
    }

    @Test
    void shouldCycleBackToFirstPlayer() {
        game.roll(1);
        game.handleCorrectAnswer(); //RK
        game.roll(1);
        game.handleCorrectAnswer(); //NX
        assertEquals(0, game.currentPlayerIndex);
    }

    @Test
    void shouldSendPlayerToPenaltyBoxOnWrongAnswer() {
        game.roll(1);
        game.wrongAnswer();
        assertTrue(game.players.get(0).isInPenaltyBox());
    }

    @Test
    void shouldGetOutOfPenaltyBoxOnOddRoll() {
        game.roll(1);
        game.wrongAnswer(); // penalty for RK
        game.roll(1);
        game.handleCorrectAnswer();
        game.roll(3); // roll 3 = exit penalty
        assertTrue(game.isGettingOutOfPenaltyBox);
    }

    @Test
    void shouldStayInPenaltyBoxOnEvenRoll() {
        game.roll(1);
        game.wrongAnswer(); // penalty for RK
        game.roll(1);
        game.handleCorrectAnswer();
        game.roll(2); // roll 2 = still penalty
        assertFalse(game.isGettingOutOfPenaltyBox);
    }

    @Test
    void shouldNotEarnCoinWhenStuckInPenaltyBox() {
        game.roll(1);
        game.wrongAnswer(); // RK en penalty box
        game.roll(1);
        game.handleCorrectAnswer();
        game.roll(2);  // roll 2 = still penalty
        game.handleCorrectAnswer();
        assertEquals(0, game.players.get(0).getPurse());
    }

    @Test
    void shouldEarnCoinOnCorrectAnswer() {
        game.roll(1);
        game.handleCorrectAnswer();
        assertEquals(1, game.players.get(0).getPurse());
    }

    @Test
    void shouldNotEarnCoinOnWrongAnswer() {
        game.roll(1);
        game.wrongAnswer();
        assertEquals(0, game.players.get(0).getPurse());
    }

    @Test
    void shouldNotWinBefore6Coins() {
        for (int i = 0; i < 5; i++) {
            game.roll(1);
            boolean result = game.handleCorrectAnswer();
            assertTrue(result);

            game.roll(1);
            game.handleCorrectAnswer();
        }
    }

    @Test
    void shouldWinWith6Coins() {
        for (int i = 0; i < 5; i++) {
            game.roll(1);
            game.handleCorrectAnswer();
            game.roll(1);
            game.handleCorrectAnswer();
        }
        game.roll(1);
        boolean result = game.handleCorrectAnswer();
        assertFalse(result);
    }


    @Test
    void shouldNotAddPlayerAfterGameStarted() {
        game.roll(1);
        boolean result = game.addPlayer("AA");
        assertFalse(result);
        assertEquals(2, game.players.size());
    }

    @Test
    void shouldAddPlayerBeforeGameStarted() {
        Game g = new Game();
        boolean result = g.addPlayer("AA");
        assertTrue(result);
        assertEquals(1, g.players.size());
    }

    @Test
    void shouldNotAddPlayerAfterWrongAnswer() {
        game.roll(1);
        game.wrongAnswer();
        boolean result = game.addPlayer("BB");
        assertFalse(result);
        assertEquals(2, game.players.size());
    }

    @Test
    void shouldNotAddPlayerAfterCorrectAnswer() {
        game.roll(1);
        game.handleCorrectAnswer();
        boolean result = game.addPlayer("BB");
        assertFalse(result);
        assertEquals(2, game.players.size());
    }

    @Test
    void shouldNotRollWithOnePlayer() {
        Game g = new Game();
        g.addPlayer("AAAAAA");
        assertThrows(Exception.class, () -> g.roll(1));
    }

    @Test
    void shouldNotRollWithNoPlayer() {
        Game g = new Game();
        assertThrows(Exception.class, () -> g.roll(1));
    }

    @Test
    void shouldRollWithTwoPlayers() {
        Game g = new Game();
        g.addPlayer("AAAAAA");
        g.addPlayer("BBBBBB");
        assertDoesNotThrow(() -> g.roll(1));
    }
}