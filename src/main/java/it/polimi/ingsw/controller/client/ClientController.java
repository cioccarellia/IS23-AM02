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
import java.util.List;
import java.util.Set;

/**
 * Client-side controller.
 * Acts as a event hub, routing incoming network calls to their respective interfaces, and back to the server
 */
public class ClientController implements AppLifecycle, ClientService, LobbyViewEventHandler, GameViewEventHandler, Serializable {

    // @Serial
    // private static final long serialVersionUID = -7833101767829378741L;

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientExhaustiveConfiguration config;

    private final ClientGateway gateway;

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


    public ClientController(ClientGateway gateway, ClientExhaustiveConfiguration config) {
        this.gateway = gateway;
        this.config = config;
    }


    /***   Lifecycle   ***/

    @Override
    public synchronized void initialize() {
        // initialize
        lobby = ViewFactory.createLobbyUi(config.mode(), this);


        //ViewLayer.scheduleLobbyExecutionThread(lobby, executorService);

        try {
            gateway.serverStatusRequest(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Current client has acquired its username
     */
    @Override
    public synchronized void authorize(String username, Game game) {
        // setup internal variables post-authorization
        ownerUsername = username;
        hasAuthenticatedWithServerAndExchangedUsername = true;

        // schedules ack thread
        ClientNetworkLayer.scheduleKeepAliveThread(ownerUsername, gateway, AppClient.executorService);
    }

    @Override
    public synchronized void terminate() {

    }


    @Override
    public void sendStatusUpdateRequest() {
        try {
            gateway.serverStatusRequest(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /***     Lobby     ***/


    @Override
    public void sendGameStartRequest(String username, GameMode mode) {
        try {
            gateway.gameStartRequest(username, mode, config.protocol(), this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendGameConnectionRequest(String username) {
        try {
            gateway.gameConnectionRequest(username, config.protocol(), this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }


    /***   ClientService   ***/

    @Override
    public void onAcceptConnectionAndFinalizeUsername(String username, Game game) {
        authorize(username, game);
    }

    @Override
    public synchronized void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        logger.info("Received status={}, playerInfo={}", status, playerInfo);

        lobby.onServerStatusUpdate(status, playerInfo);

    }

    @Override
    public synchronized void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        lobby.onServerCreationReply(result);
    }

    @Override
    public synchronized void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        lobby.onServerConnectionReply(result);
    }

    @Override
    public synchronized void onGameStartedEvent(Game game) {
        lobby.kill();

        ui = ViewFactory.createGameUi(config.mode(), game, this, ownerUsername);

        // schedules UI initialization on its own thread
        // ViewLayer.scheduleGameExecutionThread(ui, AppClient.executorService);
    }

    @Override
    public synchronized void onModelUpdateEvent(Game game) {
        ui.modelUpdate(game);
    }

    @Override
    public synchronized void onGameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {
        ui.onGameSelectionReply(turnResult);
    }

    @Override
    public synchronized void onGameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {
        ui.onGameInsertionReply(turnResult);
    }

    @Override
    public synchronized void onPlayerConnectionStatusUpdateEvent(List<PlayerInfo> usernames) {
        // not used
    }

    @Override
    public synchronized void onGameEndedEvent() {
        // not used
    }


    /***   ViewEventHandler   ***/

    /**
     * @param coordinates
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
     * @param column
     * @param tiles
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
     * @param message
     */
    @Override
    public void onViewSendMessage(ChatTextMessage message) {

    }

    /**
     *
     */
    @Override
    public void onViewQuitGame() {

    }

    /**
     *
     */
    public void keepAlive() {

    }
}
