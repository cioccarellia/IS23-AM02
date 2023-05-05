package it.polimi.ingsw.model.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.game.GameMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardCountEmptyCellsTest {

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
        assertEquals(37, board.countEmptyCells(GAME_MODE_3_PLAYERS));
    }

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #3 positive case")
    public void test_countEmptyCells_method_positively_3() {
        Board board = new Board();
        assertEquals(29, board.countEmptyCells(GAME_MODE_2_PLAYERS));
    }
}
