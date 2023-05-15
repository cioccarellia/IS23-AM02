package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.parser.ColumnParser;
import it.polimi.ingsw.ui.cli.parser.CoordinatesParser;
import it.polimi.ingsw.ui.cli.parser.PlayerTilesOrderInsertionParser;
import it.polimi.ingsw.ui.cli.printer.*;
import it.polimi.ingsw.utils.model.TurnHelper;

import java.util.List;
import java.util.Set;

public class CliApp implements UiGateway {

    private ViewEventHandler handler;
    public Game model;
    private final boolean hasReceivedInitialModel = false;

    public CliApp() {
    }

    /**
     * call model's onGameStarted and notify user that the game is running
     */
    @Override
    public void onGameStarted() {
        model.onGameStarted();
        Console.out("Game started, Good Luck!\n");
    }

    /**
     * Update model's istance in order to show users an updated model every turn
     * @param game
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
    }

    /**
     * show user's Bookshelves, updated Board, Common goal cards and Tokens, First Player and Current Player, Private
     * goal card
     */
    public void printGameModel() {
        Console.out("Board: \n");

        BoardPrinter.print(model.getBoard());

        Console.out("First common goal card:\n");

        CommonGoalCardsPrinter.print(model.getCommonGoalCardsStatus().get(0));
        Console.out("\n");

        Console.out("Second common goal card:\n");
        CommonGoalCardsPrinter.print(model.getCommonGoalCardsStatus().get(1));

        Console.out("\nPersonal goal card:\n");
        PersonalGoalCardPrinter.print(model.getCurrentPlayer().getPersonalGoalCard());
        Console.out("\n");

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

    /**
     * call model's onPlayerSelectionPhase in order to make current player selecting up to three tiles
     */
    @Override
    public void gameSelection() {
        printGameModel();
        Set<Coordinate> validCoordinates = CoordinatesParser.scan();
        model.onPlayerSelectionPhase(validCoordinates);

    }

    /**
     * Insert selected tiles inside current player's own Bookshelf
     */
    @Override
    public void gameInsertion() {
        int column = ColumnParser.scan();

        List<Tile> orderedTiles = PlayerTilesOrderInsertionParser
                .scan(model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles());
        model.onPlayerInsertionPhase(column, orderedTiles);

        model.onPlayerCheckingPhase();
    }

    /**
     * show score classification and announce the winner
     */
    @Override
    public void onGameEnded() {
        Console.out("The game has ended. Congratulations to the winner!\n" +
                "Here's the player's ranking with their points:");
        model.onGameEnded();
    }

    public void setHandler(ViewEventHandler handler) {
        this.handler = handler;
    }


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

            turn.getNextPlayerNumber(game.getCurrentPlayer().getPlayerNumber(), game.getGameMode());
            //TODO model update doesn't work
            app.modelUpdate(game);
        }

        app.onGameEnded();

    }

}
