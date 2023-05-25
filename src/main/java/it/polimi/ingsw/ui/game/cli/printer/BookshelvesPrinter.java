package it.polimi.ingsw.ui.game.cli.printer;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.game.cli.Console;
import org.apache.commons.lang.StringUtils;

import java.util.List;


public class BookshelvesPrinter {

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();


    /**
     * Prints all the bookshelves of the given game in a line
     *
     * @param game the game being played
     */
    public static void print(Game game) {
        int dim = game.getPlayerCount();

        List<Tile[][]> bookshelves = game
                .getSessions()
                .playerSessions()
                .stream()
                .map(player -> player.getBookshelf().getShelfMatrix())
                .toList();

        List<PlayerSession> players = game.
                getSessions().
                playerSessions().
                stream().
                toList();

        Console.outln();
        Console.out("Bookshelves:");
        Console.outln();

        for (int i = 0; i < dim; i++) {
            Console.out("    0  1  2  3  4      ");
        }

        Console.outln();

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < dim; k++) {
                Console.out(i);
                Console.out("  ");

                for (int j = 0; j < cols; j++) {
                    if (bookshelves.get(k)[i][j] != null) {
                        Console.out(" " + TilePrinter.print(bookshelves.get(k)[i][j]) + " ");
                    } else
                        Console.out("   ");
                }

                Console.out("  |  ");
            }
            Console.outln();
        }

        for (int i = 0; i < dim; i++) {
            String username = players.get(i).getUsername();
            if (players.get(i).getPlayerNumber() == game.getStartingPlayerNumber()) {
                Console.out(Chalk.on("F ").red().toString());
            } else
                Console.out("  ");


            if (players.get(i).getPlayerNumber() == game.getCurrentPlayerSession().getPlayerNumber()) {
                var x = Chalk.on("@" + username).bgMagenta().toString();
                Console.out(StringUtils.rightPad(x, 29, " "));
            } else {
                Console.out(StringUtils.rightPad("@" + username, 19, " "));
            }

            Console.out("  ");
        }

        Console.outln();

        for (int i = 0; i < dim; i++) {
            Console.out("  Tokens:");
            var x = players.get(i).getAcquiredTokens().toString();

            Console.out(StringUtils.rightPad(x, 14, " "));
        }
        Console.outln();
    }
}
