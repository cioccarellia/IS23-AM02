package it.polimi.ingsw.services;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientService extends Remote {

    String NAME = "ClientService";


    // Initialization

    /**
     * Communicates to the client that it has received the username
     */
    @ClientFunction
    void onAcceptConnectionAndFinalizeUsername(String string) throws RemoteException;

    @ClientFunction
    void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) throws RemoteException;

    @ClientFunction
    void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) throws RemoteException;

    @ClientFunction
    void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) throws RemoteException;

    @ClientFunction
    void onGameStartedEvent(GameModel game) throws RemoteException;


    // Running
    @ClientFunction
    void onModelUpdateEvent(GameModel game) throws RemoteException;

    @ClientFunction
    void onGameSelectionTurnEvent(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) throws RemoteException;

    @ClientFunction
    void onGameInsertionTurnEvent(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) throws RemoteException;


    // Connection - Disconnection
    @ClientFunction
    void onPlayerConnectionStatusUpdateEvent(ServerStatus status, List<PlayerInfo> usernames) throws RemoteException;

    @ClientFunction
    void onChatModelUpdate(List<ChatTextMessage> messages) throws RemoteException;

    @ClientFunction
    void onGameEndedEvent() throws RemoteException;
}