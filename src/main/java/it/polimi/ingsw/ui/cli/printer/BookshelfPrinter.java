package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.cli.Console;

public class BookshelfPrinter {

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    public static String print(Bookshelf bookshelf) {
        StringBuilder builder = new StringBuilder();
        int verticalGuideNumber = 0;

        Console.out(" 0  1  2  3  4\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                
            }
        }
    }

}
