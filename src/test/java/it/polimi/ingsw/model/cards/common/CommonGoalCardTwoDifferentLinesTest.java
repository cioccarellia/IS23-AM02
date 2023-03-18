package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.BaseShelfMatrixTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardTwoDifferentLinesTest extends BaseShelfMatrixTester {

    CommonGoalCard twoDiffLines = CommonGoalCardFunctionContainer.TWO_DIFF_LINES;

    @Test
    @DisplayName("Verify TWO_DIFF_LINES positively #1")
    public void test_f8_positive_1() {
        Tile[][] testPositiveMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.CAT},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.GAME, Tile.CAT, null, null, Tile.PLANT},
                {Tile.PLANT, Tile.CAT, Tile.GAME, Tile.FRAME, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.TROPHY, Tile.FRAME, Tile.PLANT}
        };

        boolean doesMatrixMatch = twoDiffLines.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_LINES positively #2")
    public void test_f8_positive_2() {
        Tile[][] testPositiveMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.CAT},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.FRAME, Tile.CAT, Tile.TROPHY, Tile.GAME, Tile.PLANT},
                {Tile.PLANT, Tile.CAT, Tile.FRAME, Tile.GAME, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = twoDiffLines.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_LINES negatively #1")
    public void test_f8_negative_1() {
        Tile[][] testNegativeMatrix = {
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.CAT},
                {Tile.CAT, Tile.GAME, Tile.FRAME, Tile.PLANT, Tile.GAME},
                {Tile.GAME, Tile.CAT, Tile.GAME, Tile.TROPHY, Tile.TROPHY},
                {Tile.PLANT, Tile.CAT, Tile.GAME, Tile.TROPHY, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = twoDiffLines.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_LINES negatively #2")
    public void test_f8_negative_2() {
        Tile[][] testNegativeMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.CAT},
                {Tile.CAT, Tile.GAME, null, null, Tile.GAME},
                {Tile.GAME, Tile.CAT, null, Tile.TROPHY, Tile.TROPHY},
                {Tile.PLANT, Tile.CAT, null, Tile.TROPHY, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, null, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, null, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = twoDiffLines.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }
}