package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardThreeMax3DifferentColumnsTest implements ShelfMatrixTester {

    CommonGoalCard threeMax3diffColumnsCGC = CommonGoalCardFunctionContainer.THREE_MAX3DIFF_COLUMNS;

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS positively #1")
    public void test_f9_positive_1() {

        Tile[][] testPositiveMatrix = {
                {CAT, CAT, null, null, CAT},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, PLANT},
                {PLANT, CAT, GAME, FRAME, CAT},
                {CAT, CAT, CAT, FRAME, GAME},
                {BOOK, CAT, TROPHY, FRAME, PLANT}
        };

        boolean doesMatrixMatch = threeMax3diffColumnsCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS positively #2")
    public void test_f9_positive_2() {
        Tile[][] testPositiveMatrix = {
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumnsCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS negatively #1")
    public void test_f9_negative_1() {
        Tile[][] testNegativeMatrix = {
                {CAT, CAT, PLANT, TROPHY, CAT},
                {CAT, GAME, FRAME, PLANT, GAME},
                {GAME, TROPHY, GAME, CAT, TROPHY},
                {PLANT, FRAME, GAME, GAME, TROPHY},
                {FRAME, CAT, TROPHY, TROPHY, FRAME},
                {BOOK, CAT, CAT, FRAME, FRAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumnsCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS negatively #2")
    public void test_f9_negative_2() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {CAT, GAME, null, PLANT, GAME},
                {GAME, CAT, GAME, TROPHY, TROPHY},
                {PLANT, CAT, GAME, TROPHY, TROPHY},
                {FRAME, CAT, TROPHY, TROPHY, FRAME},
                {BOOK, CAT, CAT, FRAME, FRAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumnsCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS negatively: null matrix")
    public void test_f9_negative_null() {
        boolean doesMatrixMatch = threeMax3diffColumnsCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS positively: null matrix")
    public void test_f9_negative_full() {
        boolean doesMatrixMatch = threeMax3diffColumnsCGC.matches(generateFullMatrixOf(PLANT));

        // assert that the matrix does not match
        assertTrue(doesMatrixMatch);
    }

}