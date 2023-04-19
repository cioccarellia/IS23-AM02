package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameCommonGoalCardTest implements GameTester {

    @Test
    @DisplayName("Verify common goal card initialization #1")
    public void test_onGameStarted_1_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        // list must be composed of 2 commongoalcards
        assertEquals(2, game.getCommonGoalCards().size());
    }

    @Test
    @DisplayName("Verify personal goal card initialization #2")
    public void test_onGameStarted_2_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        // all the player must have a personal goal card
        assertNotNull(game.getPlayerSession(PLAYER_A).getPersonalGoalCard());
        assertNotNull(game.getPlayerSession(PLAYER_B).getPersonalGoalCard());

        // verificare che la personal goal card esista effettivamente
        //assertEquals(game.getPlayerSession(PLAYER_A).getPersonalGoalCard(), )
    }

}
