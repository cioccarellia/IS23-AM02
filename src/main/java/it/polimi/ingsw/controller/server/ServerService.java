package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.net.rmi.ClientService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * High level protocol for client-to-server communication (response and reply)
 */
public interface ServerService extends Remote {

    String NAME = "ServerService";

    /**
     * Sends a synchronization message to correctly configure the connection.
     */
    void synchronizeConnectionLayer(String username, ClientService service) throws RemoteException;

    /**
     * Requests an updated value for the current {@link ServerStatus}.
     * This should be used in the connection (pre-game) phase.
     *
     * @return the current server status
     */
    ServerStatus serverStatusRequest() throws RemoteException;

    /**
     * Requests for the current server to start a game.
     *
     * @param mode     defines the game mode for the soon-to-be-created game
     * @param protocol defines the communication protocol used by the current client with the server.
     * @param username defines the username for the current client (which is the first player joining).
     */
    SingleResult<GameStartError> gameStartRequest(GameMode mode, String username, ClientProtocol protocol) throws RemoteException;

    /**
     * Requests for the client to connect to the game and be associated with a player.
     *
     * @param protocol defines the communication protocol used by the requesting client with the server.
     * @param username defines the username for the requesting client.
     */
    SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) throws RemoteException;

    // SingleResult<StatusError> gameTeardownRequest();


    /**
     * Submits a turn response, containing the player actions for the selection turn
     */
    SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) throws RemoteException;


    /**
     * Submits a turn response, containing the player actions for the insertion turn
     */
    SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) throws RemoteException;


    /**
     * Sends an acknowledgement call.
     */
    void keepAlive(String player) throws RemoteException;
}