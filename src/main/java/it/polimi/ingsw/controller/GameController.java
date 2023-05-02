package it.polimi.ingsw.controller;

import it.polimi.ingsw.networkProtocol.RMIConnection.Callable;
import it.polimi.ingsw.controller.connection.ClientConnection;
import it.polimi.ingsw.controller.connection.ConnectionStatus;
import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.result.failures.SignUpRequest;
import it.polimi.ingsw.controller.result.failures.StatusError;
import it.polimi.ingsw.controller.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameController implements Callable {

    protected static final Logger logger = LoggerFactory.getLogger(GameController.class);

    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    private final Map<String, ClientConnection> connections = new HashMap<>();

    /**
     * Game instance
     */
    private final Game game;

    private final int maxPlayerAmount;

    public GameController(GameMode _mode) {
        game = new Game(_mode);
        maxPlayerAmount = _mode.playerCount();

        logger.info("GameController initialized, mode={}", _mode);
    }


    public void startCurrentTurn() {

    }


    // Creates a connection between client and server
    @Override
    public SingleResult<SignUpRequest> onPlayerSignUpRequest(String username) {
        logger.info("onPlayerSignUpRequest(username={})", username);

        assert connections.size() <= maxPlayerAmount;
        if (connections.size() == maxPlayerAmount) {
            return new SingleResult.Failure<>(SignUpRequest.MAX_PLAYER_REACHED);
        }

        if (connections.containsKey(username)) {
            return new SingleResult.Failure<>(SignUpRequest.USERNAME_ALREADY_IN_USE);
        }

        if (game.getGameStatus() == GameStatus.ENDED) {
            return new SingleResult.Failure<>(SignUpRequest.GAME_ALREADY_ENDED);
        }

        connections.put(username, new ClientConnection(username, ConnectionStatus.OPEN));
        game.addPlayer(username);
        System.out.println(username + "logged");

        return new SingleResult.Success<>();
    }


    // Sets the state of the connection for a client to be open
    public SingleResult<StatusError> onPlayerConnection(String username) {
        logger.info("onPlayerConnection(username={})", username);

        connections.get(username).setStatus(ConnectionStatus.OPEN);


        return new SingleResult.Success<>();
    }

    // Sets the state of the connection for a client to be disconnected
    public void onPlayerDisconnection(String username) {
        logger.info("onPlayerDisconnection({})", username);
        connections.get(username).setStatus(ConnectionStatus.DISCONNECTED);
    }


    // Game logic
    public boolean isUsernameActivePlayer(String username) {
        return username.equals(game.getCurrentPlayer().getUsername());
    }

    public boolean shouldStandbyGame() {
        return connections
                .values()
                .stream()
                .filter(player -> player.getStatus() == ConnectionStatus.DISCONNECTED)
                .count() >= maxPlayerAmount - 1;
    }


    public SingleResult<TileSelectionFailures> onPlayerTileSelectionRequest(String username, Set<Coordinate> selection) {
        logger.info("onPlayerTileSelectionRequest(username={}, selection={})", username, selection);

        if (isUsernameActivePlayer(username)) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_PLAYER);
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != PlayerCurrentGamePhase.SELECTING) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_ACTION);
        }

        if (!game.isSelectionValid(selection)) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_SELECTION);
        }

        game.onPlayerSelectionPhase(selection);
        return new SingleResult.Success<>();
    }


    public SingleResult<BookshelfInsertionFailure> onPlayerBookshelfTileInsertionRequest(String username, int column, List<Tile> tiles) {
        logger.info("onPlayerBookshelfTileInsertionRequest(username={}, column={}, tiles={})", username, column, tiles);

        if (isUsernameActivePlayer(username)) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_PLAYER);
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != PlayerCurrentGamePhase.INSERTING) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_STATUS);
        }

        if (!game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(tiles)) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_SELECTION);
        }

        if (column < 0 || column >= BookshelfConfiguration.getInstance().cols()) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.ILLEGAL_COLUMN);
        }

        if (tiles.size() > LogicConfiguration.getInstance().maxSelectionSize()) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.TOO_MANY_TILES);
        }

        if (!game.getCurrentPlayer().getBookshelf().canFit(column, tiles.size())) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.NO_FIT);
        }

        game.onPlayerInsertionPhase(column, tiles);

        onPlayerCheckingRequest();
        return new SingleResult.Success<>();
    }

    public void onPlayerCheckingRequest() {
        logger.info("onPlayerCheckingRequest()");
        game.onPlayerCheckingPhase();
    }


}
