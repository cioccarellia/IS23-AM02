package it.polimi.ingsw.ui.game;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;

import java.io.Serializable;

public interface GameGateway extends Serializable {

    /**
     * Called when the game is started
     */
    void onGameCreated();

    /**
     * Called to notify an update in the game data model
     */
    void modelUpdate(Game game);

    /**
     * Calls to notify about a previously-submitted selection attempt
     */
    void onGameSelectionReply(SingleResult<TileSelectionFailures> turnResult);

    /**
     * Calls to notify about a previously-submitted insertion attempt
     */
    void onGameInsertionReply(SingleResult<BookshelfInsertionFailure> turnResult);
}