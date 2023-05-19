package it.polimi.ingsw.controller.server.connection.stash;

import it.polimi.ingsw.services.ClientService;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation specific properties
 */
public class RmiStash extends ProtocolStash {

    /**
     * Used to keep a RMI remote reference on a client's controller, so that the server
     * (once the connection has been set up) can remote-invoke methods on the client's controller
     */
    private @Nullable ClientService clientService = null;

    /**
     * Whether the current connection has been set up (the client called syn on the server and provided
     * its controller remote reference, alongside its username)
     */
    private boolean hasSynchronizedRmiConnection = false;

    /**
     * The RMI hostname for the associated player
     * (technically, it's the TCP socket client's address for the RMI internal implementation)
     */
    private String hostname;


    @Override
    public ClientService getClientConnectionService() {
        return clientService;
    }

    @Override
    public String getClientHostname() {
        return hostname;
    }


    public void setClientConnectionService(@Nullable ClientService clientService) {
        this.clientService = clientService;
        hasSynchronizedRmiConnection = true;
    }

    public void setClientHostname(String hostname) {
        this.hostname = hostname;
    }


    public boolean hasSynchronizedRmiConnection() {
        return hasSynchronizedRmiConnection;
    }
}