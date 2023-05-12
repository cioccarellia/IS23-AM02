package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.player.PlayerSession;

public class PlayerTokensPrinter {
    private static String print(PlayerSession playerSession){
        return playerSession.getAcquiredTokens().toString();
    }
}
