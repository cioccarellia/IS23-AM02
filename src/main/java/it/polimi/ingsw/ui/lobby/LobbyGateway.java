package it.polimi.ingsw.ui.lobby;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;

import java.io.Serializable;
import java.util.List;

/**
 * The LobbyGateway interface represents the gateway for communication between the lobby view
 * and the server. It provides methods to handle server status updates, game creation replies,
 * game connection replies, and termination of the lobby.
 */
public interface LobbyGateway extends Serializable {

    /**
     * Handles the server status update by receiving the current server status and player information.
     *
     * @param status     the current server status
     * @param playerInfo the list of player information
     */
    void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo);

    /**
     * Handles the game creation reply by receiving the result of the game creation request.
     *
     * @param result the result of the game creation request, either success or failure
     */
    void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result);

    /**
     * Handles the game connection reply by receiving the result of the game connection request.
     *
     * @param result the result of the game connection request, either success or failure
     */
    void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result);

    /**
     * Terminates the lobby and performs necessary cleanup operations.
     */
    void kill();
}
