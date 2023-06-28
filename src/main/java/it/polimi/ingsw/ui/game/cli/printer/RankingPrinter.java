package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.ui.game.cli.Console;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class RankingPrinter {

    public static void print(List<PlayerScore> playerScoreList) {
        Console.out("Player                |Token points   |Bookshelf points   |Personal goal card points   |Total points   |");
        Console.outln();

        for (PlayerScore currentScore : playerScoreList) {
            String username = currentScore.username();
            String tokenPoints = String.valueOf(currentScore.getTokenPoints());
            String bookshelfPoints = String.valueOf(currentScore.getBookshelfPoints());
            String personalGCPoints = String.valueOf(currentScore.getPersonalGoalCardsPoints());
            String totalPoints = String.valueOf(currentScore.total());

            // print i-player username
            Console.out(StringUtils.rightPad(username, 22, ""));
            Console.out("|");

            // print i-player token points
            Console.out(StringUtils.rightPad(tokenPoints, 15, ""));
            Console.out("|");

            // print i-player bookshelf points
            Console.out(StringUtils.rightPad(bookshelfPoints, 19, ""));
            Console.out("|");

            // print i-player personal goal card points
            Console.out(StringUtils.rightPad(personalGCPoints, 28, ""));
            Console.out("|");

            // print i-player total points
            Console.out(StringUtils.rightPad(totalPoints, 15, ""));
            Console.out("|");

            Console.outln();
        }

        Console.outln();
    }
}