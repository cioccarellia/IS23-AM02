package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.costants.BookShelfConstants.COLUMNS;
import static it.polimi.ingsw.costants.BookShelfConstants.ROWS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardFourCornersTest {

    Tile[][] nullMatrix = new Tile[ROWS][COLUMNS];

    @Test
    @DisplayName("Verify FOUR_CORNERS positively #1")
    public void test_f5_positive_1() {
        CommonGoalCard fourCorners = CommonGoalCardFunctionContainer.FOUR_CORNERS;

        Tile[][] testPositiveMatrix = {
                {Tile.CAT, null, null, null, Tile.CAT},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.CAT, null, null, null, Tile.CAT}
        };

        boolean doesMatrixMatch = fourCorners.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_CORNERS negatively #1")
    public void test_f5_negative_1() {
        CommonGoalCard fourCorners = CommonGoalCardFunctionContainer.FOUR_CORNERS;

        Tile[][] testNegativeMatrix = {
                {Tile.TROPHY, null, null, null, Tile.CAT},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.CAT, null, null, null, Tile.TROPHY}
        };

        boolean doesMatrixMatch = fourCorners.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_CORNERS negatively #2")
    public void test_f5_negative_2() {
        CommonGoalCard fourCorners = CommonGoalCardFunctionContainer.FOUR_CORNERS;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, Tile.CAT},
                {null, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.TROPHY, null, null, null, Tile.TROPHY},
                {Tile.CAT, null, null, null, Tile.TROPHY}
        };

        boolean doesMatrixMatch = fourCorners.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }
}