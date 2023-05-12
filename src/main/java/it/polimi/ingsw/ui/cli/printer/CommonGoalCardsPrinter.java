package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;

public class CommonGoalCardsPrinter {
    public static String print(CommonGoalCard card) {
        switch (card.getId()) {

            case SIX_PAIRS -> {
                return "Six groups each containing at least 2 tiles of the same type. " +
                        " The tiles of one group can be different from those of another group.";
            }
            case DIAGONAL -> {
                return "Five tiles of the same type forming a diagonal";
            }
            case FOUR_GROUP_FOUR -> {
                return "Four groups each containing at least 4 tiles of the same type. " +
                        "The tiles of one group can be different from those of another group.";
            }
            case FOUR_MAX3DIFF_LINES -> {
                return "Four lines each formed by 5 tiles of maximum three different types. " +
                        "One line can show the same or a different combination of another line.";
            }
            case FOUR_CORNERS -> {
                return "Four tiles of the same type in the four corners of the bookshelf.";
            }
            case TWO_DIFF_COLUMNS -> {
                return "Two columns each formed by 6 different types of tiles.";
            }
            case TWO_SQUARES -> {
                return "Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles " +
                        "of one square can be different from those of the other square.";
            }
            case TWO_DIFF_LINES -> {
                return "Two lines each formed by 5 different types of tiles. One line can show the " +
                        "same or a different combination of the other line.";
            }
            case THREE_MAX3DIFF_COLUMNS -> {
                return "Three columns each formed by 6 tiles of maximum three different types. One column can show the" +
                        " same or a different combination of another column.";
            }
            case X_TILES -> {
                return "Five tiles of the same type forming an X";
            }
            case EIGHT_TILES -> {
                return "Eight tiles of the same type. There’s no restriction about the position of these " +
                        "tiles.";
            }
            case STAIRS -> {
                return "Five columns of increasing or decreasing height. Starting from the first column on " +
                        "the left or on the right, each next column must be made of exactly o";
            }
            default -> {
                return null;
            }
        }
    }


}
