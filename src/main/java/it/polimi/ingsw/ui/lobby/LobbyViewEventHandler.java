package it.polimi.ingsw.ui.lobby;

import it.polimi.ingsw.model.game.GameMode;

public interface LobbyViewEventHandler {

    void sendGameStartRequest(String username, GameMode mode);

    void sendGameConnectionRequest(String username);

}
