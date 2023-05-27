package it.polimi.ingsw.model.game;

import static it.polimi.ingsw.model.board.CellAmount.*;

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

    /**
     * @return the number of usable cells on the board for a specific game mode
     */
    public int maxCellAmount() {
        return switch (maxPlayerAmount()) {
            case 2 -> NORMAL_AMOUNT.getCellCount();
            case 3 -> THREE_DOTS_AMOUNT.getCellCount();
            case 4 -> FOUR_DOTS_AMOUNT.getCellCount();
            default -> throw new IllegalStateException("Unexpected value: " + maxPlayerAmount());
        };
    }

    /**
     * @param number how many players are in the game
     * @return the game mode corresponding to the number of players
     */
    public static GameMode numberToMode(int number) {
        return switch (number) {
            case 2 -> GAME_MODE_2_PLAYERS;
            case 3 -> GAME_MODE_3_PLAYERS;
            case 4 -> GAME_MODE_4_PLAYERS;
            default -> throw new IllegalArgumentException("Expecting a number between 2 and 4.");
        };
    }
}