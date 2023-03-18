package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.BaseShelfMatrixTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalCardFourMax3Test extends BaseShelfMatrixTester {

    CommonGoalCard fourMax3DiffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES positively #1")
    public void test_f4_positive_1() {
        // fixme mi sa che c'è qualche problema con la copia della matrice nelle funzioni precedenti

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT}
        };

        boolean doesMatrixMatch = fourMax3DiffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES positively #2")
    public void test_f4_positive_2() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT}
        };

        boolean doesMatrixMatch = fourMax3DiffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES negatively #1")
    public void test_f4_negative_1() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT}
        };

        boolean doesMatrixMatch = fourMax3DiffCGC.matches(testPositiveMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES negatively #2")
    public void test_f4_negative_2() {
        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME}
        };

        boolean doesMatrixMatch = fourMax3DiffCGC.matches(testPositiveMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_MAX3DIFFERENT_LINES: null matrix")
    public void test_f4_edge_1() {
        boolean doesMatrixMatch = fourMax3DiffCGC.matches(nullMatrix);

        // assert that the matrix matches
        assertFalse(doesMatrixMatch);
    }

}