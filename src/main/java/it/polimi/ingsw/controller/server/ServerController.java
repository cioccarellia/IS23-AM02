package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.router.Router;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.network.tcp.TcpConnectionHandler;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.services.ServerService;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.controller.server.connection.ConnectionStatus.DISCONNECTED;
import static it.polimi.ingsw.controller.server.connection.ConnectionStatus.OPEN;
import static it.polimi.ingsw.controller.server.model.ServerStatus.*;
import static it.polimi.ingsw.model.game.GameStatus.ENDED;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.INSERTING;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.SELECTING;

/**
 * Server-side controller
 */
public class ServerController implements ServerService {

    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);


    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    private final ClientConnectionsManager connectionsManager;

    /**
     * Routes asynchronous client responses to their destination
     */
    private final Router router;

    /**
     * Game instance
     */
    private Game game;

    /**
     *
     */
    private int maxPlayerAmount;

    /**
     * Current server general status, independent from game logic
     */
    private ServerStatus serverStatus = NO_GAME_STARTED;

    public ServerController(ClientConnectionsManager manager) {
        connectionsManager = manager;
        router = new Router(connectionsManager);
    }


    private List<Pair<String, ConnectionStatus>> currentPlayerInfo() {
        return connectionsManager.values()
                .stream()
                .map(user -> new Pair<>(user.getUsername(), user.getStatus()))
                .toList();
    }


    /**
     * Connects a username and a remote service {@link ClientService} to allow asynchronous
     * communication between client and server.
     *
     * @apiNote to be called only when the user connection/creation request has been accepted.
     */
    public synchronized void synchronizeConnectionLayer(String username, @NotNull ClientService service) {
        // connection stash service for callbacks
        connectionsManager.get(username).getStash().setClientConnectionService(service);
        service.onAcceptConnectionAndFinalizeUsername(username, game);

        // en-route parameters
        switch (service) {
            case TcpConnectionHandler handler -> {
                handler.setUsername(username);
            }
            case ClientController controller -> {}
            default -> throw new IllegalStateException("Unexpected value: " + service);
        }
    }

    @Override
    public synchronized void gameStartRequest(String username, GameMode mode, ClientProtocol protocol, ClientService remoteService) {
        logger.info("gameStartedRequest, mode={}, username={}, protocol={}", mode, username, protocol);

        if (serverStatus == NO_GAME_STARTED) {
            // accepting request and setting up game
            game = new Game(mode);
            game.addPlayer(username);
            maxPlayerAmount = mode.maxPlayerAmount();

            // update server status
            serverStatus = GAME_INITIALIZING;

            // adds a player to the game model

            // synchronize
            connectionsManager.add(username, protocol, OPEN, remoteService);
            synchronizeConnectionLayer(username, remoteService);


            // notify the
            logger.info("returning success from gameStartRequest()");

            // return success to the caller
            router.route(username).onGameCreationReply(new SingleResult.Success<>());

            // route the new status to everybody
            router.broadcast().onServerStatusUpdateEvent(serverStatus, currentPlayerInfo());
        } else {
            logger.warn("returning failure from gameStartRequest()");

            router.route(username).onGameCreationReply(new SingleResult.Failure<>(GameCreationError.GAME_ALREADY_STARTED));
        }
    }


    // Creates a connection between client and server
    @Override
    public synchronized void gameConnectionRequest(String username, ClientProtocol protocol, ClientService remoteService) {
        logger.info("gameConnectionRequest(username={}, protocol={}, remoteService={})", username, protocol, remoteService);
        connectionsManager.registerInteraction(username);

        assert connectionsManager.size() <= maxPlayerAmount;

        if (serverStatus == GAME_RUNNING) {
            router.route(username).onGameConnectionReply(new SingleResult.Failure<>(GameConnectionError.GAME_ALREADY_STARTED));
            return;
        }

        if (connectionsManager.size() == maxPlayerAmount) {
            router.route(username).onGameConnectionReply(new SingleResult.Failure<>(GameConnectionError.MAX_PLAYER_REACHED));
            return;
        }

        if (connectionsManager.containsUsername(username)) {
            router.route(username).onGameConnectionReply(new SingleResult.Failure<>(GameConnectionError.USERNAME_ALREADY_IN_USE));
            return;
        }

        if (game.getGameStatus() == ENDED) {
            router.route(username).onGameConnectionReply(new SingleResult.Failure<>(GameConnectionError.GAME_ALREADY_ENDED));
            return;
        }


        // add username and connection status
        connectionsManager.add(username, protocol, OPEN, remoteService);
        synchronizeConnectionLayer(username, remoteService);

        // add player to game
        game.addPlayer(username);

        // route a success to the caller
        router.route(username).onGameConnectionReply(new SingleResult.Success<>());

        // route the new status to everybody
        router.broadcast().onServerStatusUpdateEvent(serverStatus, currentPlayerInfo());


        boolean shouldStartGame = maxPlayerAmount == connectionsManager.size();

        if (shouldStartGame) {
            // conditions for starting game are met
            serverStatus = GAME_RUNNING;

            // broadcast a game started event to everybody
            router.broadcast().onGameStartedEvent();
        }
    }


    // Game logic
    public synchronized boolean isUsernameActivePlayer(@NotNull String username) {
        return username.equals(game.getCurrentPlayerSession().getUsername());
    }

    public synchronized boolean shouldStandbyGame() {
        return connectionsManager.values().stream().filter(player -> player.getStatus() == DISCONNECTED).count() >= maxPlayerAmount - 1;
    }


    @Override
    public synchronized void gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        logger.info("gameSelectionTurnResponse(username={}, selection={})", username, selection);
        connectionsManager.registerInteraction(username);

        if (isUsernameActivePlayer(username)) {
            router.route(username).onGameSelectionTurnEvent(new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_PLAYER));
            return;
        }

        if (game.getCurrentPlayerSession().getPlayerCurrentGamePhase() != SELECTING) {
            router.route(username).onGameSelectionTurnEvent(new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_ACTION));
            return;
        }

        if (!game.isSelectionValid(selection)) {
            router.route(username).onGameSelectionTurnEvent(new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_SELECTION));
            return;
        }

        game.onPlayerSelectionPhase(selection);

        router.route(username).onGameSelectionTurnEvent(new SingleResult.Success<>());
        router.broadcast().onModelUpdateEvent(game);
    }


    @Override
    public synchronized void gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        logger.info("onPlayerBookshelfTileInsertionRequest(username={}, tiles={}, column={})", username, tiles, column);
        connectionsManager.registerInteraction(username);

        if (isUsernameActivePlayer(username)) {
            router.route(username).onGameInsertionTurnEvent(new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_PLAYER));
            return;
        }

        if (game.getCurrentPlayerSession().getPlayerCurrentGamePhase() != INSERTING) {
            router.route(username).onGameInsertionTurnEvent(new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_STATUS));
            return;
        }

        if (!game.getCurrentPlayerSession().getPlayerTileSelection().selectionEquals(tiles)) {
            router.route(username).onGameInsertionTurnEvent(new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_SELECTION));
            return;
        }

        if (column < 0 || column >= BookshelfConfiguration.getInstance().cols()) {
            router.route(username).onGameInsertionTurnEvent(new SingleResult.Failure<>(BookshelfInsertionFailure.ILLEGAL_COLUMN));
            return;
        }

        if (tiles.size() > LogicConfiguration.getInstance().maxSelectionSize()) {
            router.route(username).onGameInsertionTurnEvent(new SingleResult.Failure<>(BookshelfInsertionFailure.TOO_MANY_TILES));
            return;
        }

        if (!game.getCurrentPlayerSession().getBookshelf().canFit(column, tiles.size())) {
            router.route(username).onGameInsertionTurnEvent(new SingleResult.Failure<>(BookshelfInsertionFailure.NO_FIT));
            return;
        }

        game.onPlayerInsertionPhase(column, tiles);

        onPlayerCheckingPhase();

        router.route(username).onGameInsertionTurnEvent(new SingleResult.Success<>());
        router.broadcast().onModelUpdateEvent(game);
    }

    public synchronized void onPlayerCheckingPhase() {
        logger.info("onPlayerCheckingRequest()");
        game.onPlayerCheckingPhase();

        onNextTurn("");
    }

    @Override
    public synchronized void keepAlive(String username) {
        logger.info("keepAlive(username={})", username);

        if (connectionsManager.containsUsername(username)) {
            connectionsManager.registerInteraction(username);
        } else {
            logger.warn("Wrong keep alive username");
        }
    }

    public synchronized void onNextTurn(String nextPlayerUsername) {
        // assume the username is correct
        assert game.getSessions().isPresent(nextPlayerUsername);
        PlayerNumber currentPlayerUsername = game.getCurrentPlayerSession().getPlayerNumber();

        currentPlayerUsername = game.getSessions().getByUsername(nextPlayerUsername).getPlayerNumber();
        game.getCurrentPlayerSession().setPlayerCurrentGamePhase(SELECTING);
    }






    public ClientConnectionsManager getConnectionsManager() {
        return connectionsManager;
    }
}