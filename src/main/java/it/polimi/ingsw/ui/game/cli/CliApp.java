package it.polimi.ingsw.ui.game.cli;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.cli.parser.ColumnParser;
import it.polimi.ingsw.ui.game.cli.parser.CoordinatesParser;
import it.polimi.ingsw.ui.game.cli.parser.PlayerTilesOrderInsertionParser;
import it.polimi.ingsw.ui.game.cli.printer.BoardPrinter;
import it.polimi.ingsw.ui.game.cli.printer.BookshelvesPrinter;
import it.polimi.ingsw.ui.game.cli.printer.CommonGoalCardsPrinter;
import it.polimi.ingsw.ui.game.cli.printer.PersonalGoalCardPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class CliApp implements GameGateway {

    private static final Logger logger = LoggerFactory.getLogger(CliApp.class);

    public Game model;

    public GameViewEventHandler handler;

    public final String owner;

    public CliApp(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;

        processModel();
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

        processModel();
    }

    /**
     * Updates model's instance in order to show users an updated model every turn
     *
     * @param game model is passed to the function on order to update always the same model
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
        processModel();
    }

    /**
     * Shows users' Bookshelves, updates Board, Common goal cards and Tokens, First Player and Current Player, Private
     * goal card
     */
    public void processModel() {
        if (model == null) {
            Console.out("Void model.");
            return;
        }

        switch (model.getGameStatus()) {
            case RUNNING, LAST_ROUND -> {
                Console.printnl();
                BoardPrinter.print(model.getBoard());
                CommonGoalCardsPrinter.print(model.getCommonGoalCards());
                Console.printnl(2);
                PersonalGoalCardPrinter.print(model.getSessions().getByUsername(owner).getPersonalGoalCard());
                Console.printnl();
                BookshelvesPrinter.print(model);
                Console.printnl();

                PlayerSession currentPlayer = model.getCurrentPlayerSession();

                if (currentPlayer.getUsername().equals(owner)) {
                    switch (currentPlayer.getPlayerCurrentGamePhase()) {
                        case IDLE -> {
                        }
                        case SELECTING -> {
                        }
                        case INSERTING -> {
                        }
                        case CHECKING -> {

                        }
                    }
                }

            }
            case ENDED -> onGameEnded();
            case STANDBY -> onGameStandby();
        }
    }

    /**
     * To be invoked when it's the player turn to select
     */
    public void gameSelection() {
        Set<Coordinate> validCoordinates = CoordinatesParser.scan(model);

        handler.onViewSelection(validCoordinates);
    }

    /**
     * To be invoked when it's the player turn to insert
     */
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


    @Override
    public void onGameSelectionReply(SingleResult<TileSelectionFailures> turnResult) {
        switch (turnResult) {
            case SingleResult.Failure<TileSelectionFailures> failure -> {

            }
            case SingleResult.Success<TileSelectionFailures> success -> {

            }
        }
    }

    @Override
    public void onGameInsertionReply(SingleResult<BookshelfInsertionFailure> turnResult) {
        switch (turnResult) {
            case SingleResult.Failure<BookshelfInsertionFailure> failure -> {

            }
            case SingleResult.Success<BookshelfInsertionFailure> success -> {

            }
        }
    }


    /**
     * Shows ranking and announces the winner.
     */
    public void onGameEnded() {
        Console.out("""
                The game has ended.
                Here's the player's ranking with their points:
                """);

        List<PlayerScore> playersRanking = model.getRankings();

        playersRanking.forEach(System.out::println);
    }

    private void onGameStandby() {
        Console.out("Game standby.");
    }
}
