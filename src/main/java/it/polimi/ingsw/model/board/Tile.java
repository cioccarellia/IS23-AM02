package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * Enumeration representing the 6 possible game tiles.
 */
public enum Tile implements Serializable {

    /**
     * Color: white
     */
    BOOK,

    /**
     * Color: green
     */
    CAT,

    /**
     * Color: dark blue
     */
    FRAME,

    /**
     * Color: yellow
     */
    GAME,

    /**
     * Color: pink
     */
    PLANT,

    /**
     * Color: light blue
     */
    TROPHY
}