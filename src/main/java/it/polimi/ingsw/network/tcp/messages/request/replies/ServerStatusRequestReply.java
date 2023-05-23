package it.polimi.ingsw.network.tcp.messages.request.replies;

import it.polimi.ingsw.app.model.AggregatedPlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;

import java.util.List;

public class ServerStatusRequestReply extends Reply {

    private final ServerStatus status;
    private final List<AggregatedPlayerInfo> playerInfo;

    public ServerStatusRequestReply(ServerStatus status, List<AggregatedPlayerInfo> playerInfo) {
        this.status = status;
        this.playerInfo = playerInfo;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public List<AggregatedPlayerInfo> getPlayerInfo() {
        return playerInfo;
    }
}
