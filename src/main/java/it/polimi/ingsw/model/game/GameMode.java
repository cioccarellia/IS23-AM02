package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.CellAmount;

/**
 * Defines the type of game that is being played.
 * There are three possible configurations, depending on the number
 * of players in the game.
 */
public enum GameMode {
    GAME_MODE_2_PLAYERS(2),
    GAME_MODE_3_PLAYERS(3),
    GAME_MODE_4_PLAYERS(4);

    private final int players;

    GameMode(int maxPlayers) {
        players = maxPlayers;
    }

    public int maxPlayerAmount() {
        return players;
    }

    public int maxCellAmount() {
        return switch (maxPlayerAmount()) {
            case 2 -> CellAmount.NORMAL_AMOUNT.getCellCount();
            case 3 -> CellAmount.THREE_DOTS_AMOUNT.getCellCount();
            case 4 -> CellAmount.FOUR_DOTS_AMOUNT.getCellCount();
            default -> throw new IllegalStateException("Unexpected value: " + maxPlayerAmount());
        };
    }
}