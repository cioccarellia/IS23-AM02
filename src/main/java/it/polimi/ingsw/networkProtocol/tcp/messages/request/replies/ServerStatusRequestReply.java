package it.polimi.ingsw.networkProtocol.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.networkProtocol.tcp.messages.response.Response;

public class ServerStatusRequestReply extends Response {

    private final ServerStatus status;

    public ServerStatusRequestReply(ServerStatus status) {
        this.status = status;
    }

    public ServerStatus getStatus() {
        return status;
    }
}
