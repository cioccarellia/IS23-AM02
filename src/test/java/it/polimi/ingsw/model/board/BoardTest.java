package it.polimi.ingsw.model.board;

import it.polimi.ingsw.costants.BoardConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    @DisplayName("Tests the correct board position")
    public void test_board_disposition() {
        Board board = new Board();

        for (int i = 0; i < BoardConstants.BOARD_DIMENSION; i++) {
            for (int j = 0; j < BoardConstants.BOARD_DIMENSION; j++) {
                Coordinates c = new Coordinates(i, j);
            }
        }
    }
}