package it.polimi.ingsw.controller.server.connection;

import it.polimi.ingsw.net.rmi.ClientService;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation specific properties
 * */
public class Stash {

    private @Nullable ClientService clientService = null;

    private boolean hasInitializedRmiConnection = false;

    public @Nullable ClientService getClientService() {
        return clientService;
    }

    public void setClientService(@Nullable ClientService clientService) {
        this.clientService = clientService;
        hasInitializedRmiConnection = true;
    }

    public boolean hasInitializedRmiConnection() {
        return hasInitializedRmiConnection;
    }

}