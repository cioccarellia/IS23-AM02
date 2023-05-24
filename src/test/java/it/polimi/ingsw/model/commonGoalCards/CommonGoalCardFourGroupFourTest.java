package it.polimi.ingsw.model.commonGoalCards;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardFourGroupFourTest implements ShelfMatrixTester {

    final CommonGoalCard fourGroupFourCGC = FOUR_GROUP_FOUR;

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR positively #1")
    public void test_f3_positive_1() {

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {PLANT, CAT, PLANT, CAT, null},
                {PLANT, CAT, PLANT, CAT, null},
                {PLANT, CAT, PLANT, CAT, null},
                {PLANT, CAT, PLANT, CAT, null}
        };

        boolean doesMatrixMatch = fourGroupFourCGC.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR positively #2")
    public void test_f3_positive_2() {

        Tile[][] testPositiveMatrix = {
                {null, null, PLANT, null, null},
                {null, null, PLANT, null, null},
                {BOOK, BOOK, PLANT, CAT, null},
                {BOOK, BOOK, PLANT, CAT, null},
                {PLANT, PLANT, TROPHY, CAT, null},
                {PLANT, PLANT, CAT, CAT, null},

        };

        boolean doesMatrixMatch = fourGroupFourCGC.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR negatively #1")
    public void test_f3_negative_1() {
        Tile[][] tileMatrix = {
                {PLANT, null, PLANT, PLANT, null},
                {PLANT, null, GAME, TROPHY, null},
                {PLANT, null, TROPHY, GAME, null},
                {PLANT, null, PLANT, CAT, null},
                {BOOK, null, GAME, FRAME, null},
                {GAME, null, FRAME, CAT, null}
        };

        boolean doesMatrixMatch = fourGroupFourCGC.matches(tileMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR negatively #2")
    public void test_f3_negative_2() {

        Tile[][] tileMatrix = {
                {PLANT, PLANT, PLANT, PLANT, null},
                {PLANT, GAME, CAT, FRAME, null},
                {PLANT, PLANT, PLANT, PLANT, null},
                {PLANT, FRAME, PLANT, TROPHY, null},
                {GAME, CAT, FRAME, CAT, null},
                {PLANT, CAT, FRAME, CAT, null}
        };

        boolean doesMatrixMatch = fourGroupFourCGC.matches(tileMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_GROUP_FOUR: 3 rows and 1 column: #1")
    public void test_f3_edge_1() {

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, PLANT},
                {BOOK, FRAME, FRAME, FRAME, PLANT},
                {BOOK, BOOK, FRAME, GAME, PLANT},
                {BOOK, GAME, GAME, GAME, PLANT}
        };

        boolean doesMatrixMatch = fourGroupFourCGC.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_GROUP_FOUR: null matrix #2")
    public void test_f3_edge_2() {
        boolean doesMatrixMatch = fourGroupFourCGC.matches(nullMatrix);

        // assert that the matrix matches
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case fot FOUR_GROUP_FOUR: false curious matrix #3")
    public void test_f3_edge_3() {

        Tile[][] tileMatrix = {
                {null, null, null, null, null},
                {null, null, PLANT, null, null},
                {BOOK, BOOK, PLANT, CAT, null},
                {BOOK, BOOK, PLANT, CAT, null},
                {PLANT, PLANT, PLANT, CAT, null},
                {PLANT, PLANT, CAT, CAT, null},

        };
        boolean doesMatrixMatch = fourGroupFourCGC.matches(tileMatrix);
        assertFalse(doesMatrixMatch);
    }
}