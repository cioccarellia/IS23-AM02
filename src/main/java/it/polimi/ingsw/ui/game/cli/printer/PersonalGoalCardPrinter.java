package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;

public class PersonalGoalCardPrinter {
    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    /**
     * Prints the given personal goal card.
     *
     * @param personalGoalCard personal goal card that needs printing
     */
    public static void print(PersonalGoalCard personalGoalCard) {
        System.out.print("Your personal goal card:\n");
        System.out.flush();

        System.out.print("    0  1  2  3  4");
        System.out.flush();
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + "  ");
            System.out.flush();

            for (int j = 0; j < cols; j++) {

                if (personalGoalCard.getShelfPointMatrix()[i][j] != null) {
                    String tileText = TilePrinter.print(personalGoalCard.getShelfPointMatrix()[i][j]);
                    System.out.print(" " + tileText + " ");
                    System.out.flush();
                } else {
                    System.out.print("   ");
                    System.out.flush();
                }
            }

            System.out.println();
        }
    }
}
