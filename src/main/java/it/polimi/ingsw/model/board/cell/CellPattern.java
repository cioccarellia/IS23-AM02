package it.polimi.ingsw.model.board.cell;

/**
 * Defines the pattern of
 */
public enum CellPattern {
    NORMAL(2),
    THREE_DOTS(3),
    FOUR_DOTS(4);

    private final int playerCount;

    CellPattern(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
