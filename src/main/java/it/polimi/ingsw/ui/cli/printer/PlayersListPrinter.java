package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.cli.Console;
import it.polimi.ingsw.utils.model.TurnHelper;

public class PlayersListPrinter {

    /**
     * Prints the list of players playing the given game.
     *
     * @param game from which we need to get the players
     */
    public static void print(Game game) {

        Console.out("List of players with their acquired tokens:");
        PlayerNumber currentPlayer = game.getCurrentPlayer().getPlayerNumber();

        for (int i = 0; i < game.getPlayersCurrentAmount(); i++) {
            Console.printnl();
            Console.out("  Player " +
                    game.getSessions().getByNumber(currentPlayer).getUsername());
            Console.printnl();
            Console.out("  Tokens: " +
                    game.getSessions().getByNumber(currentPlayer).getAcquiredTokens());
            Console.printnl();

            currentPlayer = TurnHelper.getNextPlayerNumber(currentPlayer, game.getGameMode());
        }
    }
}
