package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.cli.Console;

public class PersonalGoalCardPrinter {
    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    /**
     * Prints the given personal goal card.
     *
     * @param personalGoalCard personal goal card that needs printing
     */
    public static void print(PersonalGoalCard personalGoalCard) {
        int verticalGuideNumber = 0;

        Console.out("    0  1  2  3  4\n");
        for (int i = 0; i < rows; i++) {

            Console.out(verticalGuideNumber);
            Console.out("  ");

            for (int j = 0; j < cols; j++) {

                if (personalGoalCard.getShelfPointMatrix()[i][j] != null) {
                    String tileText = TilePrinter.print(personalGoalCard.getShelfPointMatrix()[i][j]);
                    Console.out(" " + tileText + " ");
                } else {
                    Console.out("   ");
                }
            }

            Console.out("\n");
            verticalGuideNumber++;
        }
    }
}
