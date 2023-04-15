package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {

    @Test
    @DisplayName("Verify player adding positively")
    public void test_game_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertEquals(2, game.getPlayerNumberMap().size());

        assertNotNull(game.getPlayerSession(PLAYER_A));
        assertNotNull(game.getPlayerSession(PLAYER_B));

        assertEquals(PLAYER_A, game.getPlayerSession(PLAYER_A).getUsername());
        assertEquals(PLAYER_B, game.getPlayerSession(PLAYER_B).getUsername());
    }

    @Test
    @DisplayName("Verify player adding negatively")
    public void test_game_negatively() {
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
    @DisplayName("Verify common goal card initialization")
    public void test_on_game_started_1_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        // list must be composed by 2 commongoalcards
        assertEquals(2, game.getCommonGoalCards().size());
    }
    @Test
    @DisplayName("verify starting player and current player initialization")
    public void test_on_game_started_2_positively() {
        Game game = new Game(GameMode.GAME_MODE_3_PLAYERS);

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB", PLAYER_C = "PlayerC";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);

        game.onGameStarted();

        assertEquals(3, game.getPlayerNumberMap().size());

        // verify random first-player extraction
        assertTrue(game.getPlayerNumberMap().containsKey(game.getStartingPlayerNumber()));
        assertTrue(game.getPlayerNumberMap().containsValue(game.getCurrentPlayer()));
    }

    /*@Test
    @DisplayName("verify the correct (re)fill of the board")
    public void test_on_game_started_3_positively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);
        game.onGameStarted();

        Board board= new Board();
        board = game.getGameMatrix();

        //verify if the board is full
        assertTrue(0, board.countEmptyCells(GameMode.GAME_MODE_2_PLAYERS));
    }*/

}
