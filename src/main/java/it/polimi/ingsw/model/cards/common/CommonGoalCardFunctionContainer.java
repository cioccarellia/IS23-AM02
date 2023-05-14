package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.groupfinder.Group;
import it.polimi.ingsw.groupfinder.GroupFinder;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.config.common.CGCConfiguration;
import org.apache.commons.lang.SerializationUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    // common goal cards
    public static final CommonGoalCard SIX_PAIRS = new CommonGoalCard(CommonGoalCardIdentifier.SIX_PAIRS, CommonGoalCardFunctionContainer::sixPairs);
    public static final CommonGoalCard FOUR_GROUP_FOUR = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_GROUP_FOUR, CommonGoalCardFunctionContainer::fourGroupFour);
    // bookshelf parameters
    private final static int rows = BookshelfConfiguration.getInstance().rows();
    public static final CommonGoalCard DIAGONAL = new CommonGoalCard(CommonGoalCardIdentifier.DIAGONAL, CommonGoalCardFunctionContainer::diagonal);
    private final static int cols = BookshelfConfiguration.getInstance().cols();
    public static final CommonGoalCard FOUR_MAX3DIFF_LINES = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_MAX3DIFF_LINES, CommonGoalCardFunctionContainer::fourMaxThreeDiffLines);
    public static final CommonGoalCard FOUR_CORNERS = new CommonGoalCard(CommonGoalCardIdentifier.FOUR_CORNERS, CommonGoalCardFunctionContainer::fourCorners);
    public static final CommonGoalCard TWO_DIFF_COLUMNS = new CommonGoalCard(CommonGoalCardIdentifier.TWO_DIFF_COLUMNS, CommonGoalCardFunctionContainer::twoDiffColumns);
    public static final CommonGoalCard TWO_SQUARES = new CommonGoalCard(CommonGoalCardIdentifier.TWO_SQUARES, CommonGoalCardFunctionContainer::twoSquares);
    public static final CommonGoalCard TWO_DIFF_LINES = new CommonGoalCard(CommonGoalCardIdentifier.TWO_DIFF_LINES, CommonGoalCardFunctionContainer::twoDiffLines);
    public static final CommonGoalCard THREE_MAX3DIFF_COLUMNS = new CommonGoalCard(CommonGoalCardIdentifier.THREE_MAX3DIFF_COLUMNS, CommonGoalCardFunctionContainer::threeMaxThreeDiffColumns);
    public static final CommonGoalCard X_TILES = new CommonGoalCard(CommonGoalCardIdentifier.X_TILES, CommonGoalCardFunctionContainer::xTiles);
    public static final CommonGoalCard EIGHT_TILES = new CommonGoalCard(CommonGoalCardIdentifier.EIGHT_TILES, CommonGoalCardFunctionContainer::eightTiles);
    public static final CommonGoalCard STAIRS = new CommonGoalCard(CommonGoalCardIdentifier.STAIRS, CommonGoalCardFunctionContainer::stairs);

    /**
     * List of all valid instances of {@link CommonGoalCard}
     */
    public static final List<CommonGoalCard> commonGoalCardDomain = Arrays.asList(SIX_PAIRS, DIAGONAL, FOUR_GROUP_FOUR, FOUR_MAX3DIFF_LINES, FOUR_CORNERS, TWO_DIFF_COLUMNS, TWO_SQUARES, TWO_DIFF_LINES, THREE_MAX3DIFF_COLUMNS, X_TILES, EIGHT_TILES, STAIRS);


    /**
     * Returns the active {@code CommonGoalCard}s list
     */
    public static List<CommonGoalCard> getActiveCommonGoalCards() {
        List<CommonGoalCardIdentifier> ids = CGCConfiguration.getInstance().getActiveCommonGoalCardIds();
        return commonGoalCardDomain.stream().filter(it -> ids.contains(it.getId())).toList();
    }

    /**
     * Returns true if there are 6 groups each containing at least 2 tiles of the same type
     *
     * @see CommonGoalCardIdentifier#SIX_PAIRS
     */
    private static Boolean sixPairs(Tile[][] matrix) {
        GroupFinder f = new GroupFinder(matrix);
        List<Group> groups = f.computeGroupPartition();

        return groups.stream().filter(group -> group.size() == 2).toList().size() >= 6;
    }

    /**
     * Returns true if there are five tiles of the same type forming a diagonal
     *
     * @see CommonGoalCardIdentifier#DIAGONAL
     */
    private static Boolean diagonal(Tile[][] matrix) {
        boolean hasAcceptableDiagonal1 = true;
        boolean hasAcceptableDiagonal2 = true;

        Tile t1 = matrix[0][0];
        Tile t2 = matrix[1][0];

        for (int i = 1; i < rows - 1; i++) {
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
     * Returns true if there are four groups each containing at least 4 tiles of the same type
     *
     * @see CommonGoalCardIdentifier#FOUR_GROUP_FOUR
     */
    private static Boolean fourGroupFour(Tile[][] matrix) {
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
     * Returns true if there are four lines each formed by 5 tiles,
     * with at most 3 different types of tile
     *
     * @see CommonGoalCardIdentifier#FOUR_MAX3DIFF_LINES
     */
    private static Boolean fourMaxThreeDiffLines(Tile[][] matrix) {
        int countDifferentLines = 0;

        for (int i = 0; i < rows; i++) {
            int countBookTile = 0, countCatTile = 0, countGameTile = 0, countTrophyTile = 0, countPlantTile = 0, countFrameTile = 0;

            for (int j = 0; j < cols; j++) {
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

                boolean isSumUnder3 = (countBookTile + countCatTile + countGameTile + countTrophyTile + countPlantTile + countFrameTile) <= 3;

                if (j == 4 && isSumUnder3)
                    countDifferentLines++;
            }
        }
        return countDifferentLines >= 4;
    }

    /**
     * Returns true if at the four corners of the bookshelf there are tiles of the same value
     *
     * @see CommonGoalCardIdentifier#FOUR_CORNERS
     */
    private static Boolean fourCorners(Tile[][] matrix) {
        Tile topLeft = matrix[0][0];
        Tile topRight = matrix[0][cols - 1];
        Tile bottomLeft = matrix[rows - 1][0];
        Tile bottomRight = matrix[rows - 1][cols - 1];

        return (topLeft != null) &&
                (topLeft == topRight) &&
                (topLeft == bottomRight) &&
                (topLeft == bottomLeft);
    }

    /**
     * Returns true if there are two columns each formed by 6 different types of tiles
     *
     * @see CommonGoalCardIdentifier#TWO_DIFF_COLUMNS
     */
    private static Boolean twoDiffColumns(Tile[][] matrix) {
        int count = 0;

        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                boolean hasSameTileOrNull = false;
                Tile t = matrix[i][j];

                for (int h = i + 1; h < rows - 1; h++) {
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
     * Returns true if there are two groups each containing 4 tiles of the same type in a 2x2 square
     *
     * @see CommonGoalCardIdentifier#TWO_SQUARES
     */
    private static Boolean twoSquares(Tile[][] matrix) {
        Tile[][] matrixCopy = (Tile[][]) SerializationUtils.clone(matrix);
        int groupCounter = 0;

        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols - 1; j++) {
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
     * Returns true if there are 2 lines each formed by 5 different types of tiles
     *
     * @see CommonGoalCardIdentifier#TWO_DIFF_LINES
     */
    private static Boolean twoDiffLines(Tile[][] matrix) {
        int countLines = 0;

        for (int i = 0; i < rows; i++) {
            int countBookTile = 0, countCatTile = 0, countGameTile = 0, countTrophyTile = 0, countPlantTile = 0, countFrameTile = 0;

            for (int j = 0; j < cols; j++) {
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
     * Returns true if there are three columns each formed by 6 tiles with at most 3 different types of tile
     *
     * @see CommonGoalCardIdentifier#THREE_MAX3DIFF_COLUMNS
     */
    private static Boolean threeMaxThreeDiffColumns(Tile[][] matrix) {
        int countDifferentColumn = 0;

        for (int j = 0; j < cols; j++) {
            int countBookTile = 0, countCatTile = 0, countGameTile = 0, countTrophyTile = 0, countPlantTile = 0, countFrameTile = 0;

            for (int i = 0; i < rows; i++) {
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
     * Returns true if there are 5 tiles of the same type forming an X
     *
     * @see CommonGoalCardIdentifier#X_TILES
     */
    private static Boolean xTiles(Tile[][] matrix) {
        for (int i = 0; i < rows - 2; i++) {
            for (int j = 0; j < cols - 2; j++) {
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
     * Returns true if there are 8 tiles of the same type, with no other restriction
     *
     * @see CommonGoalCardIdentifier#EIGHT_TILES
     */
    private static Boolean eightTiles(Tile[][] matrix) {
        int countBookTiles = 0, countCatTiles = 0, countGameTiles = 0, countTrophyTiles = 0, countPlantTiles = 0, countFrameTiles = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
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
    private static Boolean stairs(Tile[][] matrix) {
        int count1 = 0;
        int count2 = 0;

        for (int i = 1; i < rows; i++) {
            if (matrix[i][i - 1] != null) count1++;
        }
        for (int i = 0; i < cols; i++) {
            if (matrix[cols - i][i] != null) count2++;
        }

        return (count1 == 5 || count2 == 5);
    }

    /**
     * Maps a {@link CommonGoalCardIdentifier} to its associated {@link CommonGoalCard}
     */
    public static Map<CommonGoalCardIdentifier, CommonGoalCard> commonGoalCardMap() {
        return commonGoalCardDomain.stream().collect(Collectors.toMap(CommonGoalCard::getId, Function.identity()));
    }
}