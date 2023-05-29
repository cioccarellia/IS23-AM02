package it.polimi.ingsw.ui.lobby;

import it.polimi.ingsw.model.game.GameMode;

/**
 * The LobbyViewEventHandler interface represents the event handler for the lobby view.
 * It defines methods to send requests related to the lobby view, such as status update requests,
 * game start requests, and game connection requests.
 */
public interface LobbyViewEventHandler {

    /**
     * Sends a status update request to retrieve the current lobby status.
     */
    void sendStatusUpdateRequest();

    /**
     * Sends a game start request to initiate a new game.
     *
     * @param username the username of the player initiating the game
     * @param mode     the game mode chosen for the new game
     */
    void sendGameStartRequest(String username, GameMode mode);

    /**
     * Sends a game connection request to join an existing game.
     *
     * @param username the username of the player joining the game
     */
    void sendGameConnectionRequest(String username);

}
