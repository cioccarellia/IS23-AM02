package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.game.cli.Console;

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

    /**
     * Prints the given bookshelf.
     *
     * @param bookshelf bookshelf that needs printing
     */
    public static void print(Bookshelf bookshelf) {

        Console.out("    0  1  2  3  4");
        Console.printnl();
        for (int i = 0; i < rows; i++) {

            Console.out(i);
            Console.out("  ");

            for (int j = 0; j < cols; j++) {

                if (bookshelf.getShelfMatrix()[i][j] != null) {
                    String tileText = TilePrinter.print(bookshelf.getShelfMatrix()[i][j]);

                    Console.out(" " + tileText + " ");
                } else {
                    Console.out("   ");
                }
            }

            Console.printnl();
        }
    }

    public static void main(String[] args) {

        Bookshelf bookshelf = new Bookshelf();

        bookshelf.fillUpBookShelf(BOOKSHELF_MATRIX);
        print(bookshelf);

        System.out.flush();
    }

}
