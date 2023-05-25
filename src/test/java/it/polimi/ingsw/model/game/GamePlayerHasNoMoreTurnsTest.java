package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamePlayerHasNoMoreTurnsTest implements GameTester {
    @Test
    @DisplayName("Verify the function of playerHasNoMoreTurns, positively")
    public void test_playerHasNoMoreTurns_positively() {
        Game game = new Game(GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.flagNoMoreTurnsForPlayer(PLAYER_A);
        game.flagNoMoreTurnsForPlayer(PLAYER_B);

        assertTrue(game.getSessionFor(PLAYER_A).noMoreTurns);
        assertTrue(game.getSessionFor(PLAYER_B).noMoreTurns);
    }

    @Test
    @DisplayName("Verify the function of playerHasNoMoreTurns, negatively #1")
    public void test_playerHasNoMoreTurns_negatively_1() {
        Game game = new Game(GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertFalse(game.getSessionFor(PLAYER_A).noMoreTurns);
        assertFalse(game.getSessionFor(PLAYER_B).noMoreTurns);
    }

}
