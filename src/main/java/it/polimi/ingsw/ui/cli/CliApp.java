package it.polimi.ingsw.ui.cli;

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
import it.polimi.ingsw.ui.cli.printer.*;
import it.polimi.ingsw.utils.model.TurnHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CliApp implements UiGateway {

    private final boolean hasReceivedInitialModel = false;
    public Game model;
    private ViewEventHandler handler;

    public CliApp() {
    }

    public static void main(String[] args) {
        CliApp app = new CliApp();
        Game game = new Game(GameMode.GAME_MODE_4_PLAYERS);
        TurnHelper turn = new TurnHelper();
        PlayerNumber playerNumber;
        GameController controller = new GameController();

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
            PlayerNumber nextPlayer = turn.getNextPlayerNumber(game.getCurrentPlayer().getPlayerNumber(), game.getGameMode());
            game.onNextTurn(game.getSessions().getByNumber(nextPlayer).getUsername());
            app.modelUpdate(game);

        }

        app.onGameEnded();

    }

    /**
     * Calls model's onGameStarted and notify user that the game is running
     */
    @Override
    public void onGameStarted() {
        model.onGameStarted();
        Console.out("Game has started, Good Luck!\n");
    }

    /**
     * Updates model's instance in order to show users an updated model every turn
     *
     * @param game
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
    }

    /**
     * Shows users' Bookshelves, updates Board, Common goal cards and Tokens, First Player and Current Player, Private
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
        //TODO we need to print the player's card for whom is asking for their private goal card, not the current player. Others can't see other player's card
        PersonalGoalCardPrinter.print(model.getCurrentPlayer().getPersonalGoalCard());
        Console.out("\n");

        for (int i = 0, player = 1; i < model.getPlayerNumber(); i++, player++) {
            //TODO when we fix nextPlayer we can use that to change player and substitute int player with it
            Console.out("\nBookshelf for player " +
                    model.getSessions().getByNumber(PlayerNumber.fromInt(player)).getUsername() + ":\n");

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
        Set<Coordinate> validCoordinates = CoordinatesParser.scan(model);
        model.onPlayerSelectionPhase(validCoordinates);

    }

    /**
     * Insert selected tiles inside current player's own Bookshelf
     */
    @Override
    public void gameInsertion() {
        int tilesSize = model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles().size();
        int column = ColumnParser.scan(model.getGameMatrix(), tilesSize);
        List<Tile> orderedTiles = new ArrayList<>();
        if(tilesSize > 1) {
            orderedTiles = PlayerTilesOrderInsertionParser
                    .scan(model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles());
        }
        model.onPlayerInsertionPhase(column, orderedTiles);

        model.onPlayerCheckingPhase();
    }

    /**
     * show score classification and announce the winner
     */
    @Override
    public void onGameEnded() {
        Console.out("""
                The game has ended. Congratulations to the winner!
                Here's the player's ranking with their points:
                """);
        model.onGameEnded();
    }

    public void setHandler(ViewEventHandler handler) {
        this.handler = handler;
    }

}
