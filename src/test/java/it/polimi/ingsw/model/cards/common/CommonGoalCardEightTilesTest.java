package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardEightTilesTest implements ShelfMatrixTester {
    CommonGoalCard eightTiles = CommonGoalCardFunctionContainer.EIGHT_TILES;

    @Test
    @DisplayName("Verify EIGHT_TILES positively #1")
    public void test_f11_positive_1() {
        Tile[][] testPositiveMatrix = {
                {Tile.GAME, Tile.GAME, Tile.TROPHY, Tile.TROPHY, Tile.TROPHY},
                {Tile.CAT, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.TROPHY},
                {Tile.BOOK, Tile.BOOK, Tile.BOOK, Tile.BOOK, Tile.TROPHY},
                {Tile.CAT, Tile.BOOK, Tile.CAT, Tile.BOOK, Tile.CAT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.CAT, Tile.CAT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT}
        };

        boolean doesMatrixMatch = eightTiles.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify EIGHT_TILES negatively #1")
    public void test_f11_negative_1() {
        Tile[][] testNegativeMatrix = {
                {null, Tile.GAME, Tile.TROPHY, Tile.TROPHY, Tile.TROPHY},
                {Tile.CAT, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.TROPHY},
                {Tile.BOOK, Tile.BOOK, Tile.BOOK, Tile.BOOK, Tile.TROPHY},
                {Tile.CAT, Tile.BOOK, Tile.CAT, Tile.BOOK, Tile.CAT},
                {Tile.GAME, Tile.PLANT, Tile.PLANT, Tile.CAT, Tile.CAT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT}
        };

        boolean doesMatrixMatch = eightTiles.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for EIGHT_TILES: null matrix #1")
    public void test_f11_edge_1() {
        boolean doesMatrixMatch = eightTiles.matches(nullMatrix);

        assertFalse(doesMatrixMatch);
    }
}
