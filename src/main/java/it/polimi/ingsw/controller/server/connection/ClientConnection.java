package it.polimi.ingsw.controller.server.connection;

import it.polimi.ingsw.controller.server.connection.stash.RmiStash;
import it.polimi.ingsw.controller.server.connection.stash.TcpStash;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;

/**
 * Class representing a virtual connection with a client app.
 * It contains protocol-specific data, as well as general functions and
 * utilities to handle communication and internal representation
 * for that specific client.
 */
public class ClientConnection {

    /**
     * The username associated with the current connection.
     */
    private final String username;

    /**
     * Protocol
     */
    private final ClientProtocol proto;

    private final RmiStash rmiStash = new RmiStash();
    private final TcpStash tcpStash = new TcpStash();

    /**
     * Status of the connection with the client
     */
    private ConnectionStatus status;

    public ClientConnection(String username, ClientProtocol proto, ConnectionStatus status) {
        this.username = username;
        this.proto = proto;
        this.status = status;
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

    public RmiStash getRmiStash() {
        return rmiStash;
    }

    public RmiStash getTcpStash() {
        return rmiStash;
    }

}