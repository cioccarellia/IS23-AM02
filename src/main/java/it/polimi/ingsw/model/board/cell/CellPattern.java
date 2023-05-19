package it.polimi.ingsw.model.board.cell;

/**
 * Defines the pattern of a cell
 */
public enum CellPattern {
    /**
     * Pattern of cell used in every game
     */
    NORMAL(2),
    /**
     * Pattern of cell used only when three or four players are playing
     */
    THREE_DOTS(3),
    /**
     * Pattern of cell used only when four players are playing
     */
    FOUR_DOTS(4);

    private final int playerAmount;

    CellPattern(int playerAmount) {
        this.playerAmount = playerAmount;
    }

    /**
     * @return the number of players corresponding to the pattern (e.g. three_dots is for a three players game)
     */
    public int getPlayerCount() {
        return playerAmount;
    }
}
