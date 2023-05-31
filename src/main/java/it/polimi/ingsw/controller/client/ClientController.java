package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.app.client.AppClient;
import it.polimi.ingsw.app.client.layers.network.ClientNetworkLayer;
import it.polimi.ingsw.app.client.layers.view.ViewFactory;
import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.controller.client.lifecycle.AppLifecycle;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Set;

/**
 * Client-side controller.
 * Acts as an event hub, routing incoming network calls to their respective interfaces, and back to the server
 */
public class ClientController extends UnicastRemoteObject implements AppLifecycle, ClientService, LobbyViewEventHandler, GameViewEventHandler, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private ClientExhaustiveConfiguration config;

    private ClientGateway gateway;

    /**
     * Lobby waiting room.
     */
    private LobbyGateway lobby;

    /**
     * Game ser-interface.
     * The controller receives incoming event calls by the server, and forwards model centric events
     * to the user interface gateway.
     * <p>
     * The UI gateway processes those events, displays them to the user, and eventually forwards its
     * user-generated events to this controller (through {@link GameViewEventHandler})
     */
    private GameGateway ui;


    private String ownerUsername;
    private boolean hasAuthenticatedWithServerAndExchangedUsername = false;
    private ClientController identity;

    /**
     * Constructs a ClientController object with the specified gateway and configuration.
     *
     * @param gateway The client gateway.
     * @param config  The exhaustive configuration for the client.
     */
    public ClientController(ClientGateway gateway, ClientExhaustiveConfiguration config) throws RemoteException {
        this.gateway = gateway;
        this.config = config;

        switch (config.protocol()) {
            case RMI, TCP -> {
                identity = this;
            }
        }
    }

    protected ClientController() throws RemoteException {
    }


    // Lifecycle

    /**
     * Initializes the client controller.
     */
    @Override
    public void initialize() {
        // initialize
        ViewFactory.createLobbyUiAsync(config.mode(), this, AppClient.clientExecutorService);

        //ViewLayer.scheduleLobbyExecutionThread(lobby, executorService);
    }

    @Override
    public void onLobbyUiReady(LobbyGateway lobby) {
        this.lobby = lobby;

        try {
            gateway.serverStatusRequest(identity);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameUiReady(GameGateway ui) {
        this.ui = ui;
    }


    /**
     * Authorizes the client with the specified username and game.
     *
     * @param username The username of the client.
     * @param game     The game.
     */
    @Override
    public void authorize(String username, Game game) {
        // setup internal variables post-authorization
        ownerUsername = username;
        hasAuthenticatedWithServerAndExchangedUsername = true;

        // schedules ack thread
        ClientNetworkLayer.scheduleKeepAliveThread(ownerUsername, gateway, AppClient.clientExecutorService);
    }

    /**
     * Terminates the client controller.
     */
    @Override
    public void terminate() {

    }

    /**
     * Sends a status update request to the server.
     */
    @Override
    public void sendStatusUpdateRequest() {
        try {
            gateway.serverStatusRequest(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    // Lobby

    /**
     * Sends a game start request with the specified username and game mode.
     *
     * @param username The username of the client.
     * @param mode     The game mode.
     */
    @Override
    public void sendGameStartRequest(String username, GameMode mode) {
        try {
            gateway.gameStartRequest(username, mode, config.protocol(), this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a game connection request with the specified username.
     *
     * @param username The username of the client.
     */
    @Override
    public void sendGameConnectionRequest(String username) {
        try {
            gateway.gameConnectionRequest(username, config.protocol(), this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    // ClientService

    /**
     * Handles the event when the connection is accepted and the username is finalized.
     *
     * @param username The username of the client.
     * @param game     The game.
     */
    @Override
    public void onAcceptConnectionAndFinalizeUsername(String username, Game game) {
        authorize(username, game);
    }

    /**
     * Handles the event when the server status is updated.
     *
     * @param status     The server status.
     * @param playerInfo The list of player information.
     */
    @Override
    public void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        logger.info("Received status={}, playerInfo={}", status, playerInfo);

        lobby.onServerStatusUpdate(status, playerInfo);

    }

    /**
     * Handles the event when the game creation reply is received.
     *
     * @param result The result of the game creation, either a success or an error.
     */
    @Override
    public void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        lobby.onServerCreationReply(result);
    }

    /**
     * Handles the event when the game connection reply is received.
     *
     * @param result The result of the game connection, either a success or an error.
     */
    @Override
    public void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        lobby.onServerConnectionReply(result);
    }

    /**
     * Handles the event when the game is started.
     *
     * @param game The game.
     */
    @Override
    public void onGameStartedEvent(Game game) {
        lobby.kill();

        ViewFactory.createGameUiAsync(config.mode(), game, this, ownerUsername, AppClient.clientExecutorService);
    }

    /**
     * Handles the event when the model is updated.
     *
     * @param game The game.
     */
    @Override
    public void onModelUpdateEvent(Game game) {
        ui.modelUpdate(game);
    }

    /**
     * Handles the event when the game selection turn event is received.
     *
     * @param turnResult The result of the game selection turn, either a success or a failure.
     */
    @Override
    public void onGameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {
        ui.onGameSelectionReply(turnResult);
    }

    /**
     * Handles the event when the game insertion turn event is received.
     *
     * @param turnResult The result of the game insertion turn, either a success or a failure.
     */
    @Override
    public void onGameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {
        ui.onGameInsertionReply(turnResult);
    }

    /**
     * Handles the event when the player connection status is updated.
     *
     * @param usernames The list of player usernames.
     */
    @Override
    public void onPlayerConnectionStatusUpdateEvent(List<PlayerInfo> usernames) {
        // not used
    }

    /**
     * Handles the event when the game is ended.
     */
    @Override
    public void onGameEndedEvent() {
        // not used
    }


    // ViewEventHandler

    /**
     * Handles the event when the view performs a selection.
     *
     * @param coordinates The set of coordinates selected by the view.
     */
    @Override
    public void onViewSelection(Set<Coordinate> coordinates) {
        try {
            gateway.gameSelectionTurnResponse(ownerUsername, coordinates);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the event when the view performs an insertion.
     *
     * @param column The column where the insertion is performed.
     * @param tiles  The list of tiles to be inserted.
     */
    @Override
    public void onViewInsertion(int column, List<Tile> tiles) {
        try {
            gateway.gameInsertionTurnResponse(ownerUsername, tiles, column);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the event when the view sends a chat message.
     *
     * @param message The chat message.
     */
    @Override
    public void onViewSendMessage(ChatTextMessage message) {

    }

    /**
     * Handles the event when the view requests to quit the game.
     */
    @Override
    public void onViewQuitGame() {

    }

    /**
     * Keeps the connection alive.
     */
    public void keepAlive() {

    }
}
