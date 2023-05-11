package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.game.Game;

public interface UiGateway {

    int onGameStarted();

    int modelUpdate(Game game);

    int gameSelection();

    int gameInsertion();

    int onGameEnded();

}