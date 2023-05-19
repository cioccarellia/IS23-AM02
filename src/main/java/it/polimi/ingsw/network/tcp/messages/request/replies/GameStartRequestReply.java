package it.polimi.ingsw.network.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.failures.GameStartError;

public class GameStartRequestReply extends Reply {

    private final GameStartError status;

    public GameStartRequestReply(GameStartError status) {
        this.status = status;
    }

    public GameStartError getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "GameStartRequestReply{" +
                "status=" + status +
                '}';
    }
}
