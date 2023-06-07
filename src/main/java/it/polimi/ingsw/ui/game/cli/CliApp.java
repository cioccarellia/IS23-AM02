package it.polimi.ingsw.ui.game.cli;

import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.Renderable;
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

public class CliApp implements GameGateway, Renderable {

    private static final Logger logger = LoggerFactory.getLogger(CliApp.class);

    public Game model;

    public GameViewEventHandler handler;

    public final String owner;

    public CliApp(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;

        render();
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

        render();
    }

    /**
     * Updates model's instance in order to show users an updated model every turn
     *
     * @param game model is passed to the function on order to update always the same model
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
        render();
    }

    /**
     * Shows users' Bookshelves, updates Board, Common goal cards and Tokens, First Player and Current Player, Private
     * goal card
     */
    @Override
    public void render() {
        if (model == null) {
            Console.out("Void model.");
            return;
        }

        switch (model.getGameStatus()) {
            case RUNNING, LAST_ROUND -> {
                PlayerSession currentPlayer = model.getCurrentPlayerSession();
                boolean isOwnerTurn = currentPlayer.getUsername().equals(owner);

                Console.outln();
                BoardPrinter.print(model.getBoard());
                CommonGoalCardsPrinter.print(model.getCommonGoalCards());
                Console.printnl(2);
                PersonalGoalCardPrinter.print(model.getSessions().getByUsername(owner).getPersonalGoalCard());
                Console.outln();
                BookshelvesPrinter.print(model);
                Console.outln();


                if (isOwnerTurn) {
                    switch (currentPlayer.getPlayerCurrentGamePhase()) {
                        case IDLE -> {
                            logger.warn("Current user in idle state, model={},", model);
                        }
                        case SELECTING -> {
                            gameSelection();
                        }
                        case INSERTING -> {
                            gameInsertion();
                        }
                    }
                } else {
                    Console.out("@" + currentPlayer.getUsername() + " is " + currentPlayer.getPlayerCurrentGamePhase().toString().toLowerCase());
                    Console.outln();
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
        List<Tile> selectedTiles = model.getCurrentPlayerSession().getPlayerTileSelection().getSelectedTiles();

        int tilesSize = selectedTiles.size();

        int column = ColumnParser.scan(model.getCurrentPlayerSession().getBookshelf().getShelfMatrix(), selectedTiles);

        List<Tile> orderedTiles;

        if (tilesSize > 1) {
            orderedTiles = PlayerTilesOrderInsertionParser.scan(selectedTiles);
        } else {
            orderedTiles = selectedTiles;
        }

        handler.onViewInsertion(column, orderedTiles);
    }


    @Override
    public void onGameSelectionReply(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        switch (turnResult) {
            case TypedResult.Failure<TileSelectionSuccess, TileSelectionFailures> failure -> {
                switch (failure.error()) {
                    case WRONG_GAME_PHASE, UNAUTHORIZED_PLAYER, UNAUTHORIZED_SELECTION:
                        break;
                }

            }
            case TypedResult.Success<TileSelectionSuccess, TileSelectionFailures> success -> {
                // implicit model update
                modelUpdate(success.value().model());
            }
        }
    }

    @Override
    public void onGameInsertionReply(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        switch (turnResult) {
            case TypedResult.Failure<TileInsertionSuccess, BookshelfInsertionFailure> failure -> {

            }
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success -> {
                // implicit modelupdate
                modelUpdate(success.value().model());
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
        Console.out("Game standby.\n");
    }
}
