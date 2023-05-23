package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.network.tcp.messages.replies.Reply;

public class ConnectionAcceptanceEvent extends Reply {
    private final String username;
    private final Game model;

    public ConnectionAcceptanceEvent(String username, Game model) {
        this.username = username;
        this.model = model;
    }

    public String getUsername() {
        return username;
    }

    public Game getModel() {
        return model;
    }
}
