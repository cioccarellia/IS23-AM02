package it.polimi.ingsw.network.tcp.messages.response.internal;

import it.polimi.ingsw.network.tcp.messages.request.replies.Reply;

public class UsernameInjectionEvent extends Reply {
    public String getUsername() {
        return username;
    }

    private final String username;

    public UsernameInjectionEvent(String username) {
        this.username = username;
    }
}
