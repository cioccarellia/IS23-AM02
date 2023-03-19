package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.groupfinder.Group;
import it.polimi.ingsw.groupfinder.GroupFinder;
import it.polimi.ingsw.model.board.Tile;
import org.apache.commons.lang.SerializationUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.polimi.ingsw.costants.BookShelfConstants.COLUMNS;
import static it.polimi.ingsw.costants.BookShelfConstants.ROWS;

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

    public static final CommonGoalCard SIX_PAIRS = new CommonGoalCard(CommonGoalCardIdentifier.SIX_PAIRS, CommonGoalCardFunctionContainer::f1_groupfinder);
    public static final CommonGoalCard DIAGONAL = new CommonGoalCard(CommonGoalCardIdentifier.DIAGONAL, CommonGoalCardFunctionContainer::f2);
    public static final CommonGoalCard FOUR_GROUP_FOUR = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_GROUP_FOUR, CommonGoalCardFunctionContainer::f3_groupfinder);
    public static final CommonGoalCard FOUR_MAX3DIFF_LINES = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_MAX3DIFF_LINES, CommonGoalCardFunctionContainer::f4);
    public static final CommonGoalCard FOUR_CORNERS = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_CORNERS, CommonGoalCardFunctionContainer::f5);
    public static final CommonGoalCard TWO_DIFF_COLUMNS = new CommonGoalCard(CommonGoalCardIdentifier.TWO_DIFF_COLUMNS, CommonGoalCardFunctionContainer::f6);
    public static final CommonGoalCard TWO_SQUARES = new CommonGoalCard(CommonGoalCardIdentifier.TWO_SQUARES, CommonGoalCardFunctionContainer::f7);
    public static final CommonGoalCard TWO_DIFF_LINES = new CommonGoalCard(CommonGoalCardIdentifier.TWO_DIFF_LINES, CommonGoalCardFunctionContainer::f8);
    public static final CommonGoalCard THREE_MAX3DIFF_COLUMNS = new CommonGoalCard(CommonGoalCardIdentifier.THREE_MAX3DIFF_COLUMNS, CommonGoalCardFunctionContainer::f9);
    public static final CommonGoalCard X_TILES = new CommonGoalCard(CommonGoalCardIdentifier.X_TILES, CommonGoalCardFunctionContainer::f10);
    public static final CommonGoalCard EIGHT_TILES = new CommonGoalCard(CommonGoalCardIdentifier.EIGHT_TILES, CommonGoalCardFunctionContainer::f11);
    public static final CommonGoalCard STAIRS = new CommonGoalCard(CommonGoalCardIdentifier.STAIRS, CommonGoalCardFunctionContainer::f12);

    /**
     * Return true if there are 6 groups each containing at least 2 tiles of the same type
     *
     * @see CommonGoalCardIdentifier#SIX_PAIRS
     */
    @Deprecated
    private static Boolean f1(Tile[][] matrix) {
        // FIXME
        Tile[][] matrixCopy = (Tile[][]) SerializationUtils.clone(matrix);
        int countPairs = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Tile t = matrixCopy[i][j];
                if (t != null && j + 1 < COLUMNS && t == matrixCopy[i][j + 1]) {
                    countPairs++;
                    matrixCopy[i][j] = null;
                    matrixCopy[i][j + 1] = null;
                } else if (t != null && i + 1 < ROWS && t == matrixCopy[i + 1][j]) {
                    countPairs++;
                    matrixCopy[i][j] = null;
                    matrixCopy[i + 1][j] = null;
                }
            }
        }
        return countPairs >= 6;
    }


    private static Boolean f1_groupfinder(Tile[][] matrix) {
        GroupFinder f = new GroupFinder(matrix);
        List<Group> groups = f.computeGroupPartition();

        return groups.stream().filter(group -> group.size() == 2).toList().size() >= 6;
    }

    /**
     * Return true if there are one or two diagonals each containing 5 tiles of the same type
     *
     * @see CommonGoalCardIdentifier#DIAGONAL
     */
    private static Boolean f2(Tile[][] matrix) {
        boolean hasAcceptableDiagonal1 = true;
        boolean hasAcceptableDiagonal2 = true;

        Tile t1 = matrix[0][0];
        Tile t2 = matrix[1][0];

        for (int i = 1; i < ROWS - 1; i++) {
            if (hasAcceptableDiagonal1 && t1 != null && matrix[i][i] != t1) {
                hasAcceptableDiagonal1 = false;
            } else if (t1 == null) {
                hasAcceptableDiagonal1 = false;
            }

            if (hasAcceptableDiagonal2 && t2 != null && matrix[i + 1][i] != t2) {
                hasAcceptableDiagonal2 = false;
            } else if (t2 == null) {
                hasAcceptableDiagonal2 = false;
            }
        }

        return (hasAcceptableDiagonal1 || hasAcceptableDiagonal2);
    }

    /**
     * Return true if there are 4 columns each containing at least 4 tiles
     *
     * @see CommonGoalCardIdentifier#FOUR_GROUP_FOUR
     */
    @Deprecated
    private static Boolean f3(Tile[][] matrix) {
        int countGroups = 0;
        int countTilesGroup = 1;
        Tile[][] matrixCopy = (Tile[][]) SerializationUtils.clone(matrix);        //FIXME (tutta da sistemare e deep copy)
        for (int i = 0; i < ROWS - 1; i++) {
            for (int j = 0; j < COLUMNS - 1; j++) {
                Tile t = matrixCopy[i][j];
                if (t != null && matrixCopy[i + 1][j] == t) {
                    countTilesGroup++;
                    matrixCopy[i + 1][j] = null;
                    matrixCopy[i][j] = null;
                }
                if (t != null && matrix[i][j + 1] == t) {
                    countTilesGroup++;
                    matrixCopy[i][j + 1] = null;
                    matrixCopy[i][j] = null;
                }
            }
            if (countTilesGroup >= 8) {
                countGroups += 2;
            } else if (countTilesGroup >= 4) {
                countGroups++;
            }

        }
        return countGroups >= 4;
    }

    private static Boolean f3_groupfinder(Tile[][] matrix) {
        // grouping and mapping tiles
        GroupFinder f = new GroupFinder(matrix);
        Map<Tile, List<Group>> groupMap = f.computeGroupPartitionMap();

        // how many distinct groups satisfy the card's criteria
        int amountOfValidGroups = 0;

        for (Tile tileKey : groupMap.keySet()) {
            // list of groups of the same tile type
            List<Group> sameTileGroup = groupMap.get(tileKey);

            // increments the number of valid groups by the number of valid groups
            // found in the current tile key set.
            // a group is valid if there are at least 4 tiles of the same type
            amountOfValidGroups += sameTileGroup
                    .stream()
                    .filter(group -> group.size() >= 4)
                    .toList()
                    .size();
        }

        return amountOfValidGroups >= 4;
    }


    /**
     * returns if there are four lines with at most 3 different types of tile
     *
     * @see CommonGoalCardIdentifier#FOUR_MAX3DIFF_LINES
     */
    private static Boolean f4(Tile[][] matrix) {
        int countDifferentLines = 0;

        for (int i = 0; i < ROWS; i++) {
            int countBookTile = 0, countCatTile = 0, countGameTile = 0, countTrophyTile = 0, countPlantTile = 0, countFrameTile = 0;

            for (int j = 0; j < COLUMNS; j++) {
                if (matrix[i][j] == null) {
                    break;
                }

                switch (matrix[i][j]) {
                    case BOOK -> countBookTile = 1;
                    case CAT -> countCatTile = 1;
                    case GAME -> countGameTile = 1;
                    case TROPHY -> countTrophyTile = 1;
                    case PLANT -> countPlantTile = 1;
                    case FRAME -> countFrameTile = 1;
                }

                boolean isSumUnder3 = (countBookTile + countCatTile + countGameTile + countTrophyTile + countPlantTile + countFrameTile <= 3);

                if (j == 4 && isSumUnder3)
                    countDifferentLines++;
            }

        }
        return countDifferentLines >= 4;
    }

    /**
     * @see CommonGoalCardIdentifier#FOUR_CORNERS
     */
    private static Boolean f5(Tile[][] matrix) {
        Tile topLeft = matrix[0][0];
        Tile topRight = matrix[0][COLUMNS - 1];
        Tile bottomLeft = matrix[ROWS - 1][0];
        Tile bottomRight = matrix[ROWS - 1][COLUMNS - 1];

        return (topLeft != null) &&
                (topLeft == topRight) &&
                (topLeft == bottomRight) &&
                (topLeft == bottomLeft);
    }

    /**
     * @see CommonGoalCardIdentifier#TWO_DIFF_COLUMNS
     */
    private static Boolean f6(Tile[][] matrix) {
        int count = 0;

        for (int j = 0; j < COLUMNS; j++) {
            for (int i = 0; i < ROWS; i++) {
                boolean hasSameTileOrNull = false;
                Tile t = matrix[i][j];

                for (int h = i + 1; h < ROWS - 1; h++) {
                    if (t == null || t == matrix[h][j]) {
                        hasSameTileOrNull = true;
                        break;
                    }
                }

                if (hasSameTileOrNull) {
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
        Tile[][] matrixCopy = (Tile[][]) SerializationUtils.clone(matrix);
        int groupCounter = 0;

        for (int i = 0; i < ROWS - 1; i++) {
            for (int j = 0; j < COLUMNS - 1; j++) {
                Tile t = matrixCopy[i][j];
                if (t != null && t == matrixCopy[i][j + 1] && t == matrixCopy[i + 1][j] && t == matrixCopy[i + 1][j + 1]) {
                    groupCounter++;
                    matrixCopy[i][j] = null;
                    matrixCopy[i][j + 1] = null;
                    matrixCopy[i + 1][j] = null;
                    matrixCopy[i + 1][j + 1] = null;
                }
            }
        }

        return groupCounter >= 2;
    }

    /**
     * Return true if there are 2 lines each formed by at least 5 different tiles
     */
    private static Boolean f8(Tile[][] matrix) {
        int countLines = 0;

        for (int i = 0; i < ROWS; i++) {
            int countBookTile = 0, countCatTile = 0, countGameTile = 0, countTrophyTile = 0, countPlantTile = 0, countFrameTile = 0;

            for (int j = 0; j < COLUMNS; j++) {
                if (matrix[i][j] == null)
                    break;

                switch (matrix[i][j]) {
                    case BOOK -> countBookTile = 1;
                    case CAT -> countCatTile = 1;
                    case GAME -> countGameTile = 1;
                    case TROPHY -> countTrophyTile = 1;
                    case PLANT -> countPlantTile = 1;
                    case FRAME -> countFrameTile = 1;
                }

                boolean isSumEqualTo5 = countBookTile + countCatTile + countGameTile + countTrophyTile + countPlantTile + countFrameTile == 5;

                if (isSumEqualTo5)
                    countLines++;
            }
        }
        return countLines >= 2;

    }

    /**
     * returns if there are three columns with at most 3 different types of tile
     *
     * @see CommonGoalCardIdentifier#THREE_MAX3DIFF_COLUMNS
     */
    private static Boolean f9(Tile[][] matrix) {
        int countDifferentColumn = 0;

        for (int j = 0; j < COLUMNS; j++) {
            int countBookTile = 0, countCatTile = 0, countGameTile = 0, countTrophyTile = 0, countPlantTile = 0, countFrameTile = 0;

            for (int i = 0; i < ROWS; i++) {
                if (matrix[i][j] == null)
                    break;

                switch (matrix[i][j]) {
                    case BOOK -> countBookTile = 1;
                    case CAT -> countCatTile = 1;
                    case GAME -> countGameTile = 1;
                    case TROPHY -> countTrophyTile = 1;
                    case PLANT -> countPlantTile = 1;
                    case FRAME -> countFrameTile = 1;
                }

                boolean isSumLowerThan3 = (countBookTile + countCatTile + countGameTile + countTrophyTile + countPlantTile + countFrameTile <= 3);

                if (i == 5 && isSumLowerThan3)
                    countDifferentColumn++;
            }
        }
        return countDifferentColumn >= 3;
    }

    /**
     * returns if there are 5 tiles of the same type forming an X
     *
     * @see CommonGoalCardIdentifier#X_TILES
     */
    private static Boolean f10(Tile[][] matrix) {
        for (int i = 0; i < ROWS - 2; i++) {
            for (int j = 0; j < COLUMNS - 2; j++) {
                if (matrix[i][j] == null)
                    continue;

                // enumerate cross points
                Tile topLeft = matrix[i][j];
                Tile topRight = matrix[i][j + 2];
                Tile center = matrix[i + 1][j + 1];
                Tile bottomRight = matrix[i + 2][j + 2];
                Tile bottomLeft = matrix[i + 2][j];

                // check whether all the cross points are equal
                boolean isCross = Stream.of(topLeft, topRight, bottomRight, bottomLeft).allMatch(it -> it == center);

                // if pattern is a cross
                if (isCross)
                    return true;
            }
        }

        return false;
    }

    /**
     * returns true if there are 8 tiles of the same type
     *
     * @see CommonGoalCardIdentifier#EIGHT_TILES
     */
    private static Boolean f11(Tile[][] matrix) {
        int countBookTiles = 0, countCatTiles = 0, countGameTiles = 0, countTrophyTiles = 0, countPlantTiles = 0, countFrameTiles = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (matrix[i][j] == null)
                    break;

                switch (matrix[i][j]) {
                    case BOOK -> countBookTiles++;
                    case CAT -> countCatTiles++;
                    case GAME -> countGameTiles++;
                    case TROPHY -> countTrophyTiles++;
                    case PLANT -> countPlantTiles++;
                    case FRAME -> countFrameTiles++;
                }
            }
        }

        return (countBookTiles >= 8 || countCatTiles >= 8 || countGameTiles >= 8 || countTrophyTiles >= 8 ||
                countPlantTiles >= 8 || countFrameTiles >= 8);
    }

    /**
     * Return true if there's left side stair or a right side stair
     *
     * @see CommonGoalCardIdentifier#STAIRS
     */
    private static Boolean f12(Tile[][] matrix) {
        int count1 = 0;
        int count2 = 0;

        for (int i = 1; i < ROWS; i++) {
            if (matrix[i][i - 1] != null) count1++;
        }
        for (int i = 0; i < COLUMNS; i++) {
            if (matrix[COLUMNS - i][i] != null) count2++;
        }

        return (count1 == 5 || count2 == 5);
    }

    /**
     * List of all valid instances of {@link CommonGoalCard}
     */
    public static final List<CommonGoalCard> commonGoalCardDomain = Arrays.asList(SIX_PAIRS, DIAGONAL, FOUR_GROUP_FOUR, FOUR_MAX3DIFF_LINES, FOUR_CORNERS, TWO_DIFF_COLUMNS, TWO_SQUARES, TWO_DIFF_LINES, THREE_MAX3DIFF_COLUMNS, X_TILES, EIGHT_TILES, STAIRS);

    /**
     * Maps a {@link CommonGoalCardIdentifier} to its associated {@link CommonGoalCard}
     * */
    public static Map<CommonGoalCardIdentifier, CommonGoalCard> commonGoalCardMap() {
        return commonGoalCardDomain.stream().collect(Collectors.toMap(CommonGoalCard::getId, Function.identity()));
    }
}