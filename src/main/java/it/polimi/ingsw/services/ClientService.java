package it.polimi.ingsw.services;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import javafx.util.Pair;

import java.util.List;

public interface ClientService {

    String NAME = "ClientService";


    // Initialization
    @ClientFunction
    void injectUsername(String string);

    @ClientFunction
    void serverStatusUpdateEvent(ServerStatus status, List<Pair<String, ConnectionStatus>> playerInfo);

    @ClientFunction
    void gameCreationReply(SingleResult<GameCreationError> result);

    @ClientFunction
    void gameConnectionReply(SingleResult<GameConnectionError> result);


    @ClientFunction
    void gameStartedEvent();


    // Running
    @ClientFunction
    void modelUpdateEvent(Game game);

    @ClientFunction
    void gameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult);

    @ClientFunction
    void gameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult);


    // Connection - Disconnection
    @ClientFunction
    void playerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> usernames);

    @ClientFunction
    void gameEndedEvent();
}