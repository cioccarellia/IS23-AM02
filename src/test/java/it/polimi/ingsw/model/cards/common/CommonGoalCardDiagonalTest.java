package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.CAT;
import static it.polimi.ingsw.model.board.Tile.PLANT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardDiagonalTest implements ShelfMatrixTester {

    CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

    @Test
    @DisplayName("Verify DIAGONAL positively: upper diagonal")
    public void test_f2_positive_1() {
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
    @DisplayName("Verify DIAGONAL positively: lower diagonal")
    public void test_f2_positive_2() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {CAT, null, null, null, null},
                {null, CAT, null, null, null},
                {null, null, CAT, null, null},
                {null, null, null, CAT, null},
                {null, null, null, null, CAT}
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
                {null, CAT, null, null, null},
                {null, null, null, null, null},
                {null, null, null, CAT, null},
                {null, null, null, null, CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: null matrix")
    public void test_f2_negative_2() {
        boolean doesMatrixMatch = diagonalCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

}