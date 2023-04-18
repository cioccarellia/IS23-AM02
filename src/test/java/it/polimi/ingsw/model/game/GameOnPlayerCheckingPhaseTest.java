package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameOnPlayerCheckingPhaseTest implements GameTester {

    private Game game;

    @Test
    @BeforeEach()
    void init() {
        game = new Game(GameMode.GAME_MODE_4_PLAYERS);

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

        List<Tile> testingTilesList = List.of(
                matrix[c1.x()][c1.y()],
                matrix[c2.x()][c2.y()]
        );

        game.onPlayerInsertionPhase(1, testingTilesList);
    }

    @Test
    @DisplayName("verify the function OnPlayerCheckingPhase, positively")
    public void test_OnPlayerCheckingPhase_positively() {

        game.onPlayerCheckingPhase();

        assertFalse(game.getCurrentPlayer().getBookshelf().isFull());

        assertEquals(game.getCurrentPlayer().getAcquiredTokens(), null);
    }
}