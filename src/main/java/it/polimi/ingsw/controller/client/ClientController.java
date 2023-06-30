package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.app.client.AppClient;
import it.polimi.ingsw.app.client.layers.network.ClientNetworkLayer;
import it.polimi.ingsw.app.client.layers.view.ViewFactory;
import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.controller.client.lifecycle.AppLifecycle;
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
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.services.ClientFunction;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client-side controller.
 * Acts as an event hub, routing incoming network calls to their respective interfaces, and back to the server.
 */
public class ClientController extends UnicastRemoteObject implements AppLifecycle, ClientService, LobbyViewEventHandler, GameViewEventHandler, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    /**
     * Launch config, containing all protocol and server specific data
     * */
    private ClientExhaustiveConfiguration config;

    /**
     * Server communication
     * */
    private ClientGateway gateway;

    /**
     * Lobby waiting room, used to read username (and eventually game mode) and start the server game.
     * The controller receives incoming event calls by the server, and forwards model centric events
     * to the user interface gateway.
     * <p>
     * The UI gateway processes those events, displays them to the user, and eventually forwards its
     * user-generated events to this controller (through {@link GameViewEventHandler})
     */
    private LobbyGateway lobby;

    /**
     * Actual game
     * The controller receives incoming event calls by the server, and forwards model centric events
     * to the user interface gateway.
     * <p>
     * The {@code GameGateway} processes those events, displays them to the user, and eventually forwards its
     * user-generated events to this controller (through {@link GameViewEventHandler})
     */
    private GameGateway ui;


    private String ownerUsername;
    private ClientController identity;

    /**
     * We need an asynchronous, multithreaded pool because multiple independent calls may be coming in at once,
     * and we need to process them in parallel (e.g. chat message while user updates data)
     */
    private final ExecutorService asyncExecutor = Executors.newCachedThreadPool();

    /**
     * Constructs a ClientController object with the specified gateway and configuration.
     *
     * @param gateway The client gateway.
     * @param config  The exhaustive configuration for the client.
     */
    public ClientController(ClientGateway gateway, @NotNull ClientExhaustiveConfiguration config) throws RemoteException {
        this.gateway = gateway;
        this.config = config;

        switch (config.protocol()) {
            case RMI, TCP -> identity = this;
        }
    }

    @SuppressWarnings("unused")
    protected ClientController() throws RemoteException {
    }


    ///////////////////////////////////////////////////////////////// Client Lifecycle

    /**
     * Initializes the client controller.
     */
    @Override
    public void initialize() {
        // initialize
        ViewFactory.createLobbyUiAsync(config.mode(), this, AppClient.clientExecutorService);

        // we will get a callback for onLobbyUiReady() to get the actual {@code LobbyGateway}
    }

    @Override
    public void onLobbyUiReady(LobbyGateway lobby) {
        logger.info("onLobbyUiReady() started");
        this.lobby = lobby;

        try {
            gateway.serverStatusRequest(identity);
        } catch (RemoteException e) {
            System.out.println("Connection issue, can not communicate with the server");
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameUiReady(GameGateway ui) {
        this.ui = ui;

        ui.onGameCreated();
    }


    /**
     * Authorizes the client with the specified username and game.
     *
     * @param username The username of the client.
     */
    @Override
    public void authorize(String username) {
        // setup internal variables post-authorization
        ownerUsername = username;

        // schedules keep-alive thread
        ClientNetworkLayer.scheduleKeepAliveThread(ownerUsername, gateway, AppClient.clientExecutorService);
    }

    /**
     * Terminates the client controller.
     */
    @Override
    public void terminate() {
        asyncExecutor.shutdown();
    }


    ///////////////////////////////////////////////////////////////// ClientService (callbacks)

    /**
     * Handles the event when the connection is accepted and the username is finalized.
     *
     * @param username The username of the client.
     */
    @Override
    @ClientFunction
    public void onAcceptConnectionAndFinalizeUsername(String username) {
        logger.info("onAcceptConnectionAndFinalizeUsername(username={})", username);

        asyncExecutor.submit(() -> authorize(username));
    }

    /**
     * Handles the event when the server status is updated.
     *
     * @param status     The server status.
     * @param playerInfo The list of player information.
     */
    @Override
    @ClientFunction
    public void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        logger.info("onServerStatusUpdateEvent(status={}, playerInfo={})", status, playerInfo);

        asyncExecutor.submit(() -> {
            if (lobby != null) {
                lobby.onServerStatusUpdate(status, playerInfo);
            }
        });

        asyncExecutor.submit(() -> {
            if (ui != null) {
                ui.onGameServerStatusUpdate(status, playerInfo);
            }
        });
    }

    /**
     * Handles the event when the game creation reply is received.
     *
     * @param result The result of the game creation, either a success or an error.
     */
    @Override
    @ClientFunction
    public void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        logger.info("onGameCreationReply(result={})", result);
        asyncExecutor.submit(() -> lobby.onServerCreationReply(result));
    }

    /**
     * Handles the event when the game connection reply is received.
     *
     * @param result The result of the game connection, either a success or an error.
     */
    @Override
    @ClientFunction
    public void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        logger.info("onGameConnectionReply(result={})", result);

        asyncExecutor.submit(() -> lobby.onServerConnectionReply(result));
    }

    /**
     * Handles the event when the game is started.
     *
     * @param game The game.
     */
    @Override
    @ClientFunction
    public void onGameStartedEvent(GameModel game) {
        logger.info("onGameStartedEvent(game={})", game);

        asyncExecutor.submit(() -> ViewFactory.createGameUiAsync(config.mode(), game, this, ownerUsername, AppClient.clientExecutorService));
    }

    /**
     * Handles the event when the model is updated.
     *
     * @param game The game.
     */
    @Override
    @ClientFunction
    public void onModelUpdateEvent(GameModel game) {
        logger.info("onModelUpdateEvent(game={})", game);

        asyncExecutor.submit(() -> ui.modelUpdate(game));
    }

    /**
     * Handles the event when the game selection turn event is received.
     *
     * @param turnResult The result of the game selection turn, either a success or a failure.
     */
    @Override
    @ClientFunction
    public void onGameSelectionTurnEvent(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        logger.info("onGameSelectionTurnEvent(turnResult={})", turnResult);

        asyncExecutor.submit(() -> ui.onGameSelectionReply(turnResult));
    }

    /**
     * Handles the event when the game insertion turn event is received.
     *
     * @param turnResult The result of the game insertion turn, either a success or a failure.
     */
    @Override
    @ClientFunction
    public void onGameInsertionTurnEvent(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        logger.info("onGameInsertionTurnEvent(turnResult={})", turnResult);

        asyncExecutor.submit(() -> ui.onGameInsertionReply(turnResult));
    }

    /**
     * Handles the event when the player connection status is updated.
     *
     * @param playerInfo The list of player playerInfo.
     */
    @Override
    @ClientFunction
    public void onPlayerConnectionStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        // logger.info("onPlayerConnectionStatusUpdateEvent(turnResult={})", playerInfo);
        // not used
    }

    @Override
    public void onChatModelUpdate(List<ChatTextMessage> messages) {
        logger.info("onChatModelUpdate(messages={})", messages);

        asyncExecutor.submit(() -> ui.chatModelUpdate(messages));
    }

    /**
     * Handles the event when the game is ended.
     */
    @Override
    @ClientFunction
    public void onGameEndedEvent() {
        logger.info("onGameEndedEvent()");

        asyncExecutor.submit(() -> ui.onGameEnded());
    }


    ///////////////////////////////////////////////////////////////// LobbyViewEventHandler

    /**
     * Sends a status update request to the server.
     */
    @Override
    public void sendStatusUpdateRequest() {
        logger.info("sendStatusUpdateRequest()");

        try {
            gateway.serverStatusRequest(this);
        } catch (RemoteException e) {
            System.out.println("Connection issue, can not communicate with the server");
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a game start request with the specified username and game mode.
     *
     * @param username The username of the client.
     * @param mode     The game mode.
     */
    @Override
    public void sendGameStartRequest(String username, GameMode mode) {
        logger.info("sendGameStartRequest()");

        try {
            gateway.gameStartRequest(username, mode, config.protocol(), this);
        } catch (RemoteException e) {
            System.out.println("Connection issue, can not communicate with the server");
            System.exit(-1);
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
        logger.info("sendGameConnectionRequest()");

        try {
            gateway.gameConnectionRequest(username, config.protocol(), this);
        } catch (RemoteException e) {
            System.out.println("Connection issue, can not communicate with the server");
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }


    ///////////////////////////////////////////////////////////////// GameViewEventHandler

    /**
     * Handles the event when the view performs a selection.
     *
     * @param coordinates The set of coordinates selected by the view.
     */
    @Override
    public void onViewSelection(Set<Coordinate> coordinates) {
        logger.info("onViewSelection()");

        try {
            gateway.gameSelectionTurnResponse(ownerUsername, coordinates);
        } catch (RemoteException e) {
            System.out.println("Connection issue, can not communicate with the server");
            System.exit(-1);
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
        logger.info("onViewInsertion()");

        try {
            gateway.gameInsertionTurnResponse(ownerUsername, tiles, column);
        } catch (RemoteException e) {
            System.out.println("Connection issue, can not communicate with the server");
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the event when the view sends a chat message.
     */
    @Override
    public void onViewSendMessage(String senderUsername, MessageRecipient recipient, String text) {
        logger.info("onViewSendMessage()");

        try {
            gateway.sendTextMessage(senderUsername, recipient, text);
        } catch (RemoteException e) {
            System.out.println("Connection issue, can not communicate with the server");
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the event when the view requests to quit the game.
     */
    @Override
    public void onViewQuitGame(String username) {
        logger.info("onViewQuitGame(username={})", username);

        try {
            gateway.quitRequest(username);
        } catch (RemoteException e) {
            System.out.println("Connection issue, can not communicate with the server");
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }
}
