package it.polimi.ingsw.ui.game.cli.parser;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.game.cli.printer.TilePrinter;

import java.util.List;
import java.util.Scanner;

public class ColumnParser {
    private static final Scanner scanner = new Scanner(System.in);

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    /**
     * @param bookshelf     current player's bookshelf
     * @param selectedTiles the tiles the player wants to insert in their bookshelf
     * @return the column the player chose
     */
    public static int scan(Tile[][] bookshelf, List<Tile> selectedTiles) {
        while (true) {
            System.out.println();
            System.out.print("Select the column for insertion >");
            System.out.flush();

            selectedTiles.forEach(tile -> {
                String tileText = TilePrinter.print(tile);
                System.out.print(" " + tileText);
                System.out.flush();

            });

            System.out.println();

            int column = -1;

            try {
                column = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {}

            if (column < 0 || column > cols) {
                System.out.print(Chalk.on("Selected column out of bounds, choose a number between 0 and 4.").bgYellow().toString());
                System.out.flush();
            } else if (!itFits(bookshelf, selectedTiles.size(), column)) {
                System.out.print(Chalk.on("Selected column does not have enough space to house your selection.").bgYellow().toString());
                System.out.flush();
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


