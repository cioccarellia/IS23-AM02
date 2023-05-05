package it.polimi.ingsw.networkProtocol.tcp.messages.response;

import it.polimi.ingsw.controller.ServerStatus;

public class ServerStatusResponse extends Response {
    private final ServerStatus status;

    public ServerStatusResponse(ServerStatus status) {
        this.status = status;
    }

    public ServerStatus getStatus() {
        return status;
    }
}
