package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class GameExceptionTests {


    @Test
    @DisplayName("Tests that starting a game without enough players throws an exception")
    public void test_onGameStarted_throwsException_NoEnoughPlayers() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        try {
            game.onGameStarted();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }


}
