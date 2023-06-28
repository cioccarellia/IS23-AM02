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
    public void test_removeTileAt_method_positively() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(1, 5);
        board.removeTileAt(c);
        assertEquals(Optional.empty(), board.getTileAt(c));
    }

    @Test
    @DisplayName("Tests the correct functionality of removeTileAt method, edge case")
    public void test_removeTileAt_method_edge_case() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(0, 0);
        board.removeTileAt(c);
        assertEquals(Optional.empty(), board.getTileAt(c));
    }
}
