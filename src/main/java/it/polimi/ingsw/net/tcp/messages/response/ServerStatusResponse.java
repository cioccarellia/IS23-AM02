package it.polimi.ingsw.net.tcp.messages.response;

import it.polimi.ingsw.controller.server.model.ServerStatus;

public class ServerStatusResponse extends Response {
    private final ServerStatus status;

    public ServerStatusResponse(ServerStatus status) {
        this.status = status;
    }

    public ServerStatus getStatus() {
        return status;
    }
}
