package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.costants.BookShelfConstants.COLUMNS;
import static it.polimi.ingsw.costants.BookShelfConstants.ROWS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardFourGroupTest {

    Tile[][] nullMatrix = new Tile[ROWS][COLUMNS];

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR positively #1")
    public void test_f3_positive_1() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR positively #2")
    public void test_f3_positive_2() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, Tile.PLANT, null, null},
                {Tile.BOOK, Tile.BOOK, Tile.PLANT, null, null},
                {Tile.BOOK, Tile.BOOK, Tile.PLANT, Tile.CAT, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.CAT, null},
                {Tile.PLANT, Tile.PLANT, Tile.CAT, Tile.CAT, null},

        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR negatively #1")
    public void test_f3_negative_1() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        Tile[][] testNegativeMatrix = {
                {Tile.PLANT, null, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, null, Tile.GAME, Tile.TROPHY, null},
                {Tile.PLANT, null, Tile.TROPHY, Tile.GAME, null},
                {Tile.PLANT, null, Tile.PLANT, Tile.CAT, null},
                {Tile.BOOK, null, Tile.GAME, Tile.FRAME, null},
                {Tile.GAME, null, Tile.FRAME, Tile.CAT, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_GROUP_FOUR negatively #2")
    public void test_f3_negative_2() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;
        //questo crasha perché è sbagliata la funzione, dobbiamo capire come farla
        Tile[][] testNegativeMatrix = {
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.GAME, Tile.CAT, Tile.FRAME, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null},
                {Tile.PLANT, Tile.FRAME, Tile.PLANT, Tile.TROPHY, null},
                {Tile.GAME, Tile.CAT, Tile.FRAME, Tile.CAT, null},
                {Tile.PLANT, Tile.CAT, Tile.FRAME, Tile.CAT, null}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_GROUP_FOUR: 3 raws and 1 column: #1")
    public void test_f3_edge_1() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;
        //normale che crashi perché la funzione è da sistemare
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, Tile.PLANT},
                {Tile.BOOK, Tile.FRAME, Tile.FRAME, Tile.FRAME, Tile.PLANT},
                {Tile.BOOK, Tile.BOOK, Tile.FRAME, Tile.GAME, Tile.PLANT},
                {Tile.BOOK, Tile.GAME, Tile.GAME, Tile.GAME, Tile.PLANT}
        };

        boolean doesMatrixMatch = fourGroupFour.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_GROUP_FOUR: null matrix #2")
    public void test_f3_edge_2() {
        CommonGoalCard fourGroupFour = CommonGoalCardFunctionContainer.FOUR_GROUP_FOUR;

        boolean doesMatrixMatch = fourGroupFour.matches(nullMatrix);

        // assert that the matrix matches
        assertFalse(doesMatrixMatch);
    }
}