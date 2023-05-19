package it.polimi.ingsw.controller.server.connection.stash;

import it.polimi.ingsw.services.ClientService;

public abstract class ProtocolStash {

    public abstract ClientService getClientConnectionService();
    public abstract void setClientConnectionService(ClientService service);

    public abstract String getClientHostname();
    public abstract void setClientHostname(String hostname);

}
