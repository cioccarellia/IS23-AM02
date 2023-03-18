package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.costants.BookShelfConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalCardTest {

    Tile[][] nullMatrix = new Tile[ROWS][COLUMNS];

    @Test
    @DisplayName("Verify SIX_PAIRS positively #1")
    public void test_f1_positive() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT},
                {Tile.PLANT, null, Tile.PLANT, null, Tile.PLANT}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testPositiveMatrix);

        // assert that the matrix matches
        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify SIX_PAIRS negatively #1")
    public void test_f1_negative() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testNegativeMatrix = {

                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null}

        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS #1")
    public void test_f1_edge_1() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS #2")
    public void test_f1_edge_2() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, null, null, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.BOOK, null},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, null}
        };

        boolean doesMatrixMatch = sixPairsCGC.matches(testNegativeMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("edge case for SIX_PAIRS: null matrix")
    public void test_f1_edge_3() {
        CommonGoalCard sixPairsCGC = CommonGoalCardFunctionContainer.SIX_PAIRS;

        boolean doesMatrixMatch = sixPairsCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }


    @Test
    @DisplayName("Verify DIAGONAL positively: upper diagonal")
    public void test_f2_positive_1() {
        CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

        Tile[][] testPositiveMatrix = {
                {Tile.PLANT, null, null, null, null},
                {null, Tile.PLANT, null, null, null},
                {null, null, Tile.PLANT, null, null},
                {null, null, null, Tile.PLANT, null},
                {null, null, null, null, Tile.PLANT},
                {null, null, null, null, null}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL positively: lower diagonal")
    public void test_f2_positive_2() {
        CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, null, null, null, null},
                {null, Tile.CAT, null, null, null},
                {null, null, Tile.CAT, null, null},
                {null, null, null, Tile.CAT, null},
                {null, null, null, null, Tile.CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: almost diagonal")
    public void test_f2_negative_1() {
        CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, null, null, null, null},
                {null, Tile.CAT, null, null, null},
                {null, null, null, null, null},
                {null, null, null, Tile.CAT, null},
                {null, null, null, null, Tile.CAT}
        };

        boolean doesMatrixMatch = diagonalCGC.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify DIAGONAL negatively: null matrix")
    public void test_f2_negative_2() {
        CommonGoalCard diagonalCGC = CommonGoalCardFunctionContainer.DIAGONAL;

        boolean doesMatrixMatch = diagonalCGC.matches(nullMatrix);

        // assert that the matrix does not match
        assertFalse(doesMatrixMatch);
    }


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

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES positively #1")
    public void test_f4_positive_1() {
        //mi sa che c'è qualche problema con la copia della matrice nelle funzioni precedenti
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT},
                {Tile.PLANT, Tile.TROPHY, Tile.CAT, Tile.TROPHY, Tile.PLANT}
        };

        boolean doesMatrixMatch = four_max3diffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES positively #2")
    public void test_f4_positive_2() {
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT, Tile.CAT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT}
        };

        boolean doesMatrixMatch = four_max3diffCGC.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES negatively #1")
    public void test_f4_negative_1() {
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT}
        };

        boolean doesMatrixMatch = four_max3diffCGC.matches(testPositiveMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify FOUR_MAX3DIFFERENT_LINES negatively #2")
    public void test_f4_negative_2() {
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        Tile[][] testPositiveMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT, Tile.PLANT},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.GAME}
        };

        boolean doesMatrixMatch = four_max3diffCGC.matches(testPositiveMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Edge case for FOUR_MAX3DIFFERENT_LINES: null matrix")
    public void test_f4_edge_1() {
        CommonGoalCard four_max3diffCGC = CommonGoalCardFunctionContainer.FOUR_MAX3DIFF_LINES;

        boolean doesMatrixMatch = four_max3diffCGC.matches(nullMatrix);

        // assert that the matrix matches
        assertFalse(doesMatrixMatch);
    }



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

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS positively #1")
    public void test_f6_positive_1() {
        CommonGoalCard twoDiffColumns = CommonGoalCardFunctionContainer.TWO_DIFF_COLUMNS;

        Tile[][] testPositiveMatrix = {
                {Tile.CAT, null, null, null, Tile.CAT},
                {Tile.TROPHY, null, null, null, Tile.GAME},
                {Tile.GAME, null, null, null, Tile.PLANT},
                {Tile.PLANT, null, null, null, Tile.TROPHY},
                {Tile.FRAME, null, null, null, Tile.BOOK},
                {Tile.BOOK, null, null, null, Tile.FRAME}
        };

        boolean doesMatrixMatch = twoDiffColumns.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS positively #2")
    public void test_f6_positive_2() {
        CommonGoalCard twoDiffColumns = CommonGoalCardFunctionContainer.TWO_DIFF_COLUMNS;

        Tile[][] testPositiveMatrix = {
                {null, Tile.CAT,  Tile.CAT, null, null},
                {null, Tile.TROPHY, Tile.GAME, null, null},
                {null, Tile.GAME, Tile.PLANT, null, null},
                {null, Tile.PLANT, Tile.TROPHY, null, null},
                {null, Tile.FRAME, Tile.BOOK, null, null},
                {null, Tile.BOOK, Tile.FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumns.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS negatively #1")
    public void test_f6_negative_1() {
        CommonGoalCard twoDiffColumns = CommonGoalCardFunctionContainer.TWO_DIFF_COLUMNS;

        Tile[][] testNegativeMatrix = {
                {null, Tile.CAT,  Tile.BOOK, null, null},
                {null, Tile.CAT, Tile.GAME, null, null},
                {null, Tile.GAME, Tile.PLANT, null, null},
                {null, Tile.PLANT, Tile.TROPHY, null, null},
                {null, Tile.FRAME, Tile.BOOK, null, null},
                {null, Tile.BOOK, Tile.FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumns.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_DIFF_COLUMNS negatively #2")
    public void test_f6_negative_2() {
        CommonGoalCard twoDiffColumns = CommonGoalCardFunctionContainer.TWO_DIFF_COLUMNS;

        Tile[][] testNegativeMatrix = {
                {Tile.CAT, null, null, null, null},
                {Tile.CAT, null, Tile.GAME, null, null},
                {Tile.CAT, null, Tile.PLANT, null, null},
                {Tile.CAT, null, Tile.TROPHY, null, null},
                {Tile.FRAME, null, Tile.BOOK, null, null},
                {Tile.BOOK, null, Tile.FRAME, null, null}
        };

        boolean doesMatrixMatch = twoDiffColumns.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify TWO_SQUARES positively #1")
    public void test_f7_positive_1() {
        CommonGoalCard twoSquares = CommonGoalCardFunctionContainer.TWO_SQUARES;

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
        CommonGoalCard twoSquares = CommonGoalCardFunctionContainer.TWO_SQUARES;

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
        CommonGoalCard twoSquares = CommonGoalCardFunctionContainer.TWO_SQUARES;

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
        CommonGoalCard twoSquares = CommonGoalCardFunctionContainer.TWO_SQUARES;

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

    @Test
    @DisplayName("Verify TWO_DIFF_LINES positively #1")
    public void test_f8_positive_1() {
        CommonGoalCard twoDiffLines = CommonGoalCardFunctionContainer.TWO_DIFF_LINES;

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
        CommonGoalCard twoDiffLines = CommonGoalCardFunctionContainer.TWO_DIFF_LINES;

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
        CommonGoalCard twoDiffLines = CommonGoalCardFunctionContainer.TWO_DIFF_LINES;

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
        CommonGoalCard twoDiffLines = CommonGoalCardFunctionContainer.TWO_DIFF_LINES;

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

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS positively #1")
    public void test_f9_positive_1() {
        CommonGoalCard threeMax3diffColumns = CommonGoalCardFunctionContainer.THREE_MAX3DIFF_COLUMNS;

        Tile[][] testPositiveMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.CAT},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.PLANT},
                {Tile.PLANT, Tile.CAT, Tile.GAME, Tile.FRAME, Tile.CAT},
                {Tile.CAT, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.GAME},
                {Tile.BOOK, Tile.CAT, Tile.TROPHY, Tile.FRAME, Tile.PLANT}
        };

        boolean doesMatrixMatch = threeMax3diffColumns.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS positively #2")
    public void test_f9_positive_2() {
        CommonGoalCard threeMax3diffColumns = CommonGoalCardFunctionContainer.THREE_MAX3DIFF_COLUMNS;

        Tile[][] testPositiveMatrix = {
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumns.matches(testPositiveMatrix);

        assertTrue(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS negatively #1")
    public void test_f9_negative_1() {
        CommonGoalCard threeMax3diffColumns = CommonGoalCardFunctionContainer.THREE_MAX3DIFF_COLUMNS;

        Tile[][] testNegativeMatrix = {
                {Tile.CAT, Tile.CAT, Tile.PLANT, Tile.TROPHY, Tile.CAT},
                {Tile.CAT, Tile.GAME, Tile.FRAME, Tile.PLANT, Tile.GAME},
                {Tile.GAME, Tile.TROPHY, Tile.GAME, Tile.CAT, Tile.TROPHY},
                {Tile.PLANT, Tile.FRAME, Tile.GAME, Tile.GAME, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumns.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

    @Test
    @DisplayName("Verify THREE_MAX3DIFF_COLUMNS negatively #2")
    public void test_f9_negative_2() {
        CommonGoalCard threeMax3diffColumns = CommonGoalCardFunctionContainer.THREE_MAX3DIFF_COLUMNS;

        Tile[][] testNegativeMatrix = {
                {null, null, null, null, null},
                {Tile.CAT, Tile.GAME, null, Tile.PLANT, Tile.GAME},
                {Tile.GAME, Tile.CAT, Tile.GAME, Tile.TROPHY, Tile.TROPHY},
                {Tile.PLANT, Tile.CAT, Tile.GAME, Tile.TROPHY, Tile.TROPHY},
                {Tile.FRAME, Tile.CAT, Tile.TROPHY, Tile.TROPHY, Tile.FRAME},
                {Tile.BOOK, Tile.CAT, Tile.CAT, Tile.FRAME, Tile.FRAME}
        };

        boolean doesMatrixMatch = threeMax3diffColumns.matches(testNegativeMatrix);

        assertFalse(doesMatrixMatch);
    }

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
        CommonGoalCard xTiles= CommonGoalCardFunctionContainer.X_TILES;

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