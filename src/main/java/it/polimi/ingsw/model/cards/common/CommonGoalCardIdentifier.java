package it.polimi.ingsw.model.cards.common;

/**
 * Uniquely identifies a {@link CommonGoalCard}.
 * The enum fields are declared in order, as given in the game manual
 */
public enum CommonGoalCardIdentifier {

    /**
     * Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).
     * The tiles of one group can be different from those of another group.
     */
    SIX_PAIRS,

    /**
     * Five tiles of the same type forming a diagonal.
     */
    DIAGONAL,

    /**
     * Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).
     * The tiles of one group can be different from those of another group.
     */
    FOUR_GROUP_FOUR,

    /**
     * Four lines each formed by 5 tiles of maximum three different types.
     * One line can show the same or a different combination of another line.
     */
    FOUR_MAX3DIFF_LINES,

    /**
     * Four tiles of the same type in the four corners of the bookshelf.
     */
    FOUR_CORNERS,

    /**
     * Two columns each formed by 6 different types of tiles.
     */
    TWO_DIFF_COLUMNS,

    /**
     * Two groups each containing 4 tiles of the same type in a 2x2 square.
     * The tiles of one square can be different from those of the other square.
     */
    TWO_SQUARES,

    /**
     * Two lines each formed by 5 different types of tiles.
     * One line can show the same or a different combination of the other line.
     */
    TWO_DIFF_LINES,

    /**
     * Three columns each formed by 6 tiles of maximum three different types.
     * One column can show the same or a different combination of another column.
     */
    THREE_MAX3DIFF_COLUMNS,

    /**
     * Five tiles of the same type forming an X.
     */
    X_TILES,

    /**
     * Eight tiles of the same type. There’s no restriction about the position of these tiles.
     */
    EIGHT_TILES,

    /**
     * Five columns of increasing or decreasing height.
     * Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
     * Tiles can be of any type.
     */
    STAIRS
}