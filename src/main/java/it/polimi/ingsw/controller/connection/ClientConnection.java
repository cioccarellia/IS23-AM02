package it.polimi.ingsw.controller.connection;

public class ClientConnection {

    private final String username;
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
