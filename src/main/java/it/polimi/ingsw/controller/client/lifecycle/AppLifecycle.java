package it.polimi.ingsw.controller.client.lifecycle;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.game.GameGateway;

public interface AppLifecycle {

    /**
     * Called upon client initialization.
     * The app should contact the server and start negotiating a game session
     */
    void initialize();


    void onLobbyUiReady(LobbyGateway gateway);

    void onGameUiReady(GameGateway gateway);


    /**
     * The current game has been cleared to proceed using its given username.
     */
    void authorize(String username, Game game);

    /**
     * The app is terminated
     */
    void terminate();

}
