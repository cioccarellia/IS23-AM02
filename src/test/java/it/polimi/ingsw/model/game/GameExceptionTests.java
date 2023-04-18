package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameExceptionTests {


    @Test
    @DisplayName("Tests that starting a game without enough players throws an exception")
    public void test_onGameStarted_throwsException_NoEnoughPlayers() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");

        try {
            game.onGameStarted();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }


    @Test
    @DisplayName("Tests that starting a game without enough players throws an exception")
    public void test_onGameStarted_throwsException_TooManyPlayers() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB", PLAYER_C = "PlayerC", PLAYER_D = "PlayerD";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertEquals(2, game.getPlayerNumberMap().size());

        assertEquals(PLAYER_A, game.getPlayerSession(PLAYER_A).getUsername());
        assertEquals(PLAYER_B, game.getPlayerSession(PLAYER_B).getUsername());

        try {
            game.getPlayerSession(PLAYER_C);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        try {
            game.getPlayerSession(PLAYER_D);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }


    @Test
    @DisplayName("Tests that players can not be added after the game has started")
    public void test_onGameStarted_throwsException_PlayersAddedAfterGameStarted() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        try {
            game.addPlayer("C");
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }


}
