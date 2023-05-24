package it.polimi.ingsw.ui.game.cli;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.cli.parser.ColumnParser;
import it.polimi.ingsw.ui.game.cli.parser.CoordinatesParser;
import it.polimi.ingsw.ui.game.cli.parser.PlayerTilesOrderInsertionParser;
import it.polimi.ingsw.ui.game.cli.printer.BoardPrinter;
import it.polimi.ingsw.ui.game.cli.printer.BookshelvesPrinter;
import it.polimi.ingsw.ui.game.cli.printer.CommonGoalCardsPrinter;
import it.polimi.ingsw.ui.game.cli.printer.PersonalGoalCardPrinter;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameStatus.LAST_ROUND;
import static it.polimi.ingsw.model.game.GameStatus.RUNNING;

public class CliApp implements GameGateway {

    private static final Logger logger = LoggerFactory.getLogger(CliApp.class);

    public Game model;

    public GameViewEventHandler handler;

    public final String owner;

    public CliApp(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;
    }

    /**
     * Calls model's onGameStarted and notify user that the game is running
     */
    @Override
    public void onGameCreated() {
        if (model == null) {
            return;
        }

        Console.out("Hi " + owner + "! Game has started, Enjoy the game and good luck!\n");

        printGameModel();
    }

    /**
     * Updates model's instance in order to show users an updated model every turn
     *
     * @param game model is passed to the function on order to update always the same model
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
        printGameModel();
    }

    /**
     * Shows users' Bookshelves, updates Board, Common goal cards and Tokens, First Player and Current Player, Private
     * goal card
     */
    public void printGameModel() {
        if (model == null) {
            Console.out("Void model.");
            return;
        }

        if (model.getGameStatus() == RUNNING || (model.getGameStatus() == LAST_ROUND)) {

            Console.printnl();
            BoardPrinter.print(model.getBoard());

            CommonGoalCardsPrinter.print(model.getCommonGoalCards());
            Console.printnl(2);

            if (model.getSessions().getByUsername(owner) != null) {
                Console.out("Your personal goal card:\n");

                PersonalGoalCardPrinter.print(model.getSessions().getByUsername(owner).getPersonalGoalCard());
                Console.printnl();
            }

            BookshelvesPrinter.print(model);
            Console.printnl();
        }
    }

    /**
     * Calls model's onPlayerSelectionPhase in order have the current player selecting up to three tiles.
     */
    @Override
    public void gameSelection() {
        printGameModel();

        Set<Coordinate> validCoordinates = CoordinatesParser.scan(model);

        handler.onViewSelection(validCoordinates);
    }

    /**
     * Inserts selected tiles inside current player's own Bookshelf, in the column they chose.
     */
    @Override
    public void gameInsertion() {
        int tilesSize = model.getCurrentPlayerSession().getPlayerTileSelection().getSelectedTiles().size();

        List<Tile> selectedTiles = model.getCurrentPlayerSession().getPlayerTileSelection().getSelectedTiles();

        int column = ColumnParser.scan(model.getCurrentPlayerSession().getBookshelf().getShelfMatrix(), tilesSize);

        List<Tile> orderedTiles;

        if (tilesSize > 1) {
            orderedTiles = PlayerTilesOrderInsertionParser.scan(selectedTiles);
        } else {
            orderedTiles = selectedTiles;
        }

        handler.onViewInsertion(column, orderedTiles);
    }

    /**
     * Shows ranking and announces the winner.
     */
    @Override
    public void onGameEnded() {
        Console.out("""
                The game has ended.
                Here's the player's ranking with their points:
                """);

        List<Pair<PlayerNumber, Integer>> playersRanking = model.calculateRanking();

        playersRanking.forEach(System.out::println);
    }

    public void setHandler(GameViewEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        // app start
    }
}
