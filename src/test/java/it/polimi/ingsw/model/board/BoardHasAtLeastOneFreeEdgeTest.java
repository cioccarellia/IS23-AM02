package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardHasAtLeastOneFreeEdgeTest {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    @Test
    @DisplayName("Tests the correct functionality of hasAtLeastOneFreeEdg, positive case")
    public void test_hasAtLeastOneFreeEdge_method_positively() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(3, 5);
        board.removeTileAt(c);
        assertTrue(board.hasAtLeastOneFreeEdge(c));
    }

    @Test
    @DisplayName("Tests the correct functionality of hasAtLeastOneFreeEdg, negative case")
    public void test_hasAtLeastOneFreeEdge_method_negatively() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(4, 4);
        board.removeTileAt(c);
        assertFalse(board.hasAtLeastOneFreeEdge(c));
    }

    @Test
    @DisplayName("Tests the correct functionality of hasAtLeastOneFreeEdg, #1 edge case")
    public void test_hasAtLeastOneFreeEdge_method_edge_case_1() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(0, 0);
        board.removeTileAt(c);
        assertTrue(board.hasAtLeastOneFreeEdge(c));
    }

    @Test
    @DisplayName("Tests the correct functionality of hasAtLeastOneFreeEdg, #2 edge case")
    public void test_hasAtLeastOneFreeEdge_method_edge_case_2() {
        Board board = new Board();
        Cell[][] cellMatrix = board.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cellMatrix[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate c = new Coordinate(5, 5);
        board.removeTileAt(c);
        assertTrue(board.hasAtLeastOneFreeEdge(c));
    }
}
