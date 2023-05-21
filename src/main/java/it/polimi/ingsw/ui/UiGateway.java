package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.gui.Scene;

import java.io.Serializable;

public interface UiGateway extends Runnable, Serializable {

    void onGameCreated();

    void modelUpdate(Game game);

    void gameSelection();

    void gameInsertion();

    void onGameEnded();

}