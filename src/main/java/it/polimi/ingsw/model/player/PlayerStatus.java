package it.polimi.ingsw.model.player;

/**
 * Defines the status of the player's game session
 */
public enum PlayerStatus {
    /**
     * The player is currently playing normally
     */
    PLAYING,

    /**
     * The player's connection is broken
     */
    DISCONNECTED,

    /**
     * The player has quit the game
     */
    QUITTED
}