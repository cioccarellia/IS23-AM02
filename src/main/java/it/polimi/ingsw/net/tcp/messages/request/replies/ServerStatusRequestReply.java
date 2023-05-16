package it.polimi.ingsw.net.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.model.ServerStatus;

public class ServerStatusRequestReply extends Reply {

    private final ServerStatus status;

    public ServerStatusRequestReply(ServerStatus status) {
        this.status = status;
    }

    public ServerStatus getStatus() {
        return status;
    }
}
