package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.CHECKING;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameOnPlayerInsertionPhaseTest implements GameTester{
    @Test
    @DisplayName("verify the function onPlayerInsertionPhase, positively")
    public void test_onPlayerInsertionPhase_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

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

        assertEquals(matrix[c1.x()][c1.y()], testingTilesList.get(0));
        assertEquals(matrix[c2.x()][c2.y()], testingTilesList.get(1));
        assertEquals(CHECKING, game.getCurrentPlayer().getPlayerCurrentGamePhase());
    }
}
