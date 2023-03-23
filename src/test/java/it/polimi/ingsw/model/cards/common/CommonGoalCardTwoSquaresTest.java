package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardTwoSquaresTest implements ShelfMatrixTester {

    CommonGoalCard twoSquaresCGC = CommonGoalCardFunctionContainer.TWO_SQUARES;

    @Test
    @DisplayName("Verify TWO_SQUARES positively #1")
    public void test_f7_positive_1() {
        Tile[][] testPositiveMatrix = {
                {CAT, CAT, null, null, CAT},
                {CAT, CAT, null, null, GAME},
                {GAME, CAT, null, null, PLANT},
                {PLANT, CAT, null, null, TROPHY},
                {FRAME, CAT, null, FRAME, FRAME},
                {BOOK, CAT, null, FRAME, FRAME}
        };

        boolean doesMatrixMatch = twoSquaresCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES positively #2")
    public void test_f7_positive_2() {
        Tile[][] testPositiveMatrix = {
                {CAT, CAT, null, null, CAT},
                {CAT, CAT, null, null, GAME},
                {GAME, CAT, null, TROPHY, TROPHY},
                {PLANT, CAT, null, TROPHY, TROPHY},
                {FRAME, CAT, null, TROPHY, FRAME},
                {BOOK, CAT, null, FRAME, FRAME}
        };

        boolean doesMatrixMatch = twoSquaresCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES negatively #1")
    public void test_f7_negative_1() {
        Tile[][] testNegativeMatrix = {
                {CAT, CAT, null, null, CAT},
                {CAT, GAME, null, null, GAME},
                {GAME, CAT, null, TROPHY, TROPHY},
                {PLANT, CAT, null, TROPHY, TROPHY},
                {FRAME, CAT, TROPHY, TROPHY, FRAME},
                {BOOK, CAT, CAT, FRAME, FRAME}
        };

        boolean doesMatrixMatch = twoSquaresCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES negatively #2")
    public void test_f7_negative_2() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {CAT, TROPHY, PLANT, TROPHY, GAME},
                {PLANT, PLANT, PLANT, PLANT, PLANT},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {CAT, TROPHY, PLANT, TROPHY, GAME}
        };

        boolean doesMatrixMatch = twoSquaresCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES negatively #3")
    public void test_f7_negative_3() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {CAT, CAT, CAT, TROPHY, GAME},
                {CAT, CAT, CAT, TROPHY, GAME},
                {PLANT, CAT, CAT, PLANT, PLANT},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {CAT, TROPHY, PLANT, TROPHY, GAME}
        };

        boolean doesMatrixMatch = twoSquaresCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES negatively: null matrix")
    public void test_f2_negative_null_matrix() {
        boolean doesMatrixMatch = twoSquaresCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }
}