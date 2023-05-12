package it.polimi.ingsw.app.server;

import it.polimi.ingsw.controller.server.connection.ClientConnection;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;

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

    public void add(String username, ClientProtocol proto, ConnectionStatus initialStatus) {
        connections.put(username, new ClientConnection(username, proto, initialStatus));
    }

    public ClientConnection get(String username) {
        return connections.get(username);
    }

    public void setConnectionStatus(String username, ConnectionStatus status) {
        assert containsUsername(username);
        connections.get(username).setStatus(status);
    }

    public boolean containsUsername(String username) {
        return connections.containsKey(username);
    }
}
