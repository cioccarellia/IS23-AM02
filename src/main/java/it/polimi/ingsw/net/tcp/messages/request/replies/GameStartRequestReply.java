package it.polimi.ingsw.net.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;
import it.polimi.ingsw.net.tcp.messages.response.Response;

public class GameStartRequestReply extends Response {

    private final SingleResult<GameStartError> status;

    public GameStartRequestReply(SingleResult<GameStartError> status) {
        this.status = status;
    }

    public SingleResult<GameStartError> getStatus() {
        return status;
    }
}
