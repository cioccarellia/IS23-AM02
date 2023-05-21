package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.gui.Scene;

import java.io.Serializable;

public interface UiGateway extends Runnable, Serializable {

    void onGameCreated(Game game, Scene scene);

    void modelUpdate(Game game, Scene scene);

    void gameSelection(Game game);

    void gameInsertion();

    void onGameEnded();

}