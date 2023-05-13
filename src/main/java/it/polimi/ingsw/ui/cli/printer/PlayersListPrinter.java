package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.cli.Console;

public class PlayersListPrinter {
    public static void print(Game model) {

        Console.out("List of players with their acquired tokens:");
        for (int i = 0, player = 1; i < model.getGameMode().maxPlayerAmount(); i++, player++) {
            Console.out("\n  Player " +
                    model.getSessions().getByNumber(PlayerNumber.fromInt(player)).getUsername() + "\n");
            Console.out("  Tokens: " +
                    model.getSessions().getByNumber(PlayerNumber.fromInt(player)).getAcquiredTokens() + "\n");
        }
    }
}
