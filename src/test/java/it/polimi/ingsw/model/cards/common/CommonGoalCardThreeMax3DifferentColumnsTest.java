package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.BaseShelfMatrixTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardThreeMax3DifferentColumnsTest extends BaseShelfMatrixTester {

    CommonGoalCard threeMax3diffColumns = CommonGoalCardFunctionContainer.THREE_MAX3DIFF_COLUMNS;

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS positively #1")
    public void test_f9_positive_1() {

        Tile[][] testPositiveMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.CAT},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.PLANT},
                {Tile.PLANT, Tile.CAT, Tile.GAME, Tile.FRAME, Tile.CAT},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.GAME},
                {Tile.BOOK, Tile.CAT, Tile.TROPHY, Tile.FRAME, Tile.PLANT}
        };

        boolean doesMatrixMatch = threeMax3diffColumns.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS positively #2")
    public void test_f9_positive_2() {
        Tile[][] testPositiveMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumns.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS negatively #1")
    public void test_f9_negative_1() {
        Tile[][] testNegativeMatrix = {
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.CAT},
                {Tile.CAT, Tile.GAME, Tile.FRAME, Tile.PLANT, Tile.GAME},
                {Tile.GAME, Tile.TROPHY, Tile.GAME, Tile.CAT, Tile.TROPHY},
                {Tile.PLANT, Tile.FRAME, Tile.GAME, Tile.GAME, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumns.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS negatively #2")
    public void test_f9_negative_2() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.GAME, null, Tile.PLANT, Tile.GAME},
                {Tile.GAME, Tile.CAT, Tile.GAME, Tile.TROPHY, Tile.TROPHY},
                {Tile.PLANT, Tile.CAT, Tile.GAME, Tile.TROPHY, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumns.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }
}