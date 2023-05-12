package it.polimi.ingsw.controller.server.connection.stash;

import it.polimi.ingsw.net.rmi.ClientService;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation specific properties
 * */
public class RmiStash {

    /**
     * Used to keep a remote reference on a client's controller, so that the server (once the connection
     * has been set up) can remote-invoke methods on the client's controller
     * */
    private @Nullable ClientService clientService = null;

    /**
     * Whether the current connection has been set up (the client called syn on the server and provided
     * its controller remote reference)
     * */
    private boolean hasSynchronizedRmiConnection = false;

    /**
     * The RMI hostname for the associated player (technically, it's the TCP socket client address for
     * the RMI implementation)
     * */
    private String hostname;

    public void setClientService(@Nullable ClientService clientService) {
        this.clientService = clientService;
        hasSynchronizedRmiConnection = true;
    }

    public @Nullable ClientService getClientService() {
        return clientService;
    }

    public boolean hasSynchronizedRmiConnection() {
        return hasSynchronizedRmiConnection;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}