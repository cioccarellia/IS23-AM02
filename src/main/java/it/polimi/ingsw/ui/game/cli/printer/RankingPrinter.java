package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.game.score.PlayerScore;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class RankingPrinter {

    public static void print(List<PlayerScore> playerScoreList) {
        System.out.print("Player                |Token points   |Bookshelf points   |Personal goal card points   |Total points   |");
        System.out.println();

        for (PlayerScore currentScore : playerScoreList) {
            String username = currentScore.username();
            String tokenPoints = String.valueOf(currentScore.getTokenPoints());
            String bookshelfPoints = String.valueOf(currentScore.getBookshelfPoints());
            String personalGCPoints = String.valueOf(currentScore.getPersonalGoalCardsPoints());
            String totalPoints = String.valueOf(currentScore.total());

            // print i-player username
            System.out.print(StringUtils.rightPad(username, 22, "") + "|");

            // print i-player token points
            System.out.print(StringUtils.rightPad(tokenPoints, 15, "") + "|");

            // print i-player bookshelf points
            System.out.print(StringUtils.rightPad(bookshelfPoints, 19, "") + "|");


            // print i-player personal goal card points
            System.out.print(StringUtils.rightPad(personalGCPoints, 28, "") + "|");


            // print i-player total points
            System.out.print(StringUtils.rightPad(totalPoints, 15, "") + "|");

            System.out.println();
        }

        System.out.println();
        System.out.flush();
    }
}