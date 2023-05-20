package it.polimi.ingsw.controller.client.lifecycle;

import it.polimi.ingsw.model.game.Game;

public interface AppLifecycle {

    void initialize();


    void authorize(String username, Game game);

    void terminate();

}
