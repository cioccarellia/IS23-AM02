package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;

/**
 * Class responsible for assigning a name to a specific common goal card,
 * that is then used in the enemy label
 */
public class CommonGoalCardNameRender {

    public static String renderCommonGoalCardName(CommonGoalCardIdentifier id) {
        switch (id) {
            case SIX_PAIRS -> {
                return "Six pairs";
            }
            case DIAGONAL -> {
                return "Diagonal";
            }
            case FOUR_GROUP_FOUR -> {
                return "Four groups of four tiles";
            }
            case FOUR_MAX3DIFF_LINES -> {
                return "Four lines with max 3 tile types";
            }
            case FOUR_CORNERS -> {
                return "Four corners";
            }
            case TWO_DIFF_COLUMNS -> {
                return "Two columns of all distinct tiles";
            }
            case TWO_SQUARES -> {
                return "Two squares";
            }
            case TWO_DIFF_LINES -> {
                return "Two lines of all distinct tiles";
            }
            case THREE_MAX3DIFF_COLUMNS -> {
                return "Three columns with max 3 tile types";
            }
            case X_TILES -> {
                return "Tiles of the same type forming an X";
            }
            case EIGHT_TILES -> {
                return "Eight tiles of all the same type";
            }
            case STAIRS -> {
                return "Stairs";
            }
            default -> throw new IllegalStateException("Unexpected value: " + id);
        }
    }
}
