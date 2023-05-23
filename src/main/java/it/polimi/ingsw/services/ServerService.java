package it.polimi.ingsw.services;

import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * High level protocol for client-to-server communication (response and reply).
 * Methods can use an anonymous remote service ({@link ClientService}) to invoke methods on the client.
 * This is used for unauthenticated request to be able to have an async response
 */
public interface ServerService extends Remote {

    String NAME = "ServerService";

    /**
     * Requests an updated value for the current {@link ServerStatus}.
     * This should be used in the connection (pre-game) phase.
     */
    @ServerFunction
    void serverStatusRequest(ClientService remoteService) throws RemoteException;

    /**
     * Requests for the current server to start a game.
     *
     * @param mode     defines the game mode for the soon-to-be-created game
     * @param protocol defines the communication protocol used by the current client with the server.
     * @param username defines the username for the current client (which is the first player joining).
     */
    @ServerFunction
    void gameStartRequest(String username, GameMode mode, ClientProtocol protocol, ClientService remoteService) throws RemoteException;

    /**
     * Requests for the client to connect to the game and be associated with a player.
     *
     * @param protocol defines the communication protocol used by the requesting client with the server.
     * @param username defines the username for the requesting client.
     */
    @ServerFunction
    void gameConnectionRequest(String username, ClientProtocol protocol, ClientService remoteService) throws RemoteException;


    /**
     * Submits a turn response, containing the player actions for the selection turn
     */
    @ServerFunction
    void gameSelectionTurnResponse(String username, Set<Coordinate> selection) throws RemoteException;


    /**
     * Submits a turn response, containing the player actions for the insertion turn
     */
    @ServerFunction
    void gameInsertionTurnResponse(String username, List<Tile> tiles, int column) throws RemoteException;


    /**
     * Sends an acknowledgement message.
     */
    @ServerFunction
    void keepAlive(String username) throws RemoteException;
}