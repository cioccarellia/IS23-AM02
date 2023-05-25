package it.polimi.ingsw.model.commonGoalCards;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer.EIGHT_TILES;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardEightTilesTest implements ShelfMatrixTester {
    final CommonGoalCard eightTilesCGC = EIGHT_TILES;

    @Test
    @DisplayName("Verify EIGHT_TILES positively #1")
    public void test_f11_positive_1() {
        Tile[][] testPositiveMatrix = {
                {GAME, GAME, TROPHY, TROPHY, TROPHY},
                {CAT, CAT, TROPHY, TROPHY, TROPHY},
                {BOOK, BOOK, BOOK, BOOK, TROPHY},
                {CAT, BOOK, CAT, BOOK, CAT},
                {PLANT, PLANT, PLANT, CAT, CAT},
                {PLANT, PLANT, PLANT, PLANT, PLANT}
        };

        boolean doesMatrixMatch = eightTilesCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify EIGHT_TILES negatively #1")
    public void test_f11_negative_1() {
        Tile[][] tileMatrix = {
                {null, GAME, TROPHY, TROPHY, TROPHY},
                {CAT, CAT, TROPHY, TROPHY, TROPHY},
                {BOOK, BOOK, BOOK, BOOK, TROPHY},
                {CAT, BOOK, CAT, BOOK, CAT},
                {GAME, PLANT, PLANT, CAT, CAT},
                {PLANT, PLANT, PLANT, PLANT, PLANT}
        };

        boolean doesMatrixMatch = eightTilesCGC.matches(tileMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for EIGHT_TILES: null matrix #1")
    public void test_f11_edge_1() {
        boolean doesMatrixMatch = eightTilesCGC.matches(nullMatrix);

        assertFalse(doesMatrixMatch);
    }
}
