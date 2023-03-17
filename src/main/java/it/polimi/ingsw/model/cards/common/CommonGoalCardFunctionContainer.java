package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static it.polimi.ingsw.costants.BookShelfConstants.*;

/**
 * 1:  SIX_PAIRS
 * 2:  DIAGONAL
 * 3:  FOUR_GROUP_FOUR
 * 4:  FOUR_MAX3DIFF_LINES
 * 5:  FOUR_CORNERS
 * 6:  TWO_DIFF_COLUMNS
 * 7:  TWO_SQUARES
 * 8:  TWO_DIFF_LINES
 * 9:  THREE_MAX3DIFF_COLUMNS
 * 10: X_TILES
 * 11: EIGHT_TILES
 * 12: STAIRS
 */

public class CommonGoalCardFunctionContainer {

    private static final CommonGoalCard c1 = new CommonGoalCard(CommonGoalCardIdentifier.SIX_PAIRS, CommonGoalCardFunctionContainer::f1);
    private static final CommonGoalCard c2 = new CommonGoalCard(CommonGoalCardIdentifier.DIAGONAL, CommonGoalCardFunctionContainer::f2);
    private static final CommonGoalCard c3 = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_GROUP_FOUR, CommonGoalCardFunctionContainer::f3);
    private static final CommonGoalCard c4 = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_MAX3DIFF_LINES, CommonGoalCardFunctionContainer::f4);
    private static final CommonGoalCard c5 = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_CORNERS, CommonGoalCardFunctionContainer::f5);
    private static final CommonGoalCard c6 = new CommonGoalCard(CommonGoalCardIdentifier.TWO_DIFF_COLUMNS, CommonGoalCardFunctionContainer::f6);
    private static final CommonGoalCard c7 = new CommonGoalCard(CommonGoalCardIdentifier.TWO_SQUARES, CommonGoalCardFunctionContainer::f7);
    private static final CommonGoalCard c8 = new CommonGoalCard(CommonGoalCardIdentifier.TWO_DIFF_LINES, CommonGoalCardFunctionContainer::f8);
    private static final CommonGoalCard c9 = new CommonGoalCard(CommonGoalCardIdentifier.THREE_MAX3DIFF_COLUMNS, CommonGoalCardFunctionContainer::f9);
    private static final CommonGoalCard c10 = new CommonGoalCard(CommonGoalCardIdentifier.X_TILES, CommonGoalCardFunctionContainer::f10);
    private static final CommonGoalCard c11 = new CommonGoalCard(CommonGoalCardIdentifier.EIGHT_TILES, CommonGoalCardFunctionContainer::f11);
    private static final CommonGoalCard c12 = new CommonGoalCard(CommonGoalCardIdentifier.STAIRS, CommonGoalCardFunctionContainer::f12);


    /**
     * Return true if there are 6 groups each containing at least 2 tiles of the same type
     *
     * @see CommonGoalCardIdentifier#SIX_PAIRS
     */
    private static Boolean f1(Tile[][] matrix) {
        // FIXME
        int groupCounter = 0;
        for (int i = 0; i < ROWS - 1; i = i + 2) {
            for (int j = 0; j < COLUMNS - 1; j = j + 2) {
                if (matrix[i][j] == matrix[i][j + 1] && matrix[i][j] != matrix[i][j + 2]) groupCounter++;
                if (matrix[i][j] == matrix[i + 1][j] && matrix[i][j] != matrix[i + 2][j]) groupCounter++;
            }
        }

        return groupCounter >= 6;
    }

    /**
     * Return true if there are one or two diagonals each containing 5 tiles of the same type
     *
     * @see CommonGoalCardIdentifier#DIAGONAL
     */
    private static Boolean f2(Tile[][] matrix) {
        int firstDiagonalCounter = 0;
        int secondDiagonalCounter = 0;

        for (int i = 0; i < COLUMNS; i++) {
            // FIXME
            if (matrix[COLUMNS - i][COLUMNS - i] == matrix[COLUMNS - i - 1][i + 1]) firstDiagonalCounter++;
            if (matrix[COLUMNS - i - 1][COLUMNS - i - 1] == matrix[COLUMNS - i - 2][i + 1]) secondDiagonalCounter++;
        }

        return (firstDiagonalCounter == 5 || secondDiagonalCounter == 5);
    }

    /**
     * Return true if there are 4 columns each containing at least 4 tiles
     *
     * @see CommonGoalCardIdentifier#FOUR_GROUP_FOUR
     */
    private static Boolean f3(Tile[][] matrix) {
        // FIXME & TODO
        int countColumns = 0;
        for (int i = 0; i < ROWS; i++) {
            if (matrix[i][0] == matrix[i][1] && (matrix[i][1] == matrix[i][2]) && (matrix[i][2] == matrix[i][3])
                    && (matrix[i][3] == matrix[i][4])
                    && (matrix[i][4] != matrix[i][5]))
                countColumns++;
        }

        return countColumns >= 4;
    }

    /**
     * Return true if there are 4 rows each containing at max 3 different tiles
     *
     * @see CommonGoalCardIdentifier#FOUR_MAX3DIFF_LINES
     */
    private static Boolean f4(Tile[][] matrix) {
        // FIXME
        int matchingRows = 0;

        for (int i = 0; i < ROWS; i++) {
            int tileTypeInRow = 0;

            for (int j = 0; j < COLUMNS; j++) {
                if (matrix[i][j] != matrix[i][j + 1])
                    tileTypeInRow++;
            }

            if (tileTypeInRow <= 3)
                matchingRows++;
        }

        return matchingRows >= 4;
    }

    /**
     * @see CommonGoalCardIdentifier#FOUR_CORNERS
     */
    private static Boolean f5(Tile[][] matrix) {
        if (matrix[0][0] == matrix[0][COLUMNS - 1] && matrix[0][0] == matrix[ROWS - 1][COLUMNS - 1] &&
                matrix[0][0] == matrix[ROWS - 1][0]) {
            return true;
        } else return false;
    }


    /**
     * @see CommonGoalCardIdentifier#TWO_DIFF_COLUMNS
     */
    private static Boolean f6(Tile[][] matrix) {
        /*int cont = 0;
        int j = 0;
        for (int i = 0; i < ROWS; i++) {
            if (matrix[i][j] != matrix[i][j + 1] &&
                    matrix[i][j] != matrix[i][j + 2] &&
                    matrix[i][j] != matrix[i][j + 3] &&
                    matrix[i][j] != matrix[i][j + 4] &&
                    matrix[i][j] != matrix[i][j + 5])
                if (matrix[i][j + 1] != matrix[i][j + 2] &&
                        matrix[i][j + 1] != matrix[i][j + 3] &&
                        matrix[i][j + 1] != matrix[i][j + 4] &&
                        matrix[i][j + 1] != matrix[i][j + 5])
                    if (matrix[i][j + 2] != matrix[i][j + 3] &&
                            matrix[i][j + 2] != matrix[i][j + 4] &&
                            matrix[i][j + 2] != matrix[i][j + 5])
                        if (matrix[i][j + 3] != matrix[i][j + 4] &&
                                matrix[i][j + 3] != matrix[i][j + 5])
                            if (matrix[i][j + 4] != matrix[i][j + 5]) {
                                cont++;
                            }
        }
        return cont >= 2;
        */
        int count = 0;
        for (int j = 0; j < COLUMNS; j++) {
            for (int i = 0; i < ROWS; i++) {
                boolean hasSameTile = false;
                for (int h = i + 1; i < ROWS - 1; h++) {
                    if (matrix[i][j] == matrix[h][j]) {
                        hasSameTile = true;
                        break;
                    }
                }
                if (hasSameTile) {
                    break;
                }
                count++;
            }
        }
        return count >= 2;
    }

    /**
     * @see CommonGoalCardIdentifier#TWO_SQUARES
     */
    private static Boolean f7(Tile[][] matrix) {
        int groupCounter = 0;
        for (int i = 0; i < ROWS - 1; i++) {
            int jFound1 = 0;
            int jFound2 = 0;
            for (int j = 1; j < COLUMNS; j++) {
                Tile t = matrix[i][j];
                if (j != jFound1 && j != jFound1 - 1 && j != jFound2 && j != jFound2 && t == matrix[i][j - 1] &&
                        t == matrix[i + 1][j] && t == matrix[i + 1][j - 1]) {
                    groupCounter++;
                    j++;
                    if (jFound1 == 0)
                        jFound1 = j;
                    else
                        jFound2 = j;
                }
            }
        }

        return groupCounter >= 2;
    }

    /**
     * Return true if there are 2 lines each formed by at least 5 different tiles
     */
    private static Boolean f8(Tile[][] matrix) {
        int countDifferentRows = 0, countDifferentCells = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (matrix[i + j][i + j] != matrix[i + j][i + j + 1]) countDifferentCells++;
            }
            if (countDifferentCells == 5) countDifferentRows++;
            countDifferentCells = 0;
        }

        return countDifferentCells >= 2;
    }

    /**
     * @see CommonGoalCardIdentifier#THREE_MAX3DIFF_COLUMNS
     */
    private static Boolean f9(Tile[][] matrix) {
        int contDifferentColumn = 0;
        int cont1 = 0;
        int cont2 = 0;
        int cont3 = 0;
        int cont4 = 0;
        int cont5 = 0;
        int cont6 = 0;
        for (int i = 0; i < ROWS; i++) {
            cont1 = cont2 = cont3 = cont4 = cont5 = cont6 = 0;

            for (int j = 0; j < COLUMNS; j++) {
                if (matrix[i][j] == Tile.BOOK) {
                    cont1 = 1;
                }

                if (matrix[i][j] == Tile.CAT) {
                    cont2 = 1;
                }

                if (matrix[i][j] == Tile.GAME) {
                    cont3 = 1;
                }

                if (matrix[i][j] == Tile.TROPHY) {
                    cont4 = 1;
                }

                if (matrix[i][j] == Tile.PLANT) {
                    cont5 = 1;
                }

                if (matrix[i][j] == Tile.FRAME) {
                    cont6 = 1;
                }
                if (cont1 + cont2 + cont3 + cont4 + cont5 + cont6 <= 3) contDifferentColumn++;
            }

        }
        return contDifferentColumn >= 3;
    }


    /**
     * @see CommonGoalCardIdentifier#X_TILES
     */
    private static Boolean f10(Tile[][] matrix) {
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                // enumerate cross points
                Tile topLeft = matrix[i][j];
                Tile topRight = matrix[i][j + 2];
                Tile center = matrix[i + 1][j + 1];
                Tile bottomRight = matrix[i + 2][j + 2];
                Tile bottomLeft = matrix[i + 2][j];

                // check whether all the cross points are equal
                boolean isCross = Stream.of(topLeft, topRight, bottomRight, bottomLeft).allMatch(it -> it == center);

                if (isCross)
                    return true;
            }
        }

        return false;
    }


    /**
     * @see CommonGoalCardIdentifier#EIGHT_TILES
     */
    private static Boolean f11(Tile[][] matrix) {
        int cont1 = 0;
        int cont2 = 0;
        int cont3 = 0;
        int cont4 = 0;
        int cont5 = 0;
        int cont6 = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (matrix[i][j] == Tile.BOOK) {
                    cont1++;
                }

                if (matrix[i][j] == Tile.CAT) {
                    cont2++;
                }

                if (matrix[i][j] == Tile.GAME) {
                    cont3++;
                }

                if (matrix[i][j] == Tile.TROPHY) {
                    cont4++;
                }

                if (matrix[i][j] == Tile.PLANT) {
                    cont5++;
                }

                if (matrix[i][j] == Tile.FRAME) {
                    cont6++;
                }
            }

        }

        if (cont1 >= 8 || cont2 >= 8 || cont3 >= 8 || cont4 >= 8 || cont5 >= 8 || cont6 >= 8) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * return true if there's left side stair or a right side stair
     *
     * @see CommonGoalCardIdentifier#STAIRS
     */
    private static Boolean f12(Tile[][] matrix) {
        int count1 = 0;
        int count2 = 0;

        for (int i = 1; i < ROWS; i++) {
            if (matrix[i][i - 1] != null)
                count1++;
        }
        for (int i = 0; i < COLUMNS; i++) {
            if (matrix[COLUMNS - i][i] != null)
                count2++;
        }

        if (count1 == 5 || count2 == 5)
            return true;
        else return false;
    }

    public static final List<CommonGoalCard> commonGoalCardDomain = Arrays.asList(
            c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12
    );
}
