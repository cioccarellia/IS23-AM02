package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardStairsTest implements ShelfMatrixTester {
    CommonGoalCard stairs = CommonGoalCardFunctionContainer.STAIRS;

    @Test
    @DisplayName("Verify STAIRS positively #1")
    public void test_f12_positive_1() {

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.TROPHY, null, null, null, null},
                {Tile.GAME, Tile.GAME, null, null, null},
                {Tile.TROPHY, Tile.GAME, Tile.TROPHY, null, null},
                {Tile.GAME, Tile.GAME, Tile.PLANT, Tile.GAME, null},
                {Tile.CAT, Tile.GAME, Tile.GAME, Tile.BOOK, Tile.FRAME}
        };

        boolean doesMatrixMatch = stairs.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify STAIRS positively #2")
    public void test_f12_positive_2() {

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null,Tile.CAT},
                {null, null, null, Tile.GAME, Tile.PLANT},
                {null, null, Tile.TROPHY, Tile.GAME, Tile.GAME},
                {null, Tile.GAME, Tile.GAME, Tile.GAME, Tile.GAME},
                {Tile.FRAME, Tile.PLANT, Tile.GAME, Tile.TROPHY, Tile.CAT}
        };

        boolean doesMatrixMatch = stairs.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify STAIRS negatively #1")
    public void test_f12_negative_1() {

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.GAME, Tile.GAME, null, null, null},
                {Tile.TROPHY, Tile.GAME, Tile.TROPHY, null, null},
                {Tile.GAME, Tile.GAME, Tile.PLANT, Tile.GAME, null},
                {Tile.CAT, Tile.GAME, Tile.GAME, Tile.BOOK, null}
        };

        boolean doesMatrixMatch = stairs.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify STAIRS negatively #2")
    public void test_f12_negative_2() {

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, Tile.GAME, Tile.PLANT},
                {null, null, Tile.TROPHY, Tile.GAME, Tile.GAME},
                {null, Tile.GAME, Tile.GAME, Tile.GAME, Tile.GAME},
                {null, Tile.PLANT, Tile.GAME, Tile.TROPHY, Tile.CAT}
        };

        boolean doesMatrixMatch = stairs.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for STAIRS: little stair on the left #1")
    public void test_f12_edge_1() {

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.GAME, null, null, null, null},
                {Tile.TROPHY, Tile.GAME, null, null, null},
                {Tile.GAME, Tile.GAME, Tile.PLANT, null, null},
                {Tile.CAT, Tile.GAME, Tile.GAME, Tile.BOOK, null}
        };

        boolean doesMatrixMatch = stairs.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for STAIRS positively: little stair on the right #2")
    public void test_f12_edge_2() {

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, Tile.PLANT},
                {null, null, null, Tile.GAME, Tile.GAME},
                {null, null, Tile.GAME, Tile.GAME, Tile.GAME},
                {null, Tile.PLANT, Tile.GAME, Tile.TROPHY, Tile.CAT}
        };

        boolean doesMatrixMatch = stairs.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

}
