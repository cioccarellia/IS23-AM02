package it.polimi.ingsw.model.game;

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
}