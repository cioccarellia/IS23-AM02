package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.cli.Console;


import java.util.Arrays;

import static it.polimi.ingsw.model.board.Tile.CAT;

public class BookshelfPrinter {

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    public static final Tile[][] BOOKSHELF_MATRIX = {
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT}

    };

    public static String print(Bookshelf bookshelf) {
        StringBuilder builder = new StringBuilder();
        int verticalGuideNumber = 0;

        Tile[][] matrix = new Tile[rows][cols];
        Arrays.deepEquals(matrix, bookshelf.getShelfMatrix());

        Console.out("X 0  1  2  3  4\n");
        for (int i = 0; i < rows; i++) {

            Console.out(verticalGuideNumber);
            Console.out("  ");

            for (int j = 0; j < cols; j++) {

                if(matrix[i][j] != null)
                {
                    String tileText = TilePrinter.print(matrix[i][j]);

                    Console.out(" " + tileText + " ");
                }
                else
                {
                    Console.out("   ");
                }
            }

            Console.out("\n");
            verticalGuideNumber++;
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        var bookshelf = new Bookshelf();

        bookshelf.fillUpBookShelf(BOOKSHELF_MATRIX);
        print(bookshelf);

        System.out.flush();
    }

}
