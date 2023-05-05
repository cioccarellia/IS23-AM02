package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.cell.Cell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_3_PLAYERS;
import static org.junit.jupiter.api.Assertions.*;


public class GameTest implements GameTester {


    @Test
    @DisplayName("Verify player adding positively")
    public void test_game_positively() {
        Game game = new Game(GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertEquals(2, game.getPlayerNumberMap().size());

        assertNotNull(game.getPlayerSession(PLAYER_A));
        assertNotNull(game.getPlayerSession(PLAYER_B));

        assertEquals(PLAYER_A, game.getPlayerSession(PLAYER_A).getUsername());
        assertEquals(PLAYER_B, game.getPlayerSession(PLAYER_B).getUsername());
    }


    @Test
    @DisplayName("verify starting player and current player initialization #3")
    public void test_onGameStarted_3_positively() {
        Game game = new Game(GAME_MODE_3_PLAYERS);

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

    @Test
    @DisplayName("verify the correct (re)fill of the board, positively #4")
    public void test_onGameStarted_4_positively() {
        Game game = new Game(GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(game.getGameMatrix()[i][j]);
            }
        }

        //verify if the board is full
        assertEquals(0, board.countEmptyCells(GAME_MODE_2_PLAYERS));
    }

}