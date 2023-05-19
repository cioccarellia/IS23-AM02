package it.polimi.ingsw.network.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import javafx.util.Pair;

import java.util.List;

public class ServerStatusRequestReply extends Reply {

    private final ServerStatus status;
    private final List<Pair<String, ConnectionStatus>> playerInfo;

    public ServerStatusRequestReply(ServerStatus status, List<Pair<String, ConnectionStatus>> playerInfo) {
        this.status = status;
        this.playerInfo = playerInfo;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public List<Pair<String, ConnectionStatus>> getPlayerInfo() {
        return playerInfo;
    }
}
