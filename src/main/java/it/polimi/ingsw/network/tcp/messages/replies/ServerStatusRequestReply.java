package it.polimi.ingsw.network.tcp.messages.replies;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;

import java.util.List;

public class ServerStatusRequestReply extends Reply {

    private final ServerStatus status;
    private final List<PlayerInfo> playerInfo;

    public ServerStatusRequestReply(ServerStatus status, List<PlayerInfo> playerInfo) {
        this.status = status;
        this.playerInfo = playerInfo;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public List<PlayerInfo> getPlayerInfo() {
        return playerInfo;
    }
}
