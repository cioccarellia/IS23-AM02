package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardTwoDifferentColumnsTest implements ShelfMatrixTester {

    CommonGoalCard twoDiffColumns = CommonGoalCardFunctionContainer.TWO_DIFF_COLUMNS;

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS positively #1")
    public void test_f6_positive_1() {
        Tile[][] testPositiveMatrix = {
                {Tile.CAT, null, null, null, Tile.CAT},
                {Tile.TROPHY, null, null, null, Tile.GAME},
                {Tile.GAME, null, null, null, Tile.PLANT},
                {Tile.PLANT, null, null, null, Tile.TROPHY},
                {Tile.FRAME, null, null, null, Tile.BOOK},
                {Tile.BOOK, null, null, null, Tile.FRAME}
        };

        boolean doesMatrixMatch = twoDiffColumns.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS positively #2")
    public void test_f6_positive_2() {
        Tile[][] testPositiveMatrix = {
                {null, Tile.CAT, Tile.CAT, null, null},
                {null, Tile.TROPHY, Tile.GAME, null, null},
                {null, Tile.GAME, Tile.PLANT, null, null},
                {null, Tile.PLANT, Tile.TROPHY, null, null},
                {null, Tile.FRAME, Tile.BOOK, null, null},
                {null, Tile.BOOK, Tile.FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumns.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS negatively #1")
    public void test_f6_negative_1() {
        Tile[][] testNegativeMatrix = {
                {null, Tile.CAT, Tile.BOOK, null, null},
                {null, Tile.CAT, Tile.GAME, null, null},
                {null, Tile.GAME, Tile.PLANT, null, null},
                {null, Tile.PLANT, Tile.TROPHY, null, null},
                {null, Tile.FRAME, Tile.BOOK, null, null},
                {null, Tile.BOOK, Tile.FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumns.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS negatively #2")
    public void test_f6_negative_2() {
        Tile[][] testNegativeMatrix = {
                {Tile.CAT, null, null, null, null},
                {Tile.CAT, null, Tile.GAME, null, null},
                {Tile.CAT, null, Tile.PLANT, null, null},
                {Tile.CAT, null, Tile.TROPHY, null, null},
                {Tile.FRAME, null, Tile.BOOK, null, null},
                {Tile.BOOK, null, Tile.FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumns.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

}