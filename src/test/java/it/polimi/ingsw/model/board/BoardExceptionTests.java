package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game.GameMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.game.GameMode.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardExceptionTests {

    // map from game mode exception
    /*
    @Test
    @DisplayName("Verify the insert method throws unexpected value game mode exception")
    public void test_mapFromGameMode_exception() {
        //FIXME mapFromGameMode is private; also, we need to find a way to pass a not accepted mode, like 5 players
        Board board = new Board();
        GameMode mode = GAME_MODE_5_PLAYERS;


        Exception exception = assertThrows(IllegalStateException.class, () -> board.mapFromGameMode(mode));

        String expectedMessage = "Unexpected value: " + mode;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    */
    @Test
    @DisplayName("Verify the insert method throws unexpected value game mode exception")
    public void test_fill_method_exception() {
        Board board = new Board();
        GameMode mode = GAME_MODE_2_PLAYERS;

        List<Tile> newElements = Collections.nCopies(38, CAT);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> board.fill(newElements, mode));

        String expectedMessage = "Impossible to fit the designated elements inside the board: not enough space";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}
