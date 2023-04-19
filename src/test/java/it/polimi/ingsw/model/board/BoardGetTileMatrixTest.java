package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static it.polimi.ingsw.model.board.HardcodedBoardConstants.GLOBAL_BOARD_MATRIX;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardGetTileMatrixTest {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

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
}
