package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.session.SessionManager;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.services.ServerFunction;
import it.polimi.ingsw.services.ServerService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.controller.server.connection.ConnectionStatus.DISCONNECTED;
import static it.polimi.ingsw.controller.server.connection.ConnectionStatus.OPEN;
import static it.polimi.ingsw.controller.server.model.ServerStatus.*;
import static it.polimi.ingsw.model.game.GameStatus.ENDED;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.INSERTING;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.SELECTING;

public class ServerController implements ServerService {

    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    private final ClientConnectionsManager connectionsManager;

    /**
     * Game instance
     */
    private Game game;

    private int maxPlayerAmount;

    private ServerStatus serverStatus = NO_GAME_STARTED;

    private SessionManager sessions;

    public ServerController() {
        this.connectionsManager = new ClientConnectionsManager();
    }

    public ServerController(ClientConnectionsManager connectionsManager) {
        this.connectionsManager = connectionsManager;
    }

    @Override
    public void synchronizeConnectionLayer(String username, ClientService service) throws RemoteException {

    }

    @Override
    @ServerFunction
    public void serverStatusRequest() {
        // fixme return serverStatus;
    }

    @Override
    public void gameStartRequest(String username, GameMode mode, ClientProtocol protocol) {
        logger.info("gameStartedRequest, mode={}, username={}, protocol={}", mode, username, protocol);

        if (serverStatus == NO_GAME_STARTED) {
            game = new Game(mode);
            sessions = new SessionManager(game.getGameMode());
            maxPlayerAmount = mode.maxPlayerAmount();

            serverStatus = GAME_INITIALIZING;

            connectionsManager.add(username, protocol, OPEN);
            game.addPlayer(username);

            logger.info("returning success from gameStartRequest()");
            // fixme return new SingleResult.Success<>();
        } else {
            logger.warn("returning failure from gameStartRequest()");
            // fixme return new SingleResult.Failure<>(GameStartError.GAME_ALREADY_STARTED);
        }
    }


    // Creates a connection between client and server
    @Override
    public void gameConnectionRequest(String username, ClientProtocol protocol) {
        logger.info("gameConnectionRequest(username={}, protocol={})", username, protocol);

        assert connectionsManager.size() <= maxPlayerAmount;

        if (serverStatus == GAME_RUNNING) {
            // fixme return new SingleResult.Failure<>(GameConnectionError.GAME_ALREADY_STARTED);
        }

        if (connectionsManager.size() == maxPlayerAmount) {
            // fixme return new SingleResult.Failure<>(GameConnectionError.MAX_PLAYER_REACHED);
        }

        if (connectionsManager.containsUsername(username)) {
            // fixme return new SingleResult.Failure<>(GameConnectionError.USERNAME_ALREADY_IN_USE);
        }

        if (game.getGameStatus() == ENDED) {
            // fixme return new SingleResult.Failure<>(GameConnectionError.GAME_ALREADY_ENDED);
        }


        // if successful & can start game
        if (maxPlayerAmount == connectionsManager.size() + 1) {
            serverStatus = GAME_RUNNING;
        }

        connectionsManager.add(username, protocol, OPEN);
        game.addPlayer(username);

        // fixme return new SingleResult.Success<>();
    }


    // Game logic
    public boolean isUsernameActivePlayer(@NotNull String username) {
        return username.equals(game.getCurrentPlayer().getUsername());
    }

    public boolean shouldStandbyGame() {
        return connectionsManager.values().stream().filter(player -> player.getStatus() == DISCONNECTED).count() >= maxPlayerAmount - 1;
    }


    @Override
    public void /*SingleResult<TileSelectionFailures>*/ gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        logger.info("gameSelectionTurnResponse(username={}, selection={})", username, selection);

        if (isUsernameActivePlayer(username)) {
            // fixme return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_PLAYER);
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != SELECTING) {
            // fixme return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_ACTION);
        }

        if (!game.isSelectionValid(selection)) {
            // fixme return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_SELECTION);
        }

        game.onPlayerSelectionPhase(selection);
        // fixme return new SingleResult.Success<>();
    }


    @Override
    public void /*SingleResult<BookshelfInsertionFailure>*/ gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        logger.info("onPlayerBookshelfTileInsertionRequest(username={}, tiles={}, column={})", username, tiles, column);

        if (isUsernameActivePlayer(username)) {
            // return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_PLAYER);
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != INSERTING) {
            // return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_STATUS);
        }

        if (!game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(tiles)) {
            // return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_SELECTION);
        }

        if (column < 0 || column >= BookshelfConfiguration.getInstance().cols()) {
            // return new SingleResult.Failure<>(BookshelfInsertionFailure.ILLEGAL_COLUMN);
        }

        if (tiles.size() > LogicConfiguration.getInstance().maxSelectionSize()) {
            // return new SingleResult.Failure<>(BookshelfInsertionFailure.TOO_MANY_TILES);
        }

        if (!game.getCurrentPlayer().getBookshelf().canFit(column, tiles.size())) {
            // return new SingleResult.Failure<>(BookshelfInsertionFailure.NO_FIT);
        }

        game.onPlayerInsertionPhase(column, tiles);

        onPlayerCheckingRequest();
        // return new SingleResult.Success<>();
    }

    public void onPlayerCheckingRequest() {
        logger.info("onPlayerCheckingRequest()");
        game.onPlayerCheckingPhase();
    }

    @Override
    public void keepAlive(String username) {
        logger.info("keepAlive(username={})", username);
        if (connectionsManager.containsUsername(username)) {
            connectionsManager.setConnectionStatus(username, OPEN);
        } else {
            logger.warn("Wrong keep alive username");
        }
    }

    public void onNextTurn(String nextPlayerUsername) {
        // assume the username is correct
        assert sessions.isPresent(nextPlayerUsername);
        PlayerNumber currentPlayerUsername = game.getCurrentPlayer().getPlayerNumber();

        currentPlayerUsername = sessions.getByUsername(nextPlayerUsername).getPlayerNumber();
        game.getCurrentPlayer().setPlayerCurrentGamePhase(SELECTING);
    }
}