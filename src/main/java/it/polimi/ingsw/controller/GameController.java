package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.result.failures.*;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.networkProtocol.RMIConnection.ServerGateway;
import it.polimi.ingsw.controller.connection.ClientConnection;
import it.polimi.ingsw.controller.connection.ConnectionStatus;
import it.polimi.ingsw.controller.result.SingleResult;
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

@SuppressWarnings("unused")
public class GameController implements ServerGateway {

    protected static final Logger logger = LoggerFactory.getLogger(GameController.class);

    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    private final Map<String, ClientConnection> connections = new HashMap<>();

    /**
     * Game instance
     */
    private Game game;

    private int maxPlayerAmount;

    private ServerStatus serverStatus = ServerStatus.NO_GAME_STARTED;


    @Override
    public SingleResult<StatusError> gameStartedRequest(GameMode mode, String username, ClientProtocol protocol) {
        logger.info("gameStartedRequest, mode={}", mode);

        if (serverStatus == ServerStatus.NO_GAME_STARTED) {
            game = new Game(mode);
            maxPlayerAmount = mode.maxPlayerAmount();

            serverStatus = ServerStatus.GAME_INITIALIZING;

            connections.put(username, new ClientConnection(username, ConnectionStatus.OPEN));
            game.addPlayer(username);

            return new SingleResult.Success<>();
        } else {
            // return new SingleResult.Failure(RequestError);
            return null;
        }
    }

    @Override
    public ServerStatus serverStatusRequest() {
        return serverStatus;
    }


    // Creates a connection between client and server
    @Override
    public SingleResult<SignUpRequest> gameConnectionRequest(String username, ClientProtocol protocol) {
        logger.info("gameConnectionRequest(username={})", username);

        assert connections.size() <= maxPlayerAmount;

        if (serverStatus == ServerStatus.GAME_RUNNING) {
            return new SingleResult.Failure<>(SignUpRequest.GAME_ALREADY_STARTED);
        }

        if (connections.size() == maxPlayerAmount) {
            return new SingleResult.Failure<>(SignUpRequest.MAX_PLAYER_REACHED);
        }

        if (connections.containsKey(username)) {
            return new SingleResult.Failure<>(SignUpRequest.USERNAME_ALREADY_IN_USE);
        }

        if (game.getGameStatus() == GameStatus.ENDED) {
            return new SingleResult.Failure<>(SignUpRequest.GAME_ALREADY_ENDED);
        }


        // if successful & can start game
        if (maxPlayerAmount == connections.size() + 1) {
            serverStatus = ServerStatus.GAME_RUNNING;
        }

        connections.put(username, new ClientConnection(username, ConnectionStatus.OPEN));
        game.addPlayer(username);

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


    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        logger.info("gameSelectionTurnResponse(username={}, selection={})", username, selection);

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


    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
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

    @Override
    public void keepAlive(String player) {
        if (connections.containsKey(player)) {
            connections.get(player).setStatus(ConnectionStatus.OPEN);
        } else {
            logger.warn("Wrong keep alive username");
        }
    }

}
