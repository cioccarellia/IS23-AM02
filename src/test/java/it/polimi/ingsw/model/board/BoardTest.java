package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.game.extractors.TileExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static it.polimi.ingsw.model.board.HardcodedBoardConstants.GLOBAL_BOARD_MATRIX;
import static it.polimi.ingsw.model.board.Tile.GAME;
import static it.polimi.ingsw.model.game.GameMode.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private static final int dimension = BoardConfiguration.getInstance().getDimension();


    @Test
    @DisplayName("Tests the hardcoded board pattern")
    public void test_board_disposition() {
        Board board = new Board();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Coordinate c = new Coordinate(i, j);

                Cell boardTile = board.getCellAt(c);
                CellPattern hardcodedPattern = HardcodedBoardConstants.DFU_BOARD_MATRIX[i][j];

                if (boardTile.isDead()) {
                    assertNull(hardcodedPattern);
                } else {
                    assertSame(boardTile.getPattern(), hardcodedPattern);
                }
            }
        }
    }

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, positive case")
    public void test_countFreeEdges_method_positively() {
        Board positiveBoard = new Board();
        Cell[][] testPositiveCell = positiveBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testPositiveCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(1, 5);
        assertEquals(3, positiveBoard.countFreeEdges(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, negative case")
    public void test_countFreeEdges_method_negatively() {
        Board negativeBoard = new Board();
        Cell[][] testNegativeCell = negativeBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testNegativeCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(4, 4);
        assertEquals(0, negativeBoard.countFreeEdges(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of countFreeEdges method, edge case")
    public void test_countFreeEdges_method_edge_case() {
        Board TestingBoard = new Board();
        Cell[][] testingCell = TestingBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(0, 0);
        assertEquals(4, TestingBoard.countFreeEdges(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of getTileAt method")
    //FIXME understand if returning optional instead of Tile could be better
    public void test_getTileAt_method() {
        Board TestingBoard = new Board();
        Cell[][] testCell = TestingBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(1, 5);
        assertEquals(Optional.of(GAME), TestingBoard.getTileAt(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of removeTileAt method, positive case")
    //FIXME understand if returning optional instead of Tile could be better
    public void test_removeTileAt_method_positively() {
        Board positiveBoard = new Board();
        Cell[][] testPositiveCell = positiveBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testPositiveCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(1, 5);
        positiveBoard.removeTileAt(coordsTest);
        assertEquals(Optional.empty(), positiveBoard.getTileAt(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of removeTileAt method, edge case")
    //FIXME understand if returning optional instead of Tile could be better
    public void test_removeTileAt_method_edge_case() {
        Board testingBoard = new Board();
        Cell[][] testingCell = testingBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(0, 0);
        testingBoard.removeTileAt(coordsTest);
        assertEquals(Optional.empty(), testingBoard.getTileAt(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of getTileMatrix method")
    public void test_getTileMatrix_method() {
        Board positiveBoard = new Board();
        Cell[][] testingCell = positiveBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        boolean isEqual = Arrays.deepEquals(positiveBoard.getTileMatrix(), GLOBAL_BOARD_MATRIX);

        assertTrue(isEqual);
    }

    @Test
    @DisplayName("Tests the correct functionality of hasAtLeastOneFreeEdg, positive case")
    public void test_hasAtLeastOneFreeEdge_method_positively() {
        Board positiveBoard = new Board();
        Cell[][] testingCell = positiveBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(3, 5);
        positiveBoard.removeTileAt(coordsTest);
        assertTrue(positiveBoard.hasAtLeastOneFreeEdge(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of hasAtLeastOneFreeEdg, negative case")
    public void test_hasAtLeastOneFreeEdge_method_negatively() {
        Board negativeBoard = new Board();
        Cell[][] testingCell = negativeBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(4, 4);
        negativeBoard.removeTileAt(coordsTest);
        assertFalse(negativeBoard.hasAtLeastOneFreeEdge(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of hasAtLeastOneFreeEdg, #1 edge case")
    public void test_hasAtLeastOneFreeEdge_method_edge_case_1() {
        Board positiveBoard = new Board();
        Cell[][] testingCell = positiveBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(0, 0);
        positiveBoard.removeTileAt(coordsTest);
        assertTrue(positiveBoard.hasAtLeastOneFreeEdge(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of hasAtLeastOneFreeEdg, #2 edge case")
    public void test_hasAtLeastOneFreeEdge_method_edge_case_2() {
        Board positiveBoard = new Board();
        Cell[][] testingCell = positiveBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(HardcodedBoardConstants.getTile(i, j));
            }
        }

        Coordinate coordsTest = new Coordinate(5, 5);
        positiveBoard.removeTileAt(coordsTest);
        assertTrue(positiveBoard.hasAtLeastOneFreeEdge(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #1 positive case")
    public void test_countEmptyCells_method_positively_1() {
        Board positiveBoard = new Board();
        assertEquals(45, positiveBoard.countEmptyCells(GAME_MODE_4_PLAYERS));
    }

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #2 positive case")
    public void test_countEmptyCells_method_positively_2() {
        Board positiveBoard = new Board();
        assertEquals(38, positiveBoard.countEmptyCells(GAME_MODE_3_PLAYERS));
    }

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #3 positive case")
    public void test_countEmptyCells_method_positively_3() {
        Board positiveBoard = new Board();
        assertEquals(30, positiveBoard.countEmptyCells(GAME_MODE_2_PLAYERS));
    }

    @Test
    @DisplayName("Tests the correct functionality of fill method, #1 positively")
    public void test_fill_method_positively_1() {
        Board positiveBoard = new Board();
        TileExtractor testingElements = new TileExtractor();
        positiveBoard.fill(testingElements.extractAmount(positiveBoard.countEmptyCells(GAME_MODE_2_PLAYERS)), GAME_MODE_2_PLAYERS);
        assertEquals(0, positiveBoard.countEmptyCells(GAME_MODE_2_PLAYERS));
    }

    @Test
    @DisplayName("Tests the correct functionality of fill method, #2 positively")
    public void test_fill_method_positively_2() {
        Board positiveBoard = new Board();
        TileExtractor testingElements = new TileExtractor();
        positiveBoard.fill(testingElements.extractAmount(positiveBoard.countEmptyCells(GAME_MODE_2_PLAYERS)), GAME_MODE_2_PLAYERS);

        Coordinate coordsTest = new Coordinate(1, 3);
        assertEquals(2, positiveBoard.countFreeEdges(coordsTest));
        Coordinate coordsTest_1 = new Coordinate(0, 3);
        assertEquals(3, positiveBoard.countFreeEdges(coordsTest_1));
        Coordinate coordsTest_2 = new Coordinate(0, 4);
        assertEquals(3, positiveBoard.countFreeEdges(coordsTest_2));
        Coordinate coordsTest_3 = new Coordinate(5, 5);
        assertEquals(0, positiveBoard.countFreeEdges(coordsTest_3));
    }

    @Test
    @DisplayName("Tests the correct functionality of fill method, #3 positively")
    public void test_fill_method_positively_3() {
        Board positiveBoard = new Board();
        TileExtractor testingElements = new TileExtractor();
        positiveBoard.fill(testingElements.extractAmount(positiveBoard.countEmptyCells(GAME_MODE_2_PLAYERS)), GAME_MODE_2_PLAYERS);
        Coordinate coordsTest = new Coordinate(5, 5);
        positiveBoard.removeTileAt(coordsTest);
        assertEquals(Optional.empty(), positiveBoard.getTileAt(coordsTest));
    }

    @Test
    @DisplayName("Tests the correct functionality of fill method, #4 positively")
    public void test_fill_method_positively_4() {
        Board positiveBoard = new Board();
        TileExtractor testingElements = new TileExtractor();
        positiveBoard.fill(testingElements.extractAmount(positiveBoard.countEmptyCells(GAME_MODE_2_PLAYERS)), GAME_MODE_2_PLAYERS);
        Coordinate coordsTest = new Coordinate(5, 5);
        assertFalse(positiveBoard.hasAtLeastOneFreeEdge(coordsTest));
    }


}
