package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.app.model.PlayerInfo;

import java.util.List;

public class PlayerConnectionStatusUpdateEvent extends Response {
    final List<PlayerInfo> status;

    public PlayerConnectionStatusUpdateEvent(List<PlayerInfo> status) {
        this.status = status;
    }
}