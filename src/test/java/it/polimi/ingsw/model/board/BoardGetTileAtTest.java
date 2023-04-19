package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static it.polimi.ingsw.model.board.Tile.GAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardGetTileAtTest {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    @Test
    @DisplayName("Tests the correct functionality of getTileAt method")
    //FIXME understand if returning optional instead of Tile could be better
    public void test_getTileAt_method() {
        Board board = new Board();
        Cell[][] testCell = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(1, 5);
        assertEquals(Optional.of(GAME), board.getTileAt(coordsTest));
    }
}
