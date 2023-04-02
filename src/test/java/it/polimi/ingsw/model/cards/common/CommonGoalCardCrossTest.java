package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardCrossTest implements ShelfMatrixTester {

    CommonGoalCard xTiles = CommonGoalCardFunctionContainer.X_TILES;

    @Test
    @DisplayName("Verify X_TILES positively #1")
    public void test_f10_positive_1() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {PLANT, null, PLANT, null, null},
                {GAME, PLANT, PLANT, null, null},
                {PLANT, TROPHY, PLANT, null, null}
        };

        boolean doesMatrixMatch = xTiles.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify X_TILES positively #2")
    public void test_f10_positive_2() {
        Tile[][] testPositiveMatrix = {
                {PLANT, null, PLANT, null, PLANT},
                {PLANT, null, PLANT, null, PLANT},
                {PLANT, null, PLANT, null, GAME},
                {PLANT, PLANT, PLANT, null, PLANT},
                {PLANT, TROPHY, PLANT, null, PLANT},
                {PLANT, TROPHY, PLANT, null, PLANT}
        };

        boolean doesMatrixMatch = xTiles.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify X_TILES negatively #1")
    public void test_f10_negative_1() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {CAT, GAME, null, PLANT, GAME},
                {GAME, CAT, GAME, TROPHY, TROPHY},
                {PLANT, CAT, GAME, TROPHY, TROPHY},
                {FRAME, CAT, TROPHY, TROPHY, FRAME},
                {BOOK, CAT, CAT, FRAME, FRAME}
        };

        boolean doesMatrixMatch = xTiles.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify X_TILES negatively #2")
    public void test_f10_negative_2() {
        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, PLANT, null, null},
                {null, null, TROPHY, null, GAME},
                {TROPHY, null, TROPHY, null, PLANT},
                {TROPHY, null, TROPHY, null, PLANT}
        };

        boolean doesMatrixMatch = xTiles.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify X_TILES negatively: null matrix")
    public void test_f10_negative_null_matrix() {
        boolean doesMatrixMatch = xTiles.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }


    @Test
    @DisplayName("Verify X_TILES negatively: full matrix")
    public void test_f10_negative_full_matrix() {
        boolean doesMatrixMatch = xTiles.matches(generateFullMatrixOf(PLANT));

        // assert that the matrix does not match
        assertTrue(doesMatrixMatch);
    }
}