package it.polimi.ingsw.controller.server.router;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.services.ClientService;

public class Router {

    private final ClientConnectionsManager support;
    private final BroadcastClientService broadcast;

    public Router(ClientConnectionsManager support) {
        this.support = support;
        this.broadcast = new BroadcastClientService(support);
    }

    public ClientService route(String username) {
        assert support.containsUsername(username);
        return support.get(username);
    }

    public ClientService broadcast() {
        return broadcast;
    }
}
