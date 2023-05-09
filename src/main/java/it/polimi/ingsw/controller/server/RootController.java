package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.server.connection.ClientConnection;

import java.util.HashMap;
import java.util.Map;

public class RootController {

    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    protected final Map<String, ClientConnection> connections = new HashMap<>();


}
