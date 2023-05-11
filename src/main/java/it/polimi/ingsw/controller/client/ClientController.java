package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.gateways.Gateway;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.net.rmi.ClientService;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

public class ClientController implements ClientService, ViewEventHandler {

    UiGateway ui;
    Gateway gateway;

    public ClientController(Gateway gateway, UiGateway ui) {
        this.ui = ui;
        this.gateway = gateway;


    }

    @Override
    public void serverStatusResponse(ServerStatus status) {

    }

    @Override
    public void gameStartedEvent() {

    }

    @Override
    public void modelUpdateEvent(Game game) {
        ui.modelUpdate(game);
    }

    @Override
    public void gameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {

    }

    @Override
    public void gameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {

    }

    @Override
    public void playerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> usernames) {

    }

    @Override
    public void gameStandbyEvent() {

    }

    @Override
    public void gameResumedEvent() {

    }

    @Override
    public void gameEndedEvent() {

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
