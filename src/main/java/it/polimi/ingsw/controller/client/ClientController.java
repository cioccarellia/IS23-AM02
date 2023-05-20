package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.app.client.AppClient;
import it.polimi.ingsw.app.client.layers.network.NetworkLayer;
import it.polimi.ingsw.app.client.layers.view.ViewFactory;
import it.polimi.ingsw.app.client.layers.view.ViewLayer;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.controller.client.lifecycle.AppLifecycle;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * Client-side controller
 * Handles the view, the
 */
public class ClientController implements AppLifecycle, ClientService, ViewEventHandler, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    /**
     * User-interface.
     * The controller receives incoming event calls by the server, and forwards model centric events
     * to the user interface gateway.
     * <p>
     * The UI gateway processes those events, displays them to the user, and eventually forwards its
     * user-generated events to this controller (through {@link ViewEventHandler})
     */
    private UiGateway ui;
    private final ClientGateway gateway;

    private final ClientExhaustiveConfiguration config;

    String authUsername;
    boolean hasAuthenticatedWithServer = false;

    public ClientController(ClientGateway gateway, ClientExhaustiveConfiguration config) {
        this.gateway = gateway;
        this.config = config;
    }


    /***   Lifecycle   ***/

    @Override
    public synchronized void initialize() {
        try {
            gateway.gameStartRequest("cioccarellia", GameMode.GAME_MODE_3_PLAYERS, config.protocol(), this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized void authorize(String username) {
        // setup internal variables post-authorization
        authUsername = username;
        hasAuthenticatedWithServer = true;

        // create UI
        ui = ViewFactory.create(config.mode());

        // schedules UI initialization on its own thread
        ViewLayer.scheduleUiExecutionThread(ui, AppClient.executorService);

        // schedules ack thread
        NetworkLayer.scheduleKeepAliveThread(authUsername, gateway, AppClient.executorService);
    }

    @Override
    public synchronized void terminate() {

    }


    /***   ClientService   ***/

    public void onAcceptConnectionAndFinalizeUsername(String username) {
        authorize(username);
    }

    @Override
    public synchronized void onServerStatusUpdateEvent(ServerStatus status, List<Pair<String, ConnectionStatus>> playerInfo) {
        logger.info("Received status={}, playerInfo={}", status, playerInfo);
    }

    @Override
    public synchronized void onGameCreationReply(SingleResult<GameCreationError> result) {
        ui.onGameCreated();
    }

    @Override
    public synchronized void onGameConnectionReply(SingleResult<GameConnectionError> result) {

    }

    @Override
    public synchronized void onGameStartedEvent() {

    }

    @Override
    public synchronized void modelUpdateEvent(Game game) {
        ui.modelUpdate(game);
    }

    @Override
    public synchronized void gameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {

    }

    @Override
    public synchronized void gameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {

    }

    @Override
    public synchronized void playerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> usernames) {

    }

    @Override
    public synchronized void gameEndedEvent() {

    }


    /***   ViewEventHandler   ***/

    /**
     * @param coordinates
     */
    @Override
    public void onViewSelection(Set<Coordinate> coordinates) {

    }

    /**
     * @param column
     * @param tiles
     */
    @Override
    public void onViewInsertion(int column, List<Tile> tiles) {

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
