package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameStatus;
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
import it.polimi.ingsw.utils.model.TurnHelper;

import java.util.List;
import java.util.Set;

public class CliApp implements UiGateway {

    private ViewEventHandler handler;
    public Game model;
    private final boolean hasReceivedInitialModel = false;

    public CliApp() {
    }

    @Override
    public void onGameStarted() {
        model.onGameStarted();
        Console.out("Game started, Good Luck!\n");
    }

    @Override
    public void modelUpdate(Game game)
    {
        this.model = game;
    }

    public void printGameModel() {
        Console.out("Board: \n");

        BoardPrinter.print(model.getBoard());

        Console.out("First common goal card:\n");

        CommonGoalCardsPrinter.print(model.getCommonGoalCardsStatus().get(0));
        Console.out("\n");

        Console.out("Second common goal card:\n");
        CommonGoalCardsPrinter.print(model.getCommonGoalCardsStatus().get(1));

        for (int i = 0, player = 1; i < model.getPlayerNumber(); i++, player++) {

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
        printGameModel();
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

    public void setHandler(ViewEventHandler handler) {this.handler = handler;}


    public static void main(String[] args) {
        var app = new CliApp();
        var game = new Game(GameMode.GAME_MODE_4_PLAYERS);
        var turn = new TurnHelper();
        PlayerNumber playerNumber;
        var controller = new GameController();

        game.addPlayer("Alberto");
        game.addPlayer("Cookie");
        game.addPlayer("Giulia");
        game.addPlayer("Marco");

        app.modelUpdate(game);
        app.onGameStarted();


        while (game.getGameStatus() != GameStatus.ENDED) {

            app.gameSelection();
            app.modelUpdate(game);
            app.gameInsertion();
            app.modelUpdate(game);
            //TODO implement a better turn manager
            //game.onNextTurn(game.getCurrentPlayer().getPlayerNumber().next(game.getGameMode()).name());
            /*playerNumber = turn.getNextPlayerNumber(game.getCurrentPlayer(),game.getGameMode());
            game.getCurrentPlayer().getPlayerNumber() = playerNumber; */
            game.getCurrentPlayer().getPlayerNumber().next(game.getCurrentPlayer().getPlayerNumber());
            //TODO model update doesn't work
            app.modelUpdate(game);
        }

        app.onGameEnded();

    }

}
