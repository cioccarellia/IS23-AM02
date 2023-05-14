package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.parser.ColumnParser;
import it.polimi.ingsw.ui.cli.parser.CoordinatesParser;
import it.polimi.ingsw.ui.cli.parser.PlayerTilesOrderInsertionParser;
import it.polimi.ingsw.ui.cli.printer.BoardPrinter;
import it.polimi.ingsw.ui.cli.printer.BookshelfPrinter;
import it.polimi.ingsw.ui.cli.printer.CommonGoalCardsPrinter;
import it.polimi.ingsw.ui.cli.printer.PlayersListPrinter;

import java.util.List;
import java.util.Set;

public class CliApp implements UiGateway {

    final private ViewEventHandler handler;
    private Game model;
    private boolean hasReceivedInitialModel = false;

    public CliApp(ViewEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onGameStarted() {
    }

    @Override
    public void modelUpdate(Game game) {
        model = game;
    }

    public void printGameModel() {
        Console.out("Board: \n");

        BoardPrinter.print(model.getBoard());

        Console.out("First common goal card:\n");
        CommonGoalCardsPrinter.print(model.getCommonGoalCardsStatus().get(0));
        Console.out("\n");
        Console.out("Second common goal card:\n");
        CommonGoalCardsPrinter.print(model.getCommonGoalCardsStatus().get(1));


        for (int i = 0, player = 1; i < model.getGameMode().maxPlayerAmount(); i++, player++) {

            Console.out("\nBookshelf for player " +
                    model.getSessions().getByNumber(PlayerNumber.fromInt(player)).getUsername() + ": \n");

            BookshelfPrinter.print(model.getPlayerSession(model.getSessions().getByNumber(PlayerNumber.fromInt(player))
                            .getUsername()).getBookshelf());
        }

        Console.out("\nThe first player is: " +
                model.getSessions().getByNumber(model.getStartingPlayerNumber()).getUsername() + "\n");

        Console.out("\nThe current player is: " + model.getCurrentPlayer().getUsername() + "\n");
        Console.out("\n");
        PlayersListPrinter.print(model);

    }


    @Override
    public void gameSelection() {

        Set<Coordinate> validCoordinates = CoordinatesParser.scan();
        model.onPlayerSelectionPhase(validCoordinates);

    }


    @Override
    public void gameInsertion() {
        int column = ColumnParser.scan();

        List<Tile> orderedTiles = PlayerTilesOrderInsertionParser
                .scan(model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles());
        model.onPlayerInsertionPhase(column, orderedTiles);

        model.onPlayerCheckingPhase();
    }


    @Override
    public void onGameEnded() {
        Console.out("The game has ended. Congratulations to the winner!\n" +
                "Here's the player's ranking with their points:");
        model.onGameEnded();
    }

}
