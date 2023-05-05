package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game.GameMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static it.polimi.ingsw.model.board.Tile.CAT;
import static it.polimi.ingsw.model.game.GameMode.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryLocalVariable")
public class BoardExceptionTests {

    @Test
    @DisplayName("Verify the fill method throws unexpected value game mode exception")
    public void test_fill_method_exception() {
        Board board = new Board();
        GameMode mode = GAME_MODE_2_PLAYERS;

        List<Tile> newElements = Collections.nCopies(30, CAT);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> board.fill(newElements, mode));

        String expectedMessage = "Impossible to fit the designated elements inside the board: not enough space";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Verify the fill method throws unexpected value game mode exception #2")
    public void test_fill_method_exception_2() {
        Board board = new Board();
        GameMode mode = GAME_MODE_3_PLAYERS;

        List<Tile> newElements = Collections.nCopies(38, CAT);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> board.fill(newElements, mode));

        String expectedMessage = "Impossible to fit the designated elements inside the board: not enough space";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Verify the fill method throws unexpected value game mode exception #3")
    public void test_fill_method_exception_3() {
        Board board = new Board();
        GameMode mode = GAME_MODE_4_PLAYERS;

        List<Tile> newElements = Collections.nCopies(46, CAT);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> board.fill(newElements, mode));

        String expectedMessage = "Impossible to fit the designated elements inside the board: not enough space";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Verify the fill method does not throw unexpected value game mode exception")
    public void test_fill_method_doesnt_throw_exception() {
        Board board = new Board();
        GameMode mode = GAME_MODE_2_PLAYERS;

        List<Tile> newElements = Collections.nCopies(29, CAT);

        assertDoesNotThrow(() -> board.fill(newElements, mode));

    }

}
