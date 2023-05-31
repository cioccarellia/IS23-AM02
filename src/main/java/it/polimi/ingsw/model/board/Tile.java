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
     * Color: yellow
     */
    GAME,

    /**
     * Color: light blue
     */
    TROPHY,

    /**
     * Color: pink
     */
    PLANT,

    /**
     * Color: dark blue
     */
    FRAME
}