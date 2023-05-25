package it.polimi.ingsw.model.commonGoalCards;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardFourMax3Test implements ShelfMatrixTester {

    final CommonGoalCard fourMax3DiffCGC = FOUR_MAX3DIFF_LINES;

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES positively #1")
    public void test_f4_positive_1() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {CAT, CAT, CAT, CAT, CAT},
                {PLANT, TROPHY, CAT, TROPHY, PLANT},
                {PLANT, TROPHY, CAT, TROPHY, PLANT},
                {PLANT, TROPHY, CAT, TROPHY, PLANT}
        };

        boolean doesMatrixMatch = fourMax3DiffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES positively #2")
    public void test_f4_positive_2() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {CAT, CAT, CAT, CAT, CAT},
                {CAT, CAT, CAT, CAT, CAT},
                {PLANT, PLANT, PLANT, PLANT, PLANT},
                {PLANT, PLANT, PLANT, PLANT, PLANT},
                {PLANT, PLANT, PLANT, PLANT, PLANT}
        };

        boolean doesMatrixMatch = fourMax3DiffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES negatively #1")
    public void test_f4_negative_1() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {PLANT, PLANT, PLANT, PLANT, PLANT},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {PLANT, PLANT, PLANT, PLANT, PLANT}
        };

        boolean doesMatrixMatch = fourMax3DiffCGC.matches(testPositiveMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES negatively #2")
    public void test_f4_negative_2() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {PLANT, PLANT, PLANT, PLANT, PLANT},
                {CAT, CAT, PLANT, TROPHY, GAME},
                {CAT, CAT, PLANT, TROPHY, GAME}
        };

        boolean doesMatrixMatch = fourMax3DiffCGC.matches(testPositiveMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_MAX3DIFFERENT_LINES: null matrix")
    public void test_f4_edge_1() {
        boolean doesMatrixMatch = fourMax3DiffCGC.matches(nullMatrix);

        // assert that the matrix matches
        assertFalse(doesMatrixMatch);
    }

}