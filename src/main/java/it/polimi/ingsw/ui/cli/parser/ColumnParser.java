package it.polimi.ingsw.ui.cli.parser;

import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.cli.Console;

public class ColumnParser {
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    public static int scan() {
        while (true) {
            Console.out("In which column would you like to insert the selected tiles?");

            int column = Integer.parseInt(Console.in());

            if (column < 0 && column > cols) {
                Console.out("The column you selected is out of bounds, choose a number between 0 and 4.");
            } else {
                return column;
            }
        }
    }
}
