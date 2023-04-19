package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardCountFreeEdgesTest {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, positive case")
    public void test_countFreeEdges_method_positively() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(1, 5);
        assertEquals(3, board.countFreeEdges(c));
    }

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, negative case")
    public void test_countFreeEdges_method_negatively() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(4, 4);
        assertEquals(0, board.countFreeEdges(c));
    }

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, edge case")
    public void test_countFreeEdges_method_edge_case() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(0, 0);
        assertEquals(4, board.countFreeEdges(c));
    }
}
