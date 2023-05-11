package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;

public class CommonGoalCardsPrinter {
    private static String print(CommonGoalCard card){
        switch(card.getId()){

            case SIX_PAIRS -> {
                return "Six groups each containing at least 2 tiles of the same type." +
                        " The tiles of one group can be different from those of another group.";
            }
            case DIAGONAL -> {
                return "Five tiles of the same type forming a diagonal";
            }
            case FOUR_GROUP_FOUR -> {
                return"Four groups each containing at least 4 tiles of the same type." +
                        "The tiles of one group can be different from those of another group.";
            }
            case FOUR_MAX3DIFF_LINES -> {
                return "Four lines each formed by 5 tiles of maximum three different types." +
                        "One line can show the same or a different combination of another line.";
            }
            case FOUR_CORNERS -> {
                return"Four tiles of the same type in the four corners of the bookshelf.";
            }
            case TWO_DIFF_COLUMNS -> {
            }
            case TWO_SQUARES -> {
            }
            case TWO_DIFF_LINES -> {
            }
            case THREE_MAX3DIFF_COLUMNS -> {
            }
            case X_TILES -> {
            }
            case EIGHT_TILES -> {
            }
            case STAIRS -> {
            }
        }
        return null;

    }


}
