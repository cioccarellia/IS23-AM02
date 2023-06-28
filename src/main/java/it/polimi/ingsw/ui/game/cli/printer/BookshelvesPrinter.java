package it.polimi.ingsw.ui.game.cli.printer;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.model.player.PlayerSession;
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
    public static void print(GameModel game) {
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

        // print the bookshelf
        System.out.println();
        System.out.print("Bookshelves:");
        System.out.println();

        for (int i = 0; i < dim; i++) {
            System.out.print("    0  1  2  3  4      ");
        }

        System.out.println();

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < dim; k++) {
                System.out.print(i + "  ");

                for (int j = 0; j < cols; j++) {
                    if (bookshelves.get(k)[i][j] != null) {
                        System.out.print(" " + TilePrinter.print(bookshelves.get(k)[i][j]) + " ");
                    } else
                        System.out.print("   ");
                }

                System.out.print("  |  ");
            }
            System.out.println();
        }

        // print the player's name
        for (int i = 0; i < dim; i++) {
            String username = players.get(i).getUsername();
            if (players.get(i).getPlayerNumber() == game.getStartingPlayerNumber()) {
                System.out.print(Chalk.on("F ").red().toString());
            } else
                System.out.print("  ");

            if (players.get(i).getPlayerNumber() == game.getCurrentPlayerSession().getPlayerNumber()) {
                var x = Chalk.on("@" + username).bgMagenta().toString();
                System.out.print(StringUtils.rightPad(x, 29, " "));
            } else {
                System.out.print(StringUtils.rightPad("@" + username, 19, " "));
            }

            System.out.print("  ");
        }

        System.out.println();

        // print the token
        for (int i = 0; i < dim; i++) {
            System.out.print("  Tokens:");

            var tokens = players.get(i).getAcquiredTokens();

            StringBuilder text = new StringBuilder();

            if (!tokens.isEmpty()) {
                for (int j = 0; j < tokens.size(); j++) {
                    if (j != 0) {
                        text.append(", ");
                    }
                    text.append(tokens.get(i).getPoints());
                }
            }

            System.out.print(StringUtils.rightPad(text.toString(), 14, " "));
        }
        System.out.println();

        // print points
        List<PlayerScore> scores = game.getRankings();

        for (int i = 0; i < dim; i++) {
            System.out.print("  Points:");
            String username = players.get(i).getUsername();

            scores.stream()
                    .filter(s -> s.username().equals(username))
                    .findAny()
                    .ifPresent(totalPoints -> System.out.print(StringUtils.rightPad(String.valueOf(totalPoints.total()), 14, " ")));
        }
        System.out.println();
    }
}
