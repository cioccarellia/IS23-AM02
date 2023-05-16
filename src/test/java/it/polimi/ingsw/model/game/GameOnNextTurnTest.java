package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.IDLE;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.SELECTING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameOnNextTurnTest implements GameTester {

    @Test
    @DisplayName("Verify the function of onNextTurn, positively ")
    public void test_onNextTurn_positively() {
        Game testingGame = new Game(GAME_MODE_2_PLAYERS);
        testingGame.addPlayer("Player_A");
        testingGame.addPlayer("Player_B");

        testingGame.onNextTurn("Player_B");

        assertEquals(SELECTING, testingGame.getSessionFor("Player_B").getPlayerCurrentGamePhase());
    }

    @Test
    @DisplayName("Verify the function of onNextTurn, negatively ")
    public void test_onNextTurn_negatively() {
        Game testingGame = new Game(GAME_MODE_2_PLAYERS);
        testingGame.addPlayer("Player_A");
        testingGame.addPlayer("Player_B");

        testingGame.onNextTurn("Player_B");

        boolean isPlayerGamePhaseIdle = testingGame.getSessionFor("Player_B")
                .getPlayerCurrentGamePhase()
                .equals(IDLE);

        assertFalse(isPlayerGamePhaseIdle);
    }
}