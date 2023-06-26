package it.polimi.ingsw.network.tcp.messages.request;

public class QuitRequest extends Request {

    private final String username;

    public QuitRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "QuitRequest(username=" + username + ")";
    }
}