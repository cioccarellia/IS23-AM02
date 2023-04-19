package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalCardTwoDifferentColumnsTest implements ShelfMatrixTester {

    CommonGoalCard twoDiffColumnsCGC = CommonGoalCardFunctionContainer.TWO_DIFF_COLUMNS;

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS positively #1")
    public void test_f6_positive_1() {
        Tile[][] testPositiveMatrix = {
                {CAT, null, null, null, CAT},
                {TROPHY, null, null, null, GAME},
                {GAME, null, null, null, PLANT},
                {PLANT, null, null, null, TROPHY},
                {FRAME, null, null, null, BOOK},
                {BOOK, null, null, null, FRAME}
        };

        boolean doesMatrixMatch = twoDiffColumnsCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS positively #2")
    public void test_f6_positive_2() {
        Tile[][] testPositiveMatrix = {
                {null, CAT, CAT, null, null},
                {null, TROPHY, GAME, null, null},
                {null, GAME, PLANT, null, null},
                {null, PLANT, TROPHY, null, null},
                {null, FRAME, BOOK, null, null},
                {null, BOOK, FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumnsCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS negatively #1")
    public void test_f6_negative_1() {
        Tile[][] tileMatrix = {
                {null, CAT, BOOK, null, null},
                {null, CAT, GAME, null, null},
                {null, GAME, PLANT, null, null},
                {null, PLANT, TROPHY, null, null},
                {null, FRAME, BOOK, null, null},
                {null, BOOK, FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumnsCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS negatively #2")
    public void test_f6_negative_2() {
        Tile[][] tileMatrix = {
                {CAT, null, null, null, null},
                {CAT, null, GAME, null, null},
                {CAT, null, PLANT, null, null},
                {CAT, null, TROPHY, null, null},
                {FRAME, null, BOOK, null, null},
                {BOOK, null, FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumnsCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS negatively: null matrix")
    public void test_f6_negative_3() {
        boolean doesMatrixMatch = twoDiffColumnsCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

}