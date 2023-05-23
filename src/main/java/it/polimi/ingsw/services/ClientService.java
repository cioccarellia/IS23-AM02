package it.polimi.ingsw.services;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.model.game.Game;

import java.util.List;

public interface ClientService {

    String NAME = "ClientService";


    // Initialization

    /**
     * Communicates to the client that it has received the username
     */
    @ClientFunction
    void onAcceptConnectionAndFinalizeUsername(String string, Game game);

    @ClientFunction
    void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo);

    @ClientFunction
    void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result);

    @ClientFunction
    void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result);


    @ClientFunction
    void onGameStartedEvent(Game game);


    // Running
    @ClientFunction
    void onModelUpdateEvent(Game game);

    @ClientFunction
    void onGameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult);

    @ClientFunction
    void onGameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult);


    // Connection - Disconnection
    @ClientFunction
    void onPlayerConnectionStatusUpdateEvent(List<PlayerInfo> usernames);

    @ClientFunction
    void onGameEndedEvent();
}