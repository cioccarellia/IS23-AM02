package it.polimi.ingsw.model.board.cell;

/**
 * Defines the pattern of a cell
 */
public enum CellPattern {
    NORMAL(2),
    THREE_DOTS(3),
    FOUR_DOTS(4);

    private final int playerAmount;

    CellPattern(int playerAmount) {
        this.playerAmount = playerAmount;
    }

    public int getPlayerCount() {
        return playerAmount;
    }
}
