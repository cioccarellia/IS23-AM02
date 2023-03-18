package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalCardTest {

    Tile[][] nullMatrix = new Tile[6][5];

    @Test
    @DisplayName("Verify SIX_PAIRS positively #1")
    public void test_f1_positive() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testPositiveMatrix = {
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {null, null, null, null, null},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify SIX_PAIRS negatively #1")
    public void test_f1_negative() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testNegativeMatrix = {
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }


    @Test
    @DisplayName("edge case for SIX_PAIRS #1")
    public void test_f1_edge_1() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testNegativeMatrix = {
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS: null matrix")
    public void test_f1_edge_2() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        boolean doesMatrixMatch = sixPairsCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }


    @Test
    @DisplayName("Verify DIAGONAL positively: upper diagonal")
    public void test_f2_positive_1() {
        CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

        Tile[][] testPositiveMatrix = {
                {Tile.PLANT, null, null, null, null},
                {null, Tile.PLANT, null, null, null},
                {null, null, Tile.PLANT, null, null},
                {null, null, null, Tile.PLANT, null},
                {null, null, null, null, Tile.PLANT},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL positively: lower diagonal")
    public void test_f2_positive_2() {
        CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, null, null, null, null},
                {null, Tile.CAT, null, null, null},
                {null, null, Tile.CAT, null, null},
                {null, null, null, Tile.CAT, null},
                {null, null, null, null, Tile.CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: almost diagonal")
    public void test_f2_negative_1() {
        CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, null, null, null, null},
                {null, Tile.CAT, null, null, null},
                {null, null, null, null, null},
                {null, null, null, Tile.CAT, null},
                {null, null, null, null, Tile.CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: null matrix")
    public void test_f2_negative_2() {
        CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

        boolean doesMatrixMatch = diagonalCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }


    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR positively #1")
    public void test_f3_positive_1() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        Tile[][] testPositiveMatrix = {
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR positively #2")
    public void test_f3_positive_2() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        Tile[][] testPositiveMatrix = {
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},

        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }


    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR negatively #1")
    public void test_f3_negative_1() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        Tile[][] testNegativeMatrix = {
                {Tile.PLANT, null, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, null, null, null, null},
                {Tile.PLANT, null, null, null, null},
                {Tile.PLANT, null, Tile.PLANT, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR negatively #2")
    public void test_f3_negative_2() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        Tile[][] testNegativeMatrix = {
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, null, Tile.PLANT, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_GROUP_FOUR: 3 raws and 1 column: #1")
    public void test_f3_edge_1() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        Tile[][] testPositiveMatrix = {
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_GROUP_FOUR: null matrix #2")
    public void test_f3_edge_2() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        boolean doesMatrixMatch = fourGroupFour.matches(nullMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES positively #1")
    public void test_f4_positive_1() {
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT}
        };

        boolean doesMatrixMatch = four_max3diffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES positively #2")
    public void test_f4_positive_2() {
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT}
        };

        boolean doesMatrixMatch = four_max3diffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES negatively #1")
    public void test_f4_negative_1() {
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT}
        };

        boolean doesMatrixMatch = four_max3diffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES negatively #2")
    public void test_f4_negative_2() {
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME}
        };

        boolean doesMatrixMatch = four_max3diffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_CORNERS positively #1")
    public void test_f5_positive_1() {
        CommonGoalCard fourCorners = CommonGoalCardFunctionContainer.FOUR_CORNERS;

        Tile[][] testPositiveMatrix = {
                {Tile.CAT, null, null, null, Tile.CAT},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.CAT, null, null, null, Tile.CAT}
        };

        boolean doesMatrixMatch = fourCorners.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

}