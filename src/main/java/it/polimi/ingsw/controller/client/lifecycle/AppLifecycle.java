package it.polimi.ingsw.controller.client.lifecycle;

import it.polimi.ingsw.model.game.Game;

public interface AppLifecycle {

    /**
     * Called upon client initialization.
     * The app should contact the server and start negotiating a game session
     */
    void initialize();

    /**
     * The current game has been cleared to proceed using its given username.
     * */
    void authorize(String username, Game game);

    /**
     * The app is terminated
     * */
    void terminate();

}
