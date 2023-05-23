package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.services.ServerService;

/**
 * A server wrapper is an abstraction layer to provide a protocol-independent interface
 */
public abstract class ServerWrapper {

    abstract public ServerService exposeServerService();

    abstract public ClientProtocol protocol();
}