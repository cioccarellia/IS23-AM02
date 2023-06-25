package it.polimi.ingsw.ui.game.cli.parser;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.game.cli.Console;
import it.polimi.ingsw.ui.game.cli.printer.TilePrinter;

import java.util.List;

public class ColumnParser {
    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    /**
     * @param bookshelf     current player's bookshelf
     * @param selectedTiles the tiles the player wants to insert in their bookshelf
     * @return the column the player chose
     */
    public static int scan(Tile[][] bookshelf, List<Tile> selectedTiles) {
        while (true) {
            Console.outln();
            Console.out("Select the column for insertion >");

            selectedTiles.forEach(tile -> {
                String tileText = TilePrinter.print(tile);
                Console.out(" " + tileText);
            });

            Console.outln();

            int column = -1;

            try {
                column = Integer.parseInt(Console.in());
            } catch (NumberFormatException ignored) {}

            if (column < 0 || column > cols) {
                Console.out(Chalk.on("Selected column out of bounds, choose a number between 0 and 4.").bgYellow().toString());
            } else if (!itFits(bookshelf, selectedTiles.size(), column)) {
                Console.out(Chalk.on("Selected column does not have enough space to house your selection.").bgYellow().toString());
            } else {
                return column;
            }
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


