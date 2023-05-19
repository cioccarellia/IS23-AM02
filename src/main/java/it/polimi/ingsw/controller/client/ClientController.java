package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ClientController implements ClientService, ViewEventHandler, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private transient UiGateway ui;
    private transient final ClientGateway clientGateway;

    String authUsername;

    public ClientController(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }



    /***   ClientService   ***/
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

    public void onAcceptConnectionAndFinalizeUsername(String username) {
        authUsername = username;
    }

    /**
     * @param coordinates
     */
    @Override
    public void onSelection(Set<Coordinate> coordinates) {

    }

    /**
     * @param column
     * @param tiles
     */
    @Override
    public void onInsertion(int column, List<Tile> tiles) {

    }

    /**
     * @param message
     */
    @Override
    public void sendMessage(ChatTextMessage message) {

    }

    /**
     *
     */
    @Override
    public void quitGame() {

    }

    /**
     *
     */
    @Override
    public void keepAlive() {

    }
}
