package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.connection.PeriodicConnectionAwareComponent;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.controller.server.router.Router;
import it.polimi.ingsw.controller.server.validator.Validator;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.network.tcp.TcpConnectionHandler;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.services.ServerService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
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
public class ServerController implements ServerService, PeriodicConnectionAwareComponent {

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


    private List<PlayerInfo> packPlayerInfo() {
        return connectionsManager.values()
                .stream()
                .map(user -> new PlayerInfo(user.getUsername(), user.getStatus(), user.isHost()))
                .toList();
    }


    /**
     * Connects a username and a remote service {@link ClientService} to allow asynchronous
     * communication between client and server.
     *
     * @apiNote to be called only when the user connection/creation request has been accepted.
     */
    private synchronized void synchronizeConnectionLayer(String username, @NotNull ClientService service) throws RemoteException {
        // connection stash service for callbacks
        connectionsManager.get(username).getStash().setClientConnectionService(service);
        service.onAcceptConnectionAndFinalizeUsername(username);

        // en-route parameters
        switch (service) {
            case TcpConnectionHandler handler -> {
                handler.setUsername(username);
            }
            case Proxy controller -> {
            }
            default -> throw new IllegalStateException("Unexpected client service value: " + service);
        }
    }


    @Override
    public void serverStatusRequest(ClientService remoteService) throws RemoteException {
        try {
            remoteService.onServerStatusUpdateEvent(serverStatus, packPlayerInfo());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public synchronized void gameStartRequest(String username, GameMode mode, ClientProtocol protocol, ClientService remoteService) throws RemoteException {
        logger.info("gameStartedRequest, mode={}, username={}, protocol={}", mode, username, protocol);

        if (serverStatus == GAME_RUNNING) {
            logger.warn("returning failure from gameStartRequest(): {}", GameCreationError.GAME_ALREADY_RUNNING);

            remoteService.onGameCreationReply(new TypedResult.Failure<>(GameCreationError.GAME_ALREADY_RUNNING));
            return;
        }

        if (serverStatus == GAME_INITIALIZING) {
            logger.warn("returning failure from gameStartRequest(): {}", GameCreationError.GAME_ALREADY_INITIALIZING);

            remoteService.onGameCreationReply(new TypedResult.Failure<>(GameCreationError.GAME_ALREADY_INITIALIZING));
            return;
        }


        if (!Validator.isValidUsername(username)) {
            logger.warn("returning failure from gameStartRequest(): {}", GameCreationError.INVALID_USERNAME);

            remoteService.onGameCreationReply(new TypedResult.Failure<>(GameCreationError.INVALID_USERNAME));
            return;
        }


        // accepting request and setting up the game model
        game = new Game(mode);
        game.addPlayer(username);
        maxPlayerAmount = mode.maxPlayerAmount();

        // update the server status
        serverStatus = GAME_INITIALIZING;

        // synchronize client, sending permanent username to client and saving remote service to stash for callbacks
        connectionsManager.add(username, protocol, true, OPEN, remoteService);
        synchronizeConnectionLayer(username, remoteService);


        // notify the
        logger.info("returning success from gameStartRequest()");

        // return success to the caller
        router.route(username).onGameCreationReply(new TypedResult.Success<>(new GameCreationSuccess(username, packPlayerInfo())));

        // route the new status to everybody
        // router.broadcast().onServerStatusUpdateEvent(serverStatus, packPlayerInfo());
    }


    // Creates a connection between client and server
    @Override
    public synchronized void gameConnectionRequest(String username, ClientProtocol protocol, ClientService remoteService) throws RemoteException {
        logger.info("gameConnectionRequest(username={}, protocol={}, remoteService={})", username, protocol, remoteService);

        assert connectionsManager.size() <= maxPlayerAmount;

        if (!Validator.isValidUsername(username)) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.INVALID_USERNAME);

            remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.INVALID_USERNAME));
            return;
        }

        if (serverStatus == GAME_RUNNING) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.GAME_ALREADY_STARTED);

            // anonymous routing
            remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.GAME_ALREADY_STARTED));
            return;
        }

        if (serverStatus == NO_GAME_STARTED) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.NO_GAME_TO_JOIN);

            // anonymous routing
            remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.NO_GAME_TO_JOIN));
            return;
        }

        if (connectionsManager.size() == maxPlayerAmount) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.MAX_PLAYER_AMOUNT_EACHED);

            // anonymous routing
            remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.MAX_PLAYER_AMOUNT_EACHED));
            return;
        }

        if (connectionsManager.containsUsername(username)) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.USERNAME_ALREADY_IN_USE);

            // anonymous routing
            remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.USERNAME_ALREADY_IN_USE));
            return;
        }

        if (game.getGameStatus() == ENDED) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.GAME_ALREADY_ENDED);

            // anonymous routing
            remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.GAME_ALREADY_ENDED));
            return;
        }


        // add player to game
        game.addPlayer(username);

        // whether the conditions for a new game are met at the time of executing this call
        boolean shouldStartGame = maxPlayerAmount == game.getPlayerCount();

        if (shouldStartGame) {
            // conditions for starting game are met
            serverStatus = GAME_RUNNING;

            // tell model to start its active game phase
            game.onGameStarted();
        }


        // synchronize client, sending permanent username to client and saving remote service to stash for callbacks
        connectionsManager.add(username, protocol, false, OPEN, remoteService);
        synchronizeConnectionLayer(username, remoteService);


        // route a success response to the caller, since it connected successfully
        router.route(username).onGameConnectionReply(new TypedResult.Success<>(new GameConnectionSuccess(username, packPlayerInfo())));


        // route the new status to everybody, giving the details for the new connected player
        router.broadcastExcluding(username).onServerStatusUpdateEvent(serverStatus, packPlayerInfo());


        if (shouldStartGame) {
            // broadcast the game model with all users to everybody, only if the game has started.
            // This will display the main game UI
            router.broadcast().onGameStartedEvent(game);
        }


        // log an interaction for the requiring user
        connectionsManager.registerInteraction(username);
    }


    // Game logic
    public synchronized boolean isUsernameActivePlayer(@NotNull String username) {
        return username.equals(game.getCurrentPlayerSession().getUsername());
    }

    public synchronized boolean shouldStandbyGame() {
        return connectionsManager.values().stream().filter(player -> player.getStatus() == DISCONNECTED).count() >= maxPlayerAmount - 1;
    }


    @Override
    public synchronized void gameSelectionTurnResponse(String username, Set<Coordinate> selection) throws RemoteException {
        logger.info("gameSelectionTurnResponse(username={}, selection={})", username, selection);
        connectionsManager.registerInteraction(username);

        if (!isUsernameActivePlayer(username)) {
            router.route(username).onGameSelectionTurnEvent(new TypedResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_PLAYER));
            return;
        }

        if (game.getCurrentPlayerSession().getPlayerCurrentGamePhase() != SELECTING) {
            router.route(username).onGameSelectionTurnEvent(new TypedResult.Failure<>(TileSelectionFailures.WRONG_GAME_PHASE));
            return;
        }

        if (!game.isSelectionValid(selection)) {
            router.route(username).onGameSelectionTurnEvent(new TypedResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_SELECTION));
            return;
        }

        game.onPlayerSelectionPhase(selection);

        router.route(username).onGameSelectionTurnEvent(new TypedResult.Success<>(new TileSelectionSuccess(game)));
        router.broadcastExcluding(username).onModelUpdateEvent(game);
    }


    @Override
    public synchronized void gameInsertionTurnResponse(String username, List<Tile> tiles, int column) throws RemoteException {
        logger.info("onPlayerBookshelfTileInsertionRequest(username={}, tiles={}, column={})", username, tiles, column);
        connectionsManager.registerInteraction(username);

        if (!isUsernameActivePlayer(username)) {
            router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.WRONG_PLAYER));
            return;
        }

        if (game.getCurrentPlayerSession().getPlayerCurrentGamePhase() != INSERTING) {
            router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.WRONG_GAME_PHASE));
            return;
        }

        if (!game.getCurrentPlayerSession().getPlayerTileSelection().selectionEquals(tiles)) {
            router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.WRONG_SELECTION));
            return;
        }

        if (column < 0 || column >= BookshelfConfiguration.getInstance().cols()) {
            router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.ILLEGAL_COLUMN));
            return;
        }

        if (tiles.size() > LogicConfiguration.getInstance().maxSelectionSize()) {
            router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.TOO_MANY_TILES));
            return;
        }

        if (!game.getCurrentPlayerSession().getBookshelf().canFit(column, tiles.size())) {
            router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.NO_FIT));
            return;
        }

        game.onPlayerInsertionPhase(column, tiles);
        game.onPlayerTurnEnding();

        PlayerSession nextPlayer = processNextPlayer();

        game.onNextTurn(nextPlayer.getUsername());

        router.route(username).onGameInsertionTurnEvent(new TypedResult.Success<>(new TileInsertionSuccess(game)));
        router.broadcastExcluding(username).onModelUpdateEvent(game);
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

    private synchronized PlayerSession processNextPlayer() {
        PlayerNumber currentPlayerNumber = game.getCurrentPlayerSession().getPlayerNumber();

        PlayerNumber next = next_rec(currentPlayerNumber);

        return game.getPlayerSession(next);
    }

    private synchronized PlayerNumber next_rec(@NotNull PlayerNumber current) {
        PlayerNumber naturalNextNumber = current.next(game.getGameMode());
        PlayerSession next = game.getPlayerSession(naturalNextNumber);

        if (connectionsManager.isClientDisconnected(next.getUsername())) {
            return next_rec(next.getPlayerNumber());
        } else {
            return next.getPlayerNumber();
        }
    }


    /*
    private synchronized void onNextTurn(String nextPlayerUsername) {
        // assume the username is correct
        assert game.getSessions().isPresent(nextPlayerUsername);
        PlayerNumber currentPlayerUsername = game.getCurrentPlayerSession().getPlayerNumber();

        currentPlayerUsername = game.getSessions().getByUsername(nextPlayerUsername).getPlayerNumber();
        game.getCurrentPlayerSession().setPlayerCurrentGamePhase(SELECTING);
    }*/


    @Override
    public ClientConnectionsManager getConnectionsManager() {
        return connectionsManager;
    }

    @Override
    public void onConnectionChange() {
        try {
            if (connectionsManager.isAnyClientClosed()) {
                serverStatus = GAME_OVER;

                router.broadcastExcluding(
                        connectionsManager.getDisconnectedOrClosedClientUsernames()
                ).onServerStatusUpdateEvent(serverStatus, packPlayerInfo());
                return;
            }
            // no clients are closed


            if (connectionsManager.isAnyClientDisconnected()) {
                // Server Status Update
                // there are clients disconnected
                router.broadcastExcluding(
                        connectionsManager.getDisconnectedClientUsernames()
                ).onServerStatusUpdateEvent(serverStatus, packPlayerInfo());



                if (game != null && game.getGameStatus() != GameStatus.INITIALIZATION) {
                    // Model update: there is an active player, and the game is started/last round/standby
                    // we have to check if the current player is disconnected.
                    // if it is, and we are not in standby, we need to move forward
                    String currentPlayerUsername = game.getCurrentPlayerSession().getUsername();

                    if (connectionsManager.getDisconnectedClientUsernames().contains(currentPlayerUsername)) {
                        // force-forward turn, rollback to pre-selection model
                        PlayerSession nextPlayer = processNextPlayer();
                        game.onForcedNextTurn(nextPlayer.getUsername());
                    }


                    boolean shouldStandby = shouldStandbyGame();

                    if (shouldStandby) {
                        // player(s) disconnected and game standby
                        boolean hasModelStatusChanged = game.onStandby();

                        if (hasModelStatusChanged) {
                            router.broadcastExcluding(
                                    connectionsManager.getDisconnectedClientUsernames()
                            ).onModelUpdateEvent(game);
                        }
                    } else {
                        // player(s) disconnected but game running
                        boolean hasModelStatusChanged = game.onResume();

                        if (hasModelStatusChanged) {
                            router.broadcastExcluding(
                                    connectionsManager.getDisconnectedClientUsernames()
                            ).onModelUpdateEvent(game);
                        }
                    }
                }
            } else {
                // players all connected
                boolean hasModelStatusChanged = game.onResume();

                if (hasModelStatusChanged) {
                    router.broadcastExcluding(
                            connectionsManager.getDisconnectedClientUsernames()
                    ).onModelUpdateEvent(game);
                }

                // all clients are back online, resend model and server status. Maybe make appropriate request
                router.broadcast().onServerStatusUpdateEvent(serverStatus, packPlayerInfo());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}