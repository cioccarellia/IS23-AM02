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
import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.services.ClientFunction;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.ui.commons.gui.GuiApp;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.cli.CliApp;
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
 * Acts as an event hub, routing incoming network calls to their respective interfaces, and back to the server
 */
public class ClientController extends UnicastRemoteObject implements AppLifecycle, ClientService, LobbyViewEventHandler, GameViewEventHandler, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private ClientExhaustiveConfiguration config;

    private ClientGateway gateway;

    /**
     * Creates the UI view for the game based on the client UI mode.
     *
     * @param mode       The client UI mode.
     * @param model      The game model.
     * @param controller The game view event handler.
     * @param owner      The owner of the game.
     * @return The game UI view.
     */
    public static void createGameUiAsync(final @NotNull ClientUiMode mode, final GameModel model, final ClientController controller, final String owner, ExecutorService executorService) {
        switch (mode) {
            case CLI -> {
                logger.info("createGameUiAsync(): Starting CLI game");

                //executorService.submit(() -> {
                logger.info("createGameUiAsync(): Starting CLI game on dedicated thread");
                GameGateway game = new CliApp(model, controller, owner);

                logger.info("createGameUiAsync(): CLI started, calling controller.onGameUiReady()");
                controller.onGameUiReady(game);
                //});
            }
            case GUI -> {
                logger.info("Starting GUI game");
                GuiApp.injectGameModelPostLogin(model, controller, owner);

                executorService.submit(() -> {
                    // Platform.runLater(() -> {
                    logger.info("Starting GUI game on dedicated thread");
                    GuiApp.main(new String[]{});
                });

            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

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

    private final ExecutorService asyncExecutor = Executors.newSingleThreadExecutor();

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
            case RMI, TCP -> {
                identity = this;
            }
        }
    }

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
        logger.warn("onLobbyUiReady() started");
        this.lobby = lobby;

        try {
            logger.warn("onLobbyUiReady() sending RMI call serverStatusRequest()");
            gateway.serverStatusRequest(identity);
            logger.warn("onLobbyUiReady() returned RMI call serverStatusRequest()");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        logger.warn("onLobbyUiReady() ended");
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
        hasAuthenticatedWithServerAndExchangedUsername = true;

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
        logger.warn("onAcceptConnectionAndFinalizeUsername(username={})", username);
        asyncExecutor.submit(() -> {
            authorize(username);
        });
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
        logger.warn("onServerStatusUpdateEvent(status={}, playerInfo={})", status, playerInfo);
        logger.info("Received status={}, playerInfo={}", status, playerInfo);

        asyncExecutor.submit(() -> {
            lobby.onServerStatusUpdate(status, playerInfo);
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
        logger.warn("onGameCreationReply(result={})", result);
        asyncExecutor.submit(() -> {
            lobby.onServerCreationReply(result);
        });
    }

    /**
     * Handles the event when the game connection reply is received.
     *
     * @param result The result of the game connection, either a success or an error.
     */
    @Override
    @ClientFunction
    public void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        logger.warn("onGameConnectionReply(result={})", result);

        asyncExecutor.submit(() -> {
            lobby.onServerConnectionReply(result);
        });
    }

    /**
     * Handles the event when the game is started.
     *
     * @param game The game.
     */
    @Override
    @ClientFunction
    public void onGameStartedEvent(GameModel game) {
        logger.warn("onGameStartedEvent(game={})", game);

        asyncExecutor.submit(() -> {
            ViewFactory.createGameUiAsync(config.mode(), game, this, ownerUsername, AppClient.clientExecutorService);
        });
    }

    /**
     * Handles the event when the model is updated.
     *
     * @param game The game.
     */
    @Override
    @ClientFunction
    public void onModelUpdateEvent(GameModel game) {
        logger.warn("onModelUpdateEvent(game={})", game);

        asyncExecutor.submit(() -> {
            ui.modelUpdate(game);
        });
    }

    /**
     * Handles the event when the game selection turn event is received.
     *
     * @param turnResult The result of the game selection turn, either a success or a failure.
     */
    @Override
    @ClientFunction
    public void onGameSelectionTurnEvent(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        logger.warn("onGameSelectionTurnEvent(turnResult={})", turnResult);

        asyncExecutor.submit(() -> {
            ui.onGameSelectionReply(turnResult);
        });
    }

    /**
     * Handles the event when the game insertion turn event is received.
     *
     * @param turnResult The result of the game insertion turn, either a success or a failure.
     */
    @Override
    @ClientFunction
    public void onGameInsertionTurnEvent(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        logger.warn("onGameInsertionTurnEvent(turnResult={})", turnResult);

        asyncExecutor.submit(() -> {
            ui.onGameInsertionReply(turnResult);
        });
    }

    /**
     * Handles the event when the player connection status is updated.
     *
     * @param playerInfo The list of player playerInfo.
     */
    @Override
    @ClientFunction
    public void onPlayerConnectionStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        // logger.warn("onPlayerConnectionStatusUpdateEvent(turnResult={})", playerInfo);
        // not used
    }

    @Override
    public void onChatModelUpdate(List<ChatTextMessage> messages) {
        asyncExecutor.submit(() -> {
            ui.chatModelUpdate(messages);
        });
    }

    /**
     * Handles the event when the game is ended.
     */
    @Override
    @ClientFunction
    public void onGameEndedEvent() {
        // not used
    }


    ///////////////////////////////////////////////////////////////// LobbyViewEventHandler

    /**
     * Sends a status update request to the server.
     */
    @Override
    public void sendStatusUpdateRequest() {
        try {
            logger.warn("sendStatusUpdateRequest() sending RMI call serverStatusRequest()");
            gateway.serverStatusRequest(this);
            logger.warn("sendStatusUpdateRequest() returning RMI call serverStatusRequest()");
        } catch (RemoteException e) {
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
        try {
            logger.warn("sendGameStartRequest() sending RMI call gameStartRequest({}, {})", username, mode);
            gateway.gameStartRequest(username, mode, config.protocol(), this);
            logger.warn("sendGameStartRequest() returning RMI call gameStartRequest({}, {})", username, mode);
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
            logger.warn("sendGameConnectionRequest() sending RMI call gameConnectionRequest({})", username);
            gateway.gameConnectionRequest(username, config.protocol(), this);

            logger.warn("sendGameConnectionRequest() returning RMI call gameConnectionRequest({})", username);
        } catch (RemoteException e) {
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
     */
    @Override
    public void onViewSendMessage(String senderUsername, MessageRecipient recipient, String text) {
        try {
            gateway.sendTextMessage(senderUsername, recipient, text);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the event when the view requests to quit the game.
     */
    @Override
    public void onViewQuitGame() {

    }
}
