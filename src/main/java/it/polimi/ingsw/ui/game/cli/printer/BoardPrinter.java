package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.ui.game.cli.Console;

import java.util.Optional;

public class BoardPrinter {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    /**
     * Prints the board.
     *
     * @param board board that needs printing
     */
    public static void print(Board board) {
        Console.outln();
        Console.out("   0  1  2  3  4  5  6  7  8 ");
        Console.outln();

        for (int i = 0; i < dimension; i++) {
            Console.out(i);
            Console.out(" ");

            for (int j = 0; j < dimension; j++) {

                Coordinate coordinate = new Coordinate(i, j);
                Optional<Tile> tileOpt = board.getTileAt(coordinate);

                if (tileOpt.isPresent()) {
                    Tile tile = tileOpt.get();
                    String tileText = TilePrinter.print(tile);

                    Console.out(" " + tileText + " ");
                } else {
                    Console.out("   ");
                }

            }
            Console.outln();
        }

        Console.outln();
    }
}
