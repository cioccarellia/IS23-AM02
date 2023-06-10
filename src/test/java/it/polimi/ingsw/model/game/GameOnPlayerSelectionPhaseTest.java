package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_4_PLAYERS;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.INSERTING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameOnPlayerSelectionPhaseTest implements GameTester {
    @Test
    @DisplayName("verify the function onPlayerSelectionPhase, positively #1")
    public void test_onPlayerSelectionPhase_1_positively() {
        GameModel game = new GameModel(GAME_MODE_4_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);
        game.addPlayer(PLAYER_D);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(8, 5);
        Coordinate c2 = new Coordinate(8, 4);

        Set<Coordinate> selection = Set.of(c1, c2);
        if (game.isSelectionValid(selection)) {
            List<Tile> tiles = List.of(
                    game.getGameMatrix()[c1.x()][c1.y()],
                    game.getGameMatrix()[c2.x()][c2.y()]
            );

            game.onPlayerSelectionPhase(selection);

            assertTrue(game.getCurrentPlayerSession().getPlayerTileSelection().selectionEquals(tiles));
            assertEquals(INSERTING, game.getCurrentPlayerSession().getPlayerCurrentGamePhase());
        }
    }

    @Test
    @DisplayName("Checks that calling onPlayerSelectionPhase(coordinates) actually saves the selection on" +
            "the user session upon successful execution")
    public void test_onPlayerSelectionPhase_assertSelectionSavesCoordinatesInUserSession() {
        GameModel game = new GameModel(GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(5, 1);
        Coordinate c2 = new Coordinate(5, 2);

        Set<Coordinate> selection = Set.of(c1, c2);

        if (game.isSelectionValid(selection)) {
            List<Tile> tiles = List.of(
                    game.getGameMatrix()[c1.x()][c1.y()],
                    game.getGameMatrix()[c2.x()][c2.y()]
            );

            game.onPlayerSelectionPhase(selection);

            assertTrue(game.getCurrentPlayerSession().getPlayerTileSelection().selectionEquals(tiles));
        }
    }
}
