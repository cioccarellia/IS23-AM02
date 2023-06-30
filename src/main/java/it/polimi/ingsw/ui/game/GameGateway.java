package it.polimi.ingsw.ui.game;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;

import java.io.Serializable;
import java.util.List;


/**
 * The {@link GameGateway} interface represents the gateway for communication between the main ui view
 * and the server. It provides methods to handle server status updates, game model updates,
 * game phases changes, and game altering events.
 * This interface is meant to describe higher-order events which can be implemented in different
 * user interfaces (GUI/CLI) independently.
 * */
public interface GameGateway extends Serializable {

    /**
     * Called when the game is started
     */
    void onGameCreated();

    /**
     * Called to notify an update in the game data model
     */
    void modelUpdate(GameModel game);

    /**
     * Handles the server status update by receiving the current server status and player information.
     *
     * @param status     the current server status
     * @param playerInfo the list of player information
     */
    void onGameServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo);

    /**
     * Called to notify an update in the game data model
     */
    void chatModelUpdate(List<ChatTextMessage> messages);

    /**
     * Calls to notify about a previously-submitted selection attempt
     */
    void onGameSelectionReply(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult);

    /**
     * Calls to notify about a previously-submitted insertion attempt
     */
    void onGameInsertionReply(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult);


    /**
     * Called when the game is ended
     */
    void onGameEnded();
}