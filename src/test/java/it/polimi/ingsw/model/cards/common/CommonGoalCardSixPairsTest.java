package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.costants.BookShelfConstants.COLUMNS;
import static it.polimi.ingsw.costants.BookShelfConstants.ROWS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardSixPairsTest {

    Tile[][] nullMatrix = new Tile[ROWS][COLUMNS];

    @Test
    @DisplayName("Verify SIX_PAIRS positively #1")
    public void test_f1_positive() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT}
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

                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null}

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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS #2")
    public void test_f1_edge_2() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.BOOK, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS: null matrix")
    public void test_f1_edge_3() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        boolean doesMatrixMatch = sixPairsCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }
}