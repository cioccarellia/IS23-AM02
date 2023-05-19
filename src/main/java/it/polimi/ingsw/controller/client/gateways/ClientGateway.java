package it.polimi.ingsw.controller.client.gateways;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.services.ServerService;

/**
 * Abstracts a client-to-server server model and communication interface
 * implementing all the protocol-related business logic
 */
public abstract class ClientGateway implements ServerService {

    protected ClientController controller;

    public void linkController(final ClientController controller) {
        this.controller = controller;
    }
}