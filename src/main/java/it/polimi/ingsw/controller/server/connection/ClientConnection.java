package it.polimi.ingsw.controller.server.connection;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;

/**
 * Class representing a virtual connection with a client app.
 */
public class ClientConnection {

    /**
     * The username associated with the current connection.
     */
    private final String username;

    private final ClientProtocol proto;

    /**
     * Status of the connection with the client
     */
    private ConnectionStatus status;

    public ClientConnection(String _username, ClientProtocol _proto, ConnectionStatus _status) {
        username = _username;
        proto = _proto;
        status = _status;
    }

    public String getUsername() {
        return username;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    public ClientProtocol getProto() {
        return proto;
    }

}