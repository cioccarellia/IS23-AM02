package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.CAT;
import static it.polimi.ingsw.model.board.Tile.TROPHY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardFourCornersTest implements ShelfMatrixTester {

    CommonGoalCard fourCornersCGC = CommonGoalCardFunctionContainer.FOUR_CORNERS;

    @Test
    @DisplayName("Verify FOUR_CORNERS positively #1")
    public void test_f5_positive_1() {
        Tile[][] testPositiveMatrix = {
                {CAT, null, null, null, CAT},
                {TROPHY, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {CAT, null, null, null, CAT}
        };

        boolean doesMatrixMatch = fourCornersCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_CORNERS negatively #1")
    public void test_f5_negative_1() {
        Tile[][] testNegativeMatrix = {
                {TROPHY, null, null, null, CAT},
                {TROPHY, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {CAT, null, null, null, TROPHY}
        };

        boolean doesMatrixMatch = fourCornersCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_CORNERS negatively #2")
    public void test_f5_negative_2() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, CAT},
                {null, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {TROPHY, null, null, null, TROPHY},
                {CAT, null, null, null, TROPHY}
        };

        boolean doesMatrixMatch = fourCornersCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_CORNERS negatively: null matrix")
    public void test_f5_negative_null_matrix() {
        boolean doesMatrixMatch = fourCornersCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }
}