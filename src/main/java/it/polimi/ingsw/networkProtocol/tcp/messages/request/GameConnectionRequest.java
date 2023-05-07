package it.polimi.ingsw.networkProtocol.tcp.messages.request;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;

public class GameConnectionRequest extends Request {

    private final String username;
    private final ClientProtocol protocol;

    public GameConnectionRequest(String username, ClientProtocol protocol) {
        this.username = username;
        this.protocol = protocol;
    }

    public String getUsername() {
        return username;
    }

    public ClientProtocol getProtocol() {
        return protocol;
    }
}