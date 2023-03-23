package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardSixPairsTest implements ShelfMatrixTester {

    CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;


    @Test
    @DisplayName("Verify SIX_PAIRS positively #1.5")
    public void test_f1_positive_2() {

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {GAME, null, TROPHY, null, PLANT},
                {GAME, null, TROPHY, null, PLANT},
                {PLANT, null, PLANT, null, TROPHY},
                {PLANT, null, PLANT, null, TROPHY}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }


    @Test
    @DisplayName("Verify SIX_PAIRS negatively #1")
    public void test_f1_negative_1() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {PLANT, PLANT, null, null, null},
                {PLANT, PLANT, null, null, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify SIX_PAIRS negatively #2")
    public void test_f1_negative_2() {

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {PLANT, null, PLANT, null, PLANT},
                {PLANT, null, PLANT, null, PLANT},
                {PLANT, null, PLANT, null, PLANT},
                {PLANT, null, PLANT, null, PLANT}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS #1")
    public void test_f1_edge_1() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {PLANT, PLANT, null, null, null},
                {PLANT, PLANT, null, null, null},
                {PLANT, PLANT, null, null, null},
                {PLANT, PLANT, null, null, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS #2")
    public void test_f1_edge_2() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {PLANT, PLANT, null, null, null},
                {PLANT, PLANT, null, null, null},
                {PLANT, PLANT, PLANT, BOOK, null},
                {PLANT, PLANT, PLANT, PLANT, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS: null matrix")
    public void test_f1_edge_3() {
        boolean doesMatrixMatch = sixPairsCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }


    @Test
    @DisplayName("edge case for SIX_PAIRS #3")
    public void test_f1_edge_4() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, PLANT, PLANT},
                {null, null, null, CAT, PLANT},
                {null, null, null, CAT, TROPHY},
                {null, null, null, TROPHY, TROPHY},
                {PLANT, null, null, TROPHY, PLANT},
                {PLANT, PLANT, null, PLANT, PLANT}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }
}