package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.app.model.AggregatedPlayerInfo;

import java.util.List;

public class PlayerConnectionStatusUpdateEvent extends Response {
    List<AggregatedPlayerInfo> status;

    public PlayerConnectionStatusUpdateEvent(List<AggregatedPlayerInfo> status) {
        this.status = status;
    }
}