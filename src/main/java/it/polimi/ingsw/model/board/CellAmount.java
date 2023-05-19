package it.polimi.ingsw.model.board;

/**
 * Enum stating the amount of cells for each CellPattern, and so each GameMode
 */
public enum CellAmount {
    /**
     * Number of cells for a two players game
     */
    NORMAL_AMOUNT(29),
    /**
     * Number of cells for a three players game
     */
    THREE_DOTS_AMOUNT(37),
    /**
     * Number of cells for a four players game
     */
    FOUR_DOTS_AMOUNT(45);

    private final int cellsAmount;

    CellAmount(int cellsAmount) {
        this.cellsAmount = cellsAmount;
    }

    /**
     * @return the number of players corresponding to the pattern (e.g. three_dots is for a three players game)
     */
    public int getCellCount() {
        return cellsAmount;
    }

}
