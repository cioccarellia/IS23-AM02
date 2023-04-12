package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private static final int BOARD_DIMENSION = BoardConfiguration.getInstance().getDimension();


    @Test
    @DisplayName("Tests the hardcoded board pattern")
    public void test_board_disposition() {
        Board board = new Board();

        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
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
    @DisplayName("Tests the correct functionality of countFreeEdges method")
    public void test_countFreeEdges_method_positively(){
        Board positiveBoard = new Board();
        Cell[][] testCell = positiveBoard.getCellMatrix();

        for (int i = 0; i < BOARD_DIMENSION; i++){
            for (int j = 0; j < BOARD_DIMENSION; j++){
               testCell[i][j].setContent(HardcodedBoardConstants.getTile(i,j));
            }
        }

        Coordinate coordsTest = new Coordinate(1,5);
        assertEquals(3, positiveBoard.countFreeEdges(coordsTest));
    }
}
