package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardCrossTest extends CommonGoalCardBase {

    @Test
    @DisplayName("Verify X_TILES positively #1")
    public void test_f10_positive_1() {
        CommonGoalCard xTiles = CommonGoalCardFunctionContainer.X_TILES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, null, Tile.PLANT, null, null},
                {Tile.GAME, Tile.PLANT, Tile.PLANT, null, null},
                {Tile.PLANT, Tile.TROPHY, Tile.PLANT, null, null}
        };

        boolean doesMatrixMatch = xTiles.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify X_TILES positively #2")
    public void test_f10_positive_2() {
        CommonGoalCard xTiles = CommonGoalCardFunctionContainer.X_TILES;

        Tile[][] testPositiveMatrix = {
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, Tile.TROPHY, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, Tile.TROPHY, Tile.PLANT, null, Tile.PLANT}
        };

        boolean doesMatrixMatch = xTiles.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify X_TILES negatively #1")
    public void test_f10_negative_1() {
        CommonGoalCard xTiles = CommonGoalCardFunctionContainer.X_TILES;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.GAME, null, Tile.PLANT, Tile.GAME},
                {Tile.GAME, Tile.CAT, Tile.GAME, Tile.TROPHY, Tile.TROPHY},
                {Tile.PLANT, Tile.CAT, Tile.GAME, Tile.TROPHY, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = xTiles.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify X_TILES negatively #2")
    public void test_f10_negative_2() {
        CommonGoalCard xTiles = CommonGoalCardFunctionContainer.X_TILES;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, Tile.PLANT, null, null},
                {null, null, Tile.TROPHY, null, Tile.GAME},
                {Tile.TROPHY, null, Tile.TROPHY, null, Tile.PLANT},
                {Tile.TROPHY, null, Tile.TROPHY, null, Tile.PLANT}
        };

        boolean doesMatrixMatch = xTiles.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }
}