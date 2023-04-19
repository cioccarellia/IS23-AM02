package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardRemoveTileAtTest {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    @Test
    @DisplayName("Tests the correct functionality of removeTileAt method, positive case")
    //FIXME understand if returning optional instead of Tile could be better
    public void test_removeTileAt_method_positively() {
        Board board = new Board();
        Cell[][] testPositiveCell = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testPositiveCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(1, 5);
        board.removeTileAt(coordsTest);
        assertEquals(Optional.empty(), board.getTileAt(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of removeTileAt method, edge case")
    //FIXME understand if returning optional instead of Tile could be better
    public void test_removeTileAt_method_edge_case() {
        Board board = new Board();
        Cell[][] cell = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(0, 0);
        board.removeTileAt(coordsTest);
        assertEquals(Optional.empty(), board.getTileAt(coordsTest));
    }
}
