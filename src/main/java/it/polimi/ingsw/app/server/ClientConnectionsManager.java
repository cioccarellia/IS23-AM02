package it.polimi.ingsw.app.server;

import it.polimi.ingsw.controller.server.connection.ClientConnection;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.services.ClientService;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClientConnectionsManager {

    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    private final Map<String, ClientConnection> connections = new HashMap<>();

    public int size() {
        return connections.size();
    }

    public Collection<ClientConnection> values() {
        return connections.values();
    }

    public void add(String username, ClientProtocol proto, boolean isHost, ConnectionStatus initialStatus, ClientService remoteService) {
        var newConnection = new ClientConnection(username, proto, isHost, initialStatus, remoteService);
        connections.put(username, newConnection);
    }

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
}
