package it.polimi.ingsw.network.tcp.messages.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;

public class GameSelectionTurnRequestReply extends Reply {

    private final SingleResult<TileSelectionFailures> turnResult;

    public GameSelectionTurnRequestReply(SingleResult<TileSelectionFailures> result) {
        this.turnResult = result;
    }

    public SingleResult<TileSelectionFailures> getTurnResult() {
        return turnResult;
    }
}
