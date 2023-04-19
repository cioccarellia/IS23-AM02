package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardDispositionTest {

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

}
