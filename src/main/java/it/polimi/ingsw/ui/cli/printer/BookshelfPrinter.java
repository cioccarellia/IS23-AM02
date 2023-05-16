package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.cli.Console;

import static it.polimi.ingsw.model.board.Tile.CAT;

public class BookshelfPrinter {

    public static final Tile[][] BOOKSHELF_MATRIX = {
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT}

    };
    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    public static void print(Bookshelf bookshelf) {
        int verticalGuideNumber = 0;

        Console.out("    0  1  2  3  4\n");
        for (int i = 0; i < rows; i++) {

            Console.out(verticalGuideNumber);
            Console.out("  ");

            for (int j = 0; j < cols; j++) {

                if (bookshelf.getShelfMatrix()[i][j] != null) {
                    String tileText = TilePrinter.print(bookshelf.getShelfMatrix()[i][j]);

                    Console.out(" " + tileText + " ");
                } else {
                    Console.out("   ");
                }
            }

            Console.out("\n");
            verticalGuideNumber++;
        }
    }

    public static void main(String[] args) {
        var bookshelf = new Bookshelf();

        bookshelf.fillUpBookShelf(BOOKSHELF_MATRIX);
        print(bookshelf);

        System.out.flush();
    }

}
