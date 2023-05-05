package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_4_PLAYERS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameOnPlayerCheckingPhaseTest implements GameTester {

    /*
    @BeforeEach()
    void init() {
        new Game(GAME_MODE_4_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);
        game.addPlayer(PLAYER_D);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(1, 3);
        Coordinate c2 = new Coordinate(1, 4);

        Set<Coordinate> selection = Set.of(c1, c2);

        game.onPlayerSelectionPhase(selection);


        Tile[][] matrix = game.getGameMatrix();

        List<Tile> tiles = List.of(
                matrix[c1.x()][c1.y()],
                matrix[c2.x()][c2.y()]
        );

        game.onPlayerInsertionPhase(1, tiles);
    }

     */

    @Test
    @DisplayName("verify the function OnPlayerCheckingPhase, positively")
    public void test_OnPlayerCheckingPhase_positively() {
        Game game = new Game(GAME_MODE_4_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);
        game.addPlayer(PLAYER_D);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(1, 3);
        Coordinate c2 = new Coordinate(1, 4);

        Set<Coordinate> selection = Set.of(c1, c2);

        game.onPlayerSelectionPhase(selection);


        Tile[][] matrix = game.getGameMatrix();

        List<Tile> tiles = List.of(
                matrix[c1.x()][c1.y()],
                matrix[c2.x()][c2.y()]
        );

        game.onPlayerInsertionPhase(1, tiles);
        game.onPlayerCheckingPhase();

        assertFalse(game.getCurrentPlayer().getBookshelf().isFull());
        assertNull(game.getCurrentPlayer().getAcquiredTokens());

    }

}