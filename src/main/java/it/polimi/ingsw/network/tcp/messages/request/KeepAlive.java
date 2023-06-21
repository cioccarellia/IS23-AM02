package it.polimi.ingsw.network.tcp.messages.request;

public class KeepAlive extends Request {

    private final String username;

    public KeepAlive(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "KeepAlive(username=" + username + ")";
    }
}