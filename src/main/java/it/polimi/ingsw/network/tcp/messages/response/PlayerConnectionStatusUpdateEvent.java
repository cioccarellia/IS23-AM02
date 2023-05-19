package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import javafx.util.Pair;

import java.util.List;

public class PlayerConnectionStatusUpdateEvent extends Response {
    List<Pair<String, ConnectionStatus>> status;

    public PlayerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> status) {
        this.status = status;
    }
}