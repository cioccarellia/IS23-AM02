package it.polimi.ingsw.app.server;

import it.polimi.ingsw.controller.server.connection.ClientConnection;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.services.ClientService;

import java.util.*;

/**
 * Manages the client connections to the server.
 */
public class ClientConnectionsManager {

    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    private final Map<String, ClientConnection> connections = new HashMap<>();

    /**
     * Returns the number of client connections.
     *
     * @return The number of client connections.
     */
    public int size() {
        return connections.size();
    }

    /**
     * Returns a collection of all client connections.
     *
     * @return A collection of all client connections.
     */
    public Collection<ClientConnection> values() {
        return connections.values();
    }

    /**
     * Adds a new client connection with the specified details.
     *
     * @param username      The username of the client.
     * @param proto         The client protocol.
     * @param isHost        Indicates if the client is the host of the game.
     * @param initialStatus The initial connection status of the client.
     * @param remoteService The remote service for the client.
     */
    public void add(String username, ClientProtocol proto, boolean isHost, ConnectionStatus initialStatus, ClientService remoteService) {
        var newConnection = new ClientConnection(username, proto, isHost, initialStatus, remoteService);
        connections.put(username, newConnection);
    }

    /**
     * Retrieves the client connection with the specified username.
     *
     * @param username The username of the client.
     * @return The client connection.
     */
    public ClientConnection get(String username) {
        return connections.get(username);
    }

    /**
     * Sets the current connection status for a specific user
     */
    public void setConnectionStatus(String username, ConnectionStatus status) {
        assert containsUsername(username);
        connections.get(username).setLastSeen(Calendar.getInstance().getTime());
        connections.get(username).setStatus(status);
    }

    /**
     * Registers an interaction for a specific client, updating the last seen time and setting the connection status to OPEN.
     *
     * @param username The username of the client.
     */
    public void registerInteraction(String username) {
        assert containsUsername(username);
        connections.get(username).setLastSeen(Calendar.getInstance().getTime());
        setConnectionStatus(username, ConnectionStatus.OPEN);
    }

    /**
     * Whether a given username belongs to the session
     */
    public boolean containsUsername(String username) {
        return connections.containsKey(username);
    }

    /**
     * Checks if the client with the specified username is disconnected.
     *
     * @param username The username of the client.
     * @return True if the client is disconnected, false otherwise.
     */
    public boolean isClientDisconnected(String username) {
        return getDisconnectedClientUsernames().contains(username);
    }

    /**
     * Checks if any client is disconnected.
     *
     * @return True if at least one client is disconnected, false otherwise.
     */
    public boolean isAnyClientDisconnected() {
        return connections.values().stream().anyMatch(it -> it.getStatus() == ConnectionStatus.DISCONNECTED);
    }

    /**
     * Retrieves a list of usernames for disconnected clients.
     *
     * @return A list of usernames for disconnected clients.
     */
    public List<String> getUsernames() {
        return connections.values().stream().map(ClientConnection::getUsername).toList();
    }

    /**
     * Retrieves a list of usernames for disconnected clients.
     *
     * @return A list of usernames for disconnected clients.
     */
    public List<String> getDisconnectedClientUsernames() {
        return connections.values().stream().filter(it -> it.getStatus() == ConnectionStatus.DISCONNECTED).map(ClientConnection::getUsername).toList();
    }

    /**
     * Retrieves a list of usernames for disconnected or closed clients.
     *
     * @return A list of usernames for disconnected or closed clients.
     */
    public List<String> getDisconnectedOrClosedClientUsernames() {
        return connections.values()
                .stream()
                .filter(it -> it.getStatus() == ConnectionStatus.DISCONNECTED || it.getStatus() == ConnectionStatus.CLOSED)
                .map(ClientConnection::getUsername).toList();
    }

    /**
     * Checks if any client is closed.
     *
     * @return True if at least one client is closed, false otherwise.
     */
    public boolean isAnyClientClosed() {
        return connections.values().stream().anyMatch(it -> it.getStatus() == ConnectionStatus.CLOSED);
    }
}
