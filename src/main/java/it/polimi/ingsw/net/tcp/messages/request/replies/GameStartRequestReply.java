package it.polimi.ingsw.net.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;

public class GameStartRequestReply extends Reply {

    private final SingleResult<GameStartError> status;

    public GameStartRequestReply(SingleResult<GameStartError> status) {
        this.status = status;
    }

    public SingleResult<GameStartError> getStatus() {
        return status;
    }
}
