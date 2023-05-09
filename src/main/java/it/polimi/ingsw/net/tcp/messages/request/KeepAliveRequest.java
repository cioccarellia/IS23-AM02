package it.polimi.ingsw.net.tcp.messages.request;

public class KeepAliveRequest extends Request {

    private final String username;

    public KeepAliveRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}