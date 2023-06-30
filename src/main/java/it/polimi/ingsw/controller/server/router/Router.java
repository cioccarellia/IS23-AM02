package it.polimi.ingsw.controller.server.router;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.async.AsyncExecutor;
import it.polimi.ingsw.services.ClientService;

import java.util.List;

/**
 * Class that simplifies sending messages/calls to a client / set of clients
 */
public class Router {

    /**
     * Connection manager, used to extract the relevant data for communicating with users
     */
    private final ClientConnectionsManager support;

    /**
     * Default {@link BroadcastClientService}, with all users as target (full broadcast)
     */
    private final BroadcastClientService defaultBroadcast;

    public Router(ClientConnectionsManager support) {
        this.support = support;
        this.defaultBroadcast = new BroadcastClientService(support);
    }

    public ClientService route(String username) {
        assert support.containsUsername(username);
        return support.get(username);
    }

    public ClientService broadcast() {
        return defaultBroadcast;
    }

    public ClientService broadcastExcluding(List<String> usernames) {
        return new BroadcastClientService(support, usernames);
    }

    public ClientService broadcastExcluding(String... usernames) {
        return new BroadcastClientService(support, List.of(usernames));
    }

    public ClientService broadcastExcluding(AsyncExecutor asyncExecutor, List<String> usernames) {
        return new BroadcastClientService(support, usernames, asyncExecutor);
    }

    public ClientService broadcastExcluding(AsyncExecutor asyncExecutor, String... usernames) {
        return new BroadcastClientService(support, List.of(usernames), asyncExecutor);
    }
}
