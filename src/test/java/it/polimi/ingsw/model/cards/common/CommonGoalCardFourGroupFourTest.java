package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardFourGroupFourTest implements ShelfMatrixTester {

    CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

    @Test
    @Disabled
    @DisplayName("Verify FOUR_GROUP_FOUR positively #1")
    public void test_f3_positive_1() {
        // fixme should be negative

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {PLANT, PLANT, PLANT, PLANT, null},
                {PLANT, PLANT, PLANT, PLANT, null},
                {PLANT, PLANT, PLANT, PLANT, null},
                {PLANT, PLANT, PLANT, PLANT, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @Disabled
    @DisplayName("Verify FOUR_GROUP_FOUR positively #2")
    public void test_f3_positive_2() {
        // fixme this should be a negative test, as there are not 4 contiguous groups in the matrix

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, PLANT, null, null},
                {BOOK, BOOK, PLANT, null, null},
                {BOOK, BOOK, PLANT, CAT, null},
                {PLANT, PLANT, PLANT, CAT, null},
                {PLANT, PLANT, CAT, CAT, null},

        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR negatively #1")
    public void test_f3_negative_1() {
        Tile[][] testNegativeMatrix = {
                {PLANT, null, PLANT, PLANT, null},
                {PLANT, null, GAME, TROPHY, null},
                {PLANT, null, TROPHY, GAME, null},
                {PLANT, null, PLANT, CAT, null},
                {BOOK, null, GAME, FRAME, null},
                {GAME, null, FRAME, CAT, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR negatively #2")
    public void test_f3_negative_2() {
        // fixme questo crasha perché è sbagliata la funzione, dobbiamo capire come farla
        Tile[][] testNegativeMatrix = {
                {PLANT, PLANT, PLANT, PLANT, null},
                {PLANT, GAME, CAT, FRAME, null},
                {PLANT, PLANT, PLANT, PLANT, null},
                {PLANT, FRAME, PLANT, TROPHY, null},
                {GAME, CAT, FRAME, CAT, null},
                {PLANT, CAT, FRAME, CAT, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_GROUP_FOUR: 3 rows and 1 column: #1")
    public void test_f3_edge_1() {
        // fixme normale che crashi perché la funzione è da sistemare
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, PLANT},
                {BOOK, FRAME, FRAME, FRAME, PLANT},
                {BOOK, BOOK, FRAME, GAME, PLANT},
                {BOOK, GAME, GAME, GAME, PLANT}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_GROUP_FOUR: null matrix #2")
    public void test_f3_edge_2() {
        boolean doesMatrixMatch = fourGroupFour.matches(nullMatrix);

        // assert that the matrix matches
        assertFalse(doesMatrixMatch);
    }
}