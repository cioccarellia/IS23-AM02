package it.polimi.ingsw.networkProtocol.tcp.messages.request;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.game.GameMode;

public class GameConnectionRequest extends Request {

    private final GameMode mode;
    private final String username;
    private final ClientProtocol protocol;

    public GameConnectionRequest(GameMode mode, String username, ClientProtocol protocol) {
        this.mode = mode;
        this.username = username;
        this.protocol = protocol;
    }


    public GameMode getMode() {
        return mode;
    }

    public String getUsername() {
        return username;
    }

    public ClientProtocol getProtocol() {
        return protocol;
    }
}