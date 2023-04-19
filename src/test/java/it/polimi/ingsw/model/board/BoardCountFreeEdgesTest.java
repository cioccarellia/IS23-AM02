package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardCountFreeEdgesTest {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, positive case")
    public void test_countFreeEdges_method_positively() {
        Board board = new Board();
        Cell[][] testPositiveCell = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testPositiveCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(1, 5);
        assertEquals(3, board.countFreeEdges(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, negative case")
    public void test_countFreeEdges_method_negatively() {
        Board board = new Board();
        Cell[][] testNegativeCell = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testNegativeCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(4, 4);
        assertEquals(0, board.countFreeEdges(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, edge case")
    public void test_countFreeEdges_method_edge_case() {
        Board board = new Board();
        Cell[][] cell = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(0, 0);
        assertEquals(4, board.countFreeEdges(coordsTest));
    }
}
