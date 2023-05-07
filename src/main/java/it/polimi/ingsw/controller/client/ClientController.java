package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.net.rmi.ClientService;
import it.polimi.ingsw.ui.UiGateway;
import javafx.util.Pair;

import java.util.List;

public class ClientController implements ClientService {

    UiGateway ui;

    public ClientController(UiGateway _ui) {
        ui = _ui;
    }

    @Override
    public void serverStatusResponse(ServerStatus status) {

    }

    @Override
    public void gameStartedEvent() {

    }

    @Override
    public void modelUpdateEvent(Game game) {

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
}
