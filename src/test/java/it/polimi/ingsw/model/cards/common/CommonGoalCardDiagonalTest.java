package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardDiagonalTest implements ShelfMatrixTester {

    CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

    @Test
    @DisplayName("Verify DIAGONAL positively: upper diagonal")
    public void test_f2_positive_1() {
        Tile[][] testPositiveMatrix = {
                {PLANT, null, null, null, null},
                {CAT, PLANT, null, null, null},
                {CAT, BOOK, PLANT, null, null},
                {CAT, PLANT, BOOK, PLANT, null},
                {CAT, FRAME, CAT, FRAME, PLANT},
                {BOOK, FRAME, TROPHY, TROPHY, FRAME}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL positively: lower diagonal")
    public void test_f2_positive_2() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {CAT, null, null, null, null},
                {CAT, CAT, null, null, null},
                {PLANT, FRAME, CAT, null, null},
                {PLANT, PLANT, TROPHY, CAT, null},
                {PLANT, PLANT, PLANT, PLANT, CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }
    @Test
    @DisplayName("Verify DIAGONAL positively: lower diagonal")
    public void test_f2_positive_3() {
        Tile[][] testPositiveMatrix = {
                {PLANT, null, null, null, null},
                {CAT, PLANT, null, null, null},
                {CAT, CAT, PLANT, null, null},
                {PLANT, FRAME, CAT, PLANT, null},
                {PLANT, PLANT, TROPHY, CAT, PLANT},
                {PLANT, PLANT, PLANT, PLANT, CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: almost diagonal")
    public void test_f2_negative_1() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {CAT, null, null, null, null},
                {FRAME, CAT, null, null, null},
                {BOOK, PLANT, null, null, null},
                {PLANT, CAT, FRAME, CAT, null},
                {TROPHY, TROPHY, TROPHY, TROPHY, CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: almost diagonal")
    public void test_f2_negative_2() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {CAT, null, null, null, null},
                {FRAME, CAT, null, null, null},
                {BOOK, PLANT, FRAME, null, null},
                {PLANT, CAT, FRAME, CAT, null},
                {TROPHY, TROPHY, TROPHY, TROPHY, CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: null matrix")
    public void test_f2_negative_3() {
        boolean doesMatrixMatch = diagonalCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }
}