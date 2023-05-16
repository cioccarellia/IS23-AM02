package it.polimi.ingsw.net.tcp.messages.request;

public class KeepAlive extends Request {

    private final String username;

    public KeepAlive(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}