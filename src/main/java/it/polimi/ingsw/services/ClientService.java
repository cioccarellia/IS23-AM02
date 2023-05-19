package it.polimi.ingsw.services;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import javafx.util.Pair;

import java.util.List;

public interface ClientService {

    String NAME = "ClientService";


    // Initialization
    void injectUsername(String string);

    void serverStatusUpdateEvent(ServerStatus status, List<Pair<String, ConnectionStatus>> playerInfo);

    void gameStartedEvent();


    // Running
    void modelUpdateEvent(Game game);

    void gameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult);

    void gameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult);


    // Connection - Disconnection
    void playerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> usernames);

    void gameEndedEvent();
}