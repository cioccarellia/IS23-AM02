package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.game.Game;

public interface UiGateway {

    void onGameStarted();

    void modelUpdate(Game game);

    void gameSelection();

    void gameInsertion();

    void onGameEnded();

}