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

public class CliApp implements UiGateway {

    private static final Logger logger = LoggerFactory.getLogger(CliApp.class);

    public Game model;

    public ViewEventHandler handler;

    public CliApp(Game model, ViewEventHandler handler) {
        this.model = model;
        this.handler = handler;
    }

    /**
     * Calls model's onGameStarted and notify user that the game is running
     */
    @Override
    public void onGameCreated() {
        if (model == null) {
            return;
        }

        Console.out("MY SHELFIE \n");
        Console.out("Game has started, Enjoy the game and good luck!\n");

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
            Console.out("Void model");
            return;
        }

        Console.printnl();
        BoardPrinter.print(model.getBoard());

        CommonGoalCardsPrinter.print(model.getCommonGoalCards());
        Console.printnl(2);

        if (model.getCurrentPlayerSession() != null) {
            Console.out("Personal goal card for player " + model.getCurrentPlayerSession().getUsername() + ":\n");

            //TODO printing the current player's name is temporary, once fixed remove
            //TODO we need to print the player's card for whom is asking for their private goal card, not the current player. Others can't see other player's card

            PersonalGoalCardPrinter.print(model.getCurrentPlayerSession().getPersonalGoalCard());
            Console.printnl();
        }
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
        int tilesSize = model.getCurrentPlayerSession().getPlayerTileSelection().getSelectedTiles().size();
        List<Tile> selectedTiles = model.getCurrentPlayerSession().getPlayerTileSelection().getSelectedTiles();
        int column = ColumnParser.scan(model.getCurrentPlayerSession().getBookshelf().getShelfMatrix(), tilesSize);
        List<Tile> orderedTiles;

        if (tilesSize > 1) {
            orderedTiles = PlayerTilesOrderInsertionParser
                    .scan(selectedTiles);
        } else {
            orderedTiles = selectedTiles;
        }
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
