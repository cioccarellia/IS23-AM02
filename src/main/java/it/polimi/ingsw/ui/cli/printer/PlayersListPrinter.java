package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.player.PlayerSession;

public class PlayersListPrinter {
    public static String print(PlayerSession playerSession)
    {
        return playerSession.getUsername();
    }

}
