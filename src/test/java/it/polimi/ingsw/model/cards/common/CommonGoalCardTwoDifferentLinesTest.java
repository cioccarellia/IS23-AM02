package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer.TWO_DIFF_LINES;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardTwoDifferentLinesTest implements ShelfMatrixTester {

    final CommonGoalCard twoDiffLinesCGC = TWO_DIFF_LINES;

    @Test
    @DisplayName("Verify TWO_DIFF_LINES positively #1")
    public void test_f8_positive_1() {
        Tile[][] testPositiveMatrix = {
                {CAT, CAT, null, null, CAT},
                {CAT, CAT, null, null, GAME},
                {GAME, CAT, null, null, PLANT},
                {PLANT, CAT, GAME, FRAME, TROPHY},
                {FRAME, CAT, CAT, FRAME, FRAME},
                {BOOK, CAT, TROPHY, FRAME, PLANT}
        };

        boolean doesMatrixMatch = twoDiffLinesCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_LINES positively #2")
    public void test_f8_positive_2() {
        Tile[][] testPositiveMatrix = {
                {CAT, CAT, null, null, CAT},
                {CAT, CAT, null, null, GAME},
                {FRAME, CAT, TROPHY, GAME, PLANT},
                {PLANT, CAT, FRAME, GAME, TROPHY},
                {FRAME, CAT, CAT, FRAME, FRAME},
                {BOOK, CAT, CAT, FRAME, FRAME}
        };

        boolean doesMatrixMatch = twoDiffLinesCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_LINES negatively #1")
    public void test_f8_negative_1() {
        Tile[][] tileMatrix = {
                {CAT, CAT, PLANT, TROPHY, CAT},
                {CAT, GAME, FRAME, PLANT, GAME},
                {GAME, CAT, GAME, TROPHY, TROPHY},
                {PLANT, CAT, GAME, TROPHY, TROPHY},
                {FRAME, CAT, TROPHY, TROPHY, FRAME},
                {BOOK, CAT, CAT, FRAME, FRAME}
        };

        boolean doesMatrixMatch = twoDiffLinesCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_LINES negatively #2")
    public void test_f8_negative_2() {
        Tile[][] tileMatrix = {
                {CAT, CAT, null, null, CAT},
                {CAT, GAME, null, null, GAME},
                {GAME, CAT, null, TROPHY, TROPHY},
                {PLANT, CAT, null, TROPHY, TROPHY},
                {FRAME, CAT, null, TROPHY, FRAME},
                {BOOK, CAT, null, FRAME, FRAME}
        };

        boolean doesMatrixMatch = twoDiffLinesCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_LINES negatively: null matrix")
    public void test_f8_negative_3() {
        boolean doesMatrixMatch = twoDiffLinesCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }
}