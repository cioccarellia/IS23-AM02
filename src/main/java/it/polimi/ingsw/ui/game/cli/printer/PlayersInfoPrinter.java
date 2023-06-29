package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.model.player.PlayerSession;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class PlayersInfoPrinter {
    public static void print(List<PlayerSession> playersSessions, List<PlayerInfo> playerInfo, List<PlayerScore> scores) {
        System.out.print("List of players:");
        System.out.println();
        System.out.print("Player                |Game phase   |Total points   |");
        if (playerInfo != null) {
            System.out.print("Role   |Connection   |");
        }
        System.out.println();

        for (PlayerSession session : playersSessions) {
            StringBuilder text = new StringBuilder();
            String username = session.getUsername();

            String gamePhase = session.getPlayerCurrentGamePhase().toString().toLowerCase();
            String points = String.valueOf(scores.stream().filter(s -> s.username().equals(username)).findAny().get().total());

            text.append(StringUtils.rightPad(username, 22, "")).append("|");
            text.append(StringUtils.rightPad(gamePhase, 13, "")).append("|");
            text.append(StringUtils.rightPad(points, 15, "")).append("|");

            if (playerInfo != null) {
                PlayerInfo info = playerInfo.stream().filter(player -> player.username().equals(username)).findAny().get();

                String role = (info.isHost() ? "Host" : "Player");
                String connection = info.status().toString().toLowerCase();

                text.append(StringUtils.rightPad(role, 7, "")).append("|");
                text.append(StringUtils.rightPad(connection, 13, "")).append("|");
            }

            System.out.print(text);
            System.out.println();
        }
    }
}
