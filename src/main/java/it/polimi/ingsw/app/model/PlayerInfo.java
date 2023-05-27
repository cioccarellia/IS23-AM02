package it.polimi.ingsw.app.model;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;


/**
 * Represents the information of a player in the game.
 *
 * @param username the username of the player
 * @param status   the connection status of the player
 * @param isHost   a flag, true if the player is the host, false otherwise
 */
public record PlayerInfo(
        String username,
        ConnectionStatus status,
        boolean isHost
) {
}
