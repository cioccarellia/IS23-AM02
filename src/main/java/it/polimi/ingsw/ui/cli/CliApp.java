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
import it.polimi.ingsw.ui.cli.printer.BookshelvesPrinter;
import it.polimi.ingsw.ui.cli.printer.CommonGoalCardsPrinter;
import it.polimi.ingsw.ui.cli.printer.PersonalGoalCardPrinter;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static it.polimi.ingsw.model.game.GameStatus.ENDED;
import static it.polimi.ingsw.utils.model.TurnHelper.getNextPlayerNumber;

public class CliApp implements UiGateway {

    private static final Logger logger = LoggerFactory.getLogger(CliApp.class);

    public Game model;

    private ViewEventHandler handler;

    public CliApp(Game model, ViewEventHandler handler) {
        this.model = model;
        this.handler = handler;
    }

    public static void main(String[] args) {
        CliApp app = new CliApp();
        Game game = new Game(GAME_MODE_2_PLAYERS);

        game.addPlayer("Cookie");
        game.addPlayer("Marco");

        app.modelUpdate(game);
        app.onGameCreated();

        while (game.getGameStatus() != ENDED) {

            app.gameSelection();
            app.modelUpdate(game);

            app.gameInsertion();
            app.modelUpdate(game);

            app.gameChecking();
            app.modelUpdate(game);

            app.gameNextTurn();
            app.modelUpdate(game);
        }

        app.onGameEnded();
    }

    /**
     * Calls model's onGameStarted and notify user that the game is running
     */
    @Override
    public void onGameCreated() {
        if (model == null) {
            return;
        }
        model.onGameStarted();
        Console.out("MY SHELFIE \n");
        Console.out("Game has started, Enjoy the game and good luck!\n");
    }

    /**
     * Updates model's instance in order to show users an updated model every turn
     *
     * @param game model is passed to the function on order to update always the same model
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
        //ConsoleClear.clearConsole();
        // Console.printnl();
        // Console.out("Board:");
        Console.printnl();
        BoardPrinter.print(model.getBoard());

        CommonGoalCardsPrinter.print(model.getCommonGoalCards());
        Console.printnl();

        Console.printnl();
        Console.out("Personal goal card for player " +
                model.getSessions().getByNumber(model.getCurrentPlayer().getPlayerNumber()).getUsername() + ":\n");
        Console.flush();
        //TODO printing the current player's name is temporary, once fixed remove
        //TODO we need to print the player's card for whom is asking for their private goal card, not the current player. Others can't see other player's card
        PersonalGoalCardPrinter.print(model.getCurrentPlayer().getPersonalGoalCard());
        Console.printnl();
        BookshelvesPrinter.print(model);
        Console.printnl();
    }

    /**
     * Calls model's onPlayerSelectionPhase in order have the current player selecting up to three tiles.
     */
    @Override
    public void gameSelection() {
        printGameModel();
        Set<Coordinate> validCoordinates = CoordinatesParser.scan(model);
        model.onPlayerSelectionPhase(validCoordinates);
    }

    /**
     * Inserts selected tiles inside current player's own Bookshelf, in the column they chose.
     */
    @Override
    public void gameInsertion() {
        int tilesSize = model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles().size();
        List<Tile> selectedTiles = model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles();
        int column = ColumnParser.scan(model.getCurrentPlayer().getBookshelf().getShelfMatrix(), tilesSize);
        List<Tile> orderedTiles;

        if (tilesSize > 1) {
            orderedTiles = PlayerTilesOrderInsertionParser
                    .scan(selectedTiles);
        } else
            orderedTiles = selectedTiles;

        model.onPlayerInsertionPhase(column, orderedTiles);
    }

    public void gameChecking() {
        model.onPlayerCheckingPhase();
    }

    public void gameNextTurn() {
        model.onNextTurn(model.getSessions().getByNumber(getNextPlayerNumber(model.getCurrentPlayer().getPlayerNumber(),
                model.getGameMode())).getUsername());
    }

    /**
     * Shows ranking and announces the winner.
     */
    @Override
    public void onGameEnded() {
        Console.out("""
                The game has ended. Congratulations to the winner!
                Here's the player's ranking with their points:
                """);

        List<Pair<PlayerNumber, Integer>> playersRanking = model.onGameEnded();

        playersRanking.forEach(System.out::println);
    }

    public void setHandler(ViewEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        // app start
    }
}
