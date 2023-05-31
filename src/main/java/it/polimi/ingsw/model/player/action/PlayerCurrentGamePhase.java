package it.polimi.ingsw.model.player.action;

import java.io.Serializable;

/**
 * Enumeration representing the player possible game phases
 */
public enum PlayerCurrentGamePhase implements Serializable {
    /**
     * It is not the player's turn
     */
    IDLE,
    /**
     * It's the player's turn, and they are selecting tiles from the board
     */
    SELECTING,
    /**
     * It's the player's turn, and they are inserting the selected tiles in their bookshelf
     */
    INSERTING
}
