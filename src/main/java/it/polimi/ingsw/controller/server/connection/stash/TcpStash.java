package it.polimi.ingsw.controller.server.connection.stash;

import it.polimi.ingsw.network.tcp.TcpConnectionHandler;
import it.polimi.ingsw.services.ClientService;

public class TcpStash extends ProtocolStash {
    /**
     * Remote client socket hostname
     */
    private String hostname;

    // It's really a TcpConnectionHandler
    private TcpConnectionHandler tcpConnectionHandler;

    @Override
    public ClientService getClientConnectionService() {
        return tcpConnectionHandler;
    }

    @Override
    public String getClientHostname() {
        return hostname;
    }


    @Override
    public void setClientConnectionService(ClientService service) {
        this.tcpConnectionHandler = (TcpConnectionHandler) service;
    }

    public void setClientHostname(String hostname) {
        this.hostname = hostname;
    }
}
