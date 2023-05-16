package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.cli.Console;

public class PlayersListPrinter {

    /**
     * Prints the list of players playing the given game.
     *
     * @param game from which we need to get the players
     */
    public static void print(Game game) {

        Console.out("List of players with their acquired tokens:");
        for (int i = 0, player = 1; i < game.getGameMode().maxPlayerAmount(); i++, player++) {
            Console.out("\n  Player " +
                    game.getSessions().getByNumber(PlayerNumber.fromInt(player)).getUsername() + "\n");
            Console.out("  Tokens: " +
                    game.getSessions().getByNumber(PlayerNumber.fromInt(player)).getAcquiredTokens() + "\n");
        }
    }
}
