package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.game.GameMode.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardCountEmptyCellsTest {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #1 positive case")
    public void test_countEmptyCells_method_positively_1() {
        Board board = new Board();
        assertEquals(45, board.countEmptyCells(GAME_MODE_4_PLAYERS));
    }

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #2 positive case")
    public void test_countEmptyCells_method_positively_2() {
        Board board = new Board();
        assertEquals(38, board.countEmptyCells(GAME_MODE_3_PLAYERS));
    }

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #3 positive case")
    public void test_countEmptyCells_method_positively_3() {
        Board board = new Board();
        assertEquals(30, board.countEmptyCells(GAME_MODE_2_PLAYERS));
    }
}
