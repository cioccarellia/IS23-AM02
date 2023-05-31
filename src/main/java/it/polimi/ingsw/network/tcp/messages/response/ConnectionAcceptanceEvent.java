package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.network.tcp.messages.replies.Reply;

public class ConnectionAcceptanceEvent extends Reply {
    private final String username;

    public ConnectionAcceptanceEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "ConnectionAcceptanceEvent{" +
                "username='" + username + '\'' +
                '}';
    }
}
