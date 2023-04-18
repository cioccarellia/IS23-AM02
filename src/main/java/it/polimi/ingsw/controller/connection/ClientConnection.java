package it.polimi.ingsw.controller.connection;

/**
 * Class representing a virtual connection with a client app.
 */
public class ClientConnection {

    /**
     * The username associated with the current connection.
     */
    private final String username;

    /**
     * Status of the connection with the client
     */
    private ConnectionStatus status;

    public ClientConnection(String _username, ConnectionStatus _status) {
        username = _username;
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
}
