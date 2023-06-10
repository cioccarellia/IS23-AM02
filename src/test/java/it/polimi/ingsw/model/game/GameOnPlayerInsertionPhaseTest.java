package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.IDLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameOnPlayerInsertionPhaseTest implements GameTester {


    @Test
    @DisplayName("verify the function onPlayerInsertionPhase, positively")
    public void test_onPlayerInsertionPhase_positively() {
        GameModel game = new GameModel(GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(1, 3);
        Coordinate c2 = new Coordinate(1, 4);

        Set<Coordinate> selection = Set.of(c1, c2);

        if (game.isSelectionValid(selection)) {
            Tile[][] matrix = game.getGameMatrix();

            List<Tile> tiles = List.of(matrix[c1.x()][c1.y()], matrix[c2.x()][c2.y()]);

            game.onPlayerSelectionPhase(selection);

            game.onPlayerInsertionPhase(1, tiles);

            assertEquals(matrix[c1.x()][c1.y()], tiles.get(0));
            assertEquals(matrix[c2.x()][c2.y()], tiles.get(1));

            assertEquals(IDLE, game.getCurrentPlayerSession().getPlayerCurrentGamePhase());
        }
    }
}
