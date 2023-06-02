package it.polimi.ingsw.controller.client.gateways;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.services.ServerService;

import java.io.Serializable;

/**
 * Abstracts a client-to-server server model and communication interface
 * implementing all the protocol-related business logic
 */
public abstract class ClientGateway implements ServerService, Serializable {

    protected ClientController controller;

    abstract public ServerService getS();

    public void linkController(final ClientController controller) {
        this.controller = controller;
    }
}