package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalCardDiagonalTest implements ShelfMatrixTester {

    CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

    @Test
    @DisplayName("Verify DIAGONAL positively: upper diagonal, empty matrix")
    public void test_f2_positive_upperDiagonalEmptyMatrix() {
        Tile[][] testPositiveMatrix = {
                {PLANT, null, null, null, null},
                {null, PLANT, null, null, null},
                {null, null, PLANT, null, null},
                {null, null, null, PLANT, null},
                {null, null, null, null, PLANT},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL positively: upper diagonal, lower matrix")
    public void test_f2_positive_upperDiagonalLowerMatrix() {
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
    @DisplayName("Verify DIAGONAL positively: upper diagonal, upper matrix")
    public void test_f2_positive_upperDiagonalUpperMatrix() {
        Tile[][] testPositiveMatrix = {
                {PLANT, BOOK, BOOK, BOOK, BOOK},
                {null, PLANT, BOOK, BOOK, BOOK},
                {null, null, PLANT, BOOK, BOOK},
                {null, null, null, PLANT, BOOK},
                {null, null, null, null, PLANT},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL positively: lower diagonal, lower matrix")
    public void test_f2_positive_lowerDiagonalLowerMatrix() {
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
    @DisplayName("Verify DIAGONAL positively: lower diagonal, upper matrix")
    public void test_f2_positive_lowerDiagonalUpperMatrix() {
        Tile[][] testPositiveMatrix = {
                {PLANT, PLANT, PLANT, PLANT, PLANT},
                {CAT, PLANT, PLANT, PLANT, PLANT},
                {GAME, CAT, PLANT, PLANT, PLANT},
                {GAME, GAME, CAT, PLANT, PLANT},
                {GAME, GAME, GAME, CAT, PLANT},
                {GAME, GAME, GAME, GAME, CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL positively: lower diagonal")
    public void test_f2_positive_() {
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
        Tile[][] tileMatrix = {
                {null, null, null, null, null},
                {CAT, null, null, null, null},
                {FRAME, CAT, null, null, null},
                {BOOK, PLANT, null, null, null},
                {PLANT, CAT, FRAME, CAT, null},
                {TROPHY, TROPHY, TROPHY, TROPHY, CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: almost diagonal")
    public void test_f2_negative_2() {
        Tile[][] tileMatrix = {
                {null, null, null, null, null},
                {CAT, null, null, null, null},
                {FRAME, CAT, null, null, null},
                {BOOK, PLANT, FRAME, null, null},
                {PLANT, CAT, FRAME, CAT, null},
                {TROPHY, TROPHY, TROPHY, TROPHY, CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: null matrix")
    public void test_f2_negative_empty() {
        boolean doesMatrixMatch = diagonalCGC.matches(nullMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: full matrix")
    public void test_f2_negative_full() {
        boolean doesMatrixMatch = diagonalCGC.matches(generateFullMatrixOf(PLANT));

        assertTrue(doesMatrixMatch);
    }
}