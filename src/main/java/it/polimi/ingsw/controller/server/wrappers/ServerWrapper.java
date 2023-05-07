package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.controller.server.ServerService;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;

/**
 * A server wrapper is a
 * */
public abstract class ServerWrapper {

    abstract public ServerService serverService();
    abstract public ClientProtocol protocol();
}