package it.polimi.ingsw.app.model;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;

public record PlayerInfo(
        String username,
        ConnectionStatus status,
        boolean isHost
) {
}
