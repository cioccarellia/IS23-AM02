package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.INSERTING;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameOnPlayerSelectionPhaseTest implements GameTester{
    @Test
    @DisplayName("verify the function onPlayerSelectionPhase, positively #1")
    public void test_onPlayerSelectionPhase_1_positively() {
        Game game = new Game(GameMode.GAME_MODE_4_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);
        game.addPlayer(PLAYER_D);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(8, 5);
        Coordinate coords2 = new Coordinate(8, 4);

        Set<Coordinate> selection = Set.of(coords1, coords2);

        game.onPlayerSelectionPhase(selection);

        List<Tile> testingTiles = List.of(game.getGameMatrix()[coords1.x()][coords1.y()],
                game.getGameMatrix()[coords2.x()][coords2.y()]);

        assertTrue(game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(testingTiles));
        assertEquals(INSERTING, game.getCurrentPlayer().getPlayerCurrentGamePhase());

    }

    @Test
    @DisplayName("Checks that calling onPlayerSelectionPhase(coordinates) actually saves the selection on" +
            "the user session upon successful execution")
    public void test_onPlayerSelectionPhase_assertSelectionSavesCoordinatesinUserSession() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(5, 1);
        Coordinate coords2 = new Coordinate(5, 2);

        Set<Coordinate> selection = Set.of(coords1, coords2);


        game.onPlayerSelectionPhase(selection);

        List<Tile> testingTiles = List.of(
                game.getGameMatrix()[coords1.x()][coords1.y()],
                game.getGameMatrix()[coords2.x()][coords2.y()]
        );

        assertTrue(game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(testingTiles));

    }

}
