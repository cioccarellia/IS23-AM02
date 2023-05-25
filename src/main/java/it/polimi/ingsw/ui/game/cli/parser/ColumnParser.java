package it.polimi.ingsw.ui.game.cli.parser;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.game.cli.Console;

public class ColumnParser {
    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    /**
     * @param bookshelf   current player's bookshelf
     * @param tilesAmount how many tiles the player wants to insert in their bookshelf
     * @return the column the player chose
     */
    public static int scan(Tile[][] bookshelf, int tilesAmount) {
        while (true) {
            Console.outln();
            Console.out("In which column would you like to insert the selected tiles?");
            Console.outln();

            int column = Integer.parseInt(Console.in());

            if (column < 0 || column > cols) {
                Console.outln();
                Console.out("The column you selected is out of bounds, choose a number between 0 and 4.");
                Console.outln();
            } else if (!itFits(bookshelf, tilesAmount, column)) {
                Console.outln();
                Console.out("You selected more tiles than there is space in this column. Change column.");
            } else
                return column;
        }
    }

    /**
     * @param bookshelf   the player's bookshelf
     * @param tilesAmount how many tiles the player wants to insert in their bookshelf
     * @param column      the column the player wants to put their tiles in
     * @return if the tiles would fit in the selected column
     */
    public static boolean itFits(Tile[][] bookshelf, int tilesAmount, int column) {
        int i = rows - 1;
        while (bookshelf[i][column] != null && i > 0) {
            i--;
        }

        return i + 1 >= tilesAmount;
    }
}


