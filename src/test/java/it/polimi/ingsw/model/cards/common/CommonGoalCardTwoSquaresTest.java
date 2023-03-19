package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardTwoSquaresTest implements ShelfMatrixTester {

    CommonGoalCard twoSquares = CommonGoalCardFunctionContainer.TWO_SQUARES;

    @Test
    @DisplayName("Verify TWO_SQUARES positively #1")
    public void test_f7_positive_1() {
        Tile[][] testPositiveMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.CAT},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.GAME, Tile.CAT, null, null, Tile.PLANT},
                {Tile.PLANT, Tile.CAT, null, null, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, null, Tile.FRAME, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, null, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = twoSquares.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES positively #2")
    public void test_f7_positive_2() {
        Tile[][] testPositiveMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.CAT},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.GAME, Tile.CAT, null, Tile.TROPHY, Tile.TROPHY},
                {Tile.PLANT, Tile.CAT, null, Tile.TROPHY, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, null, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, null, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = twoSquares.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES negatively #1")
    public void test_f7_negative_1() {
        Tile[][] testNegativeMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.CAT},
                {Tile.CAT, Tile.GAME, null, null, Tile.GAME},
                {Tile.GAME, Tile.CAT, null, Tile.TROPHY, Tile.TROPHY},
                {Tile.PLANT, Tile.CAT, null, Tile.TROPHY, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = twoSquares.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES negatively #2")
    public void test_f7_negative_2() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.TROPHY, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.TROPHY, Tile.PLANT, Tile.TROPHY, Tile.GAME}
        };

        boolean doesMatrixMatch = twoSquares.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

}