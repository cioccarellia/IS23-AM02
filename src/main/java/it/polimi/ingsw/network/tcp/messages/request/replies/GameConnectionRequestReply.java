package it.polimi.ingsw.network.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;

public class GameConnectionRequestReply extends Reply {

    final private SingleResult<GameConnectionError> status;

    public GameConnectionRequestReply(SingleResult<GameConnectionError> status) {
        this.status = status;
    }

    public SingleResult<GameConnectionError> getStatus() {
        return status;
    }
}
