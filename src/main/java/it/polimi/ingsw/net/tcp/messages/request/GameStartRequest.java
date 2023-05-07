package it.polimi.ingsw.net.tcp.messages.request;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.game.GameMode;

public class GameStartRequest extends Request {

    private final GameMode mode;
    private final String username;
    private final ClientProtocol protocol;


    public GameStartRequest(GameMode mode, String username, ClientProtocol protocol) {
        this.mode = mode;
        this.username = username;
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
