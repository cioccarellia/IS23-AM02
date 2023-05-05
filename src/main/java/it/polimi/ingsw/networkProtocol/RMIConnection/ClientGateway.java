package it.polimi.ingsw.networkProtocol.RMIConnection;

import it.polimi.ingsw.controller.ServerStatus;
import it.polimi.ingsw.controller.connection.ConnectionStatus;
import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import javafx.util.Pair;

import java.rmi.Remote;
import java.util.List;

public interface ClientGateway extends Remote {

    String NAME = "ClientGateway";

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