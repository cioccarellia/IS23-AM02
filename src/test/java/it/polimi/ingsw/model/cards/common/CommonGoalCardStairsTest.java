package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer.STAIRS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardStairsTest implements ShelfMatrixTester {
    final CommonGoalCard stairsCGC = STAIRS;

    @Test
    @DisplayName("Verify STAIRS positively #1")
    public void test_f12_positive_1() {

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {TROPHY, null, null, null, null},
                {GAME, GAME, null, null, null},
                {TROPHY, GAME, TROPHY, null, null},
                {GAME, GAME, PLANT, GAME, null},
                {CAT, GAME, GAME, BOOK, FRAME}
        };

        boolean doesMatrixMatch = stairsCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify STAIRS positively #2")
    public void test_f12_positive_2() {

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, CAT},
                {null, null, null, GAME, PLANT},
                {null, null, TROPHY, GAME, GAME},
                {null, GAME, GAME, GAME, GAME},
                {FRAME, PLANT, GAME, TROPHY, CAT}
        };

        boolean doesMatrixMatch = stairsCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify STAIRS negatively #1")
    public void test_f12_negative_1() {

        Tile[][] tileMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {GAME, GAME, null, null, null},
                {TROPHY, GAME, TROPHY, null, null},
                {GAME, GAME, PLANT, GAME, null},
                {CAT, GAME, GAME, BOOK, null}
        };

        boolean doesMatrixMatch = stairsCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify STAIRS negatively #2")
    public void test_f12_negative_2() {

        Tile[][] tileMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, GAME, PLANT},
                {null, null, TROPHY, GAME, GAME},
                {null, GAME, GAME, GAME, GAME},
                {null, PLANT, GAME, TROPHY, CAT}
        };

        boolean doesMatrixMatch = stairsCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for STAIRS: little stair on the left #1")
    public void test_f12_edge_1() {

        Tile[][] tileMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {GAME, null, null, null, null},
                {TROPHY, GAME, null, null, null},
                {GAME, GAME, PLANT, null, null},
                {CAT, GAME, GAME, BOOK, null}
        };

        boolean doesMatrixMatch = stairsCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for STAIRS positively: little stair on the right #2")
    public void test_f12_edge_2() {

        Tile[][] tileMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, PLANT},
                {null, null, null, GAME, GAME},
                {null, null, GAME, GAME, GAME},
                {null, PLANT, GAME, TROPHY, CAT}
        };

        boolean doesMatrixMatch = stairsCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify STAIRS negatively: null matrix")
    public void test_f12_edge_3() {
        boolean doesMatrixMatch = stairsCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

}
