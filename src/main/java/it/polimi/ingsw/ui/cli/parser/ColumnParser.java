package it.polimi.ingsw.ui.cli.parser;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.cli.Console;

public class ColumnParser {
    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    public static int scan(Tile[][] bookshelf, int tilesAmount) {
        while (true) {
            Console.out("\nIn which column would you like to insert the selected tiles?\n ");
            Console.flush();

            int column = Integer.parseInt(Console.in());

            if (column <= 0 || column >= cols)
                Console.out("\nThe column you selected is out of bounds, choose a number between 0 and 4.\n ");
            else if (!itFits(bookshelf, tilesAmount, column))
                Console.out("\nYou selected more tiles than there is space in this column. Change column.");
            else
                return column;
        }
    }

    public static boolean itFits(Tile[][] bookshelf, int tilesAmount, int column) {
        int i = rows - 1;
        while (bookshelf[i][column] != null && i > 0) {
            i--;
        }

        return i + 1 >= tilesAmount;
    }
}


