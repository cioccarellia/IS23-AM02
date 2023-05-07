package it.polimi.ingsw.networkProtocol.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.networkProtocol.tcp.messages.response.Response;

public class GameSelectionTurnRequestReply extends Response {

    private final SingleResult<TileSelectionFailures> turnResult;

    public GameSelectionTurnRequestReply(SingleResult<TileSelectionFailures> result) {
        this.turnResult = result;
    }

    public SingleResult<TileSelectionFailures> getTurnResult() {
        return turnResult;
    }
}
