package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.game.cli.Console;
import it.polimi.ingsw.utils.model.TurnHelper;

public class PlayersListPrinter {

    /**
     * Prints the list of players playing the given game.
     *
     * @param game from which we need to get the players
     */
    public static void print(GameModel game) {

        Console.out("List of players with their acquired tokens:");
        PlayerNumber currentPlayer = game.getCurrentPlayerSession().getPlayerNumber();

        for (int i = 0; i < game.getPlayerCount(); i++) {
            Console.outln();
            Console.out("  Player " +
                    game.getSessions().getByNumber(currentPlayer).getUsername());
            Console.outln();
            Console.out("  Tokens: " +
                    game.getSessions().getByNumber(currentPlayer).getAcquiredTokens());
            Console.outln();

            currentPlayer = TurnHelper.getNextPlayerNumber(currentPlayer, game.getGameMode());
        }
    }
}
