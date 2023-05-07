package it.polimi.ingsw.networkProtocol.rmi;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import javafx.util.Pair;

import java.rmi.Remote;
import java.util.List;

public interface ClientService extends Remote {

    String NAME = "ClientService";

    // Initialization
    void serverStatusResponse(ServerStatus status);

    void gameStartedEvent();


    // Running
    void modelUpdateEvent(Game game);

    void gameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult);

    void gameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult);


    //Connection - Disconnection
    void playerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> usernames);

    void gameStandbyEvent();

    void gameResumedEvent();

    void gameEndedEvent();
}