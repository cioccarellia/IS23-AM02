package it.polimi.ingsw.net.tcp.messages.request.replies;

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
