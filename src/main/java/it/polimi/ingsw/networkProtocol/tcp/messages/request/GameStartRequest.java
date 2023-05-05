package it.polimi.ingsw.networkProtocol.tcp.messages.request;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.game.GameMode;

public class GameStartRequest extends Request {

    private final String username;
    private final GameMode mode;
    private final ClientProtocol protocol;


    public GameStartRequest(String username, GameMode mode, ClientProtocol protocol) {
        this.username = username;
        this.mode = mode;
        this.protocol = protocol;
    }

    public String getUsername() {
        return username;
    }

    public GameMode getMode() {
        return mode;
    }

    public ClientProtocol getProtocol() {
        return protocol;
    }
}
