package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
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

        assertEquals(2, game.getPlayersMap().size());

        assertTrue(game.getPlayer(PLAYER_A).isPresent());
        assertTrue(game.getPlayer(PLAYER_B).isPresent());

        assertEquals(PLAYER_A, game.getPlayer(PLAYER_A).get().getUsername());
        assertEquals(PLAYER_B, game.getPlayer(PLAYER_B).get().getUsername());
    }

    @Test
    @DisplayName("Verify player adding negatively")
    public void test_game_negatively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB", PLAYER_C = "PlayerC",  PLAYER_D = "PlayerD";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertEquals(2, game.getPlayersMap().size());

        assertFalse(game.getPlayer(PLAYER_D).isPresent());
        assertFalse(game.getPlayer(PLAYER_C).isPresent());

        assertEquals(PLAYER_A, game.getPlayer(PLAYER_A).get().getUsername());
        assertEquals(PLAYER_B, game.getPlayer(PLAYER_B).get().getUsername());
    }

    @Test
    @DisplayName("Verify common goal card initialization")
    public void test_on_game_started_1_positively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.onGameStarted();

        // list must be composed by 2 commongoalcard
        assertEquals(2, game.getCommonGoalCards().size());
    }

    @Test
    @DisplayName("Verify common goal card initialization")
    public void test_on_game_started_1_negatively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.onGameStarted();

        // list must be composed by 2 commongoalcard
        assertEquals(4, game.getCommonGoalCards().size());
    }

    @Test
    @DisplayName("verify starting player and current player initialization")
    public void test_on_game_started_2_positively()
    {
        Game game = new Game(GameMode.GAME_MODE_3_PLAYERS);
        game.onGameStarted();

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB", PLAYER_C = "PlayerC";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);

        assertEquals(3, game.getPlayersMap().size());

        // verify random first-player extraction
        assertTrue(game.getPlayersMap().containsKey(game.getStartingPlayerNumber()));
        assertTrue(game.getPlayersMap().containsValue(game.getCurrentPlayer()));
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
