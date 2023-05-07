package it.polimi.ingsw.networkProtocol.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.networkProtocol.tcp.messages.response.Response;

public class GameConnectionRequestReply extends Response {

    final private SingleResult<GameConnectionError> status;

    public GameConnectionRequestReply(SingleResult<GameConnectionError> status) {
        this.status = status;
    }

    public SingleResult<GameConnectionError> getStatus() {
        return status;
    }
}
