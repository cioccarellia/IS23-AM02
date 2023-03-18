package it.polimi.ingsw.model.game;

/**
 * Encodes the current status of the game
 * */
public enum GameStatus {
    /**
     * The game is currently being initialized and is not being played.
     * This state is kept until the designated amount of players has
     * joined the current game.
     *
     * @see GameMode
     * */
    INITIALIZATION,

    /**
     * The game is running, the players are connected.
     * */
    RUNNING,

    /**
     * The game reached the last round of play, and it will end within a few turns.
     * */
    LAST_ROUND,

    /**
     * The game ended, a winner may have been declared and the users are disconnected.
     * */
    ENDED,

    /**
     * The game is put on hold, as the conditions to continue playing are not met.
     * */
    STANDBY
}
