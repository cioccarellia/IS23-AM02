package it.polimi.ingsw.ui.game;

import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;

import java.io.Serializable;
import java.util.List;

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
}