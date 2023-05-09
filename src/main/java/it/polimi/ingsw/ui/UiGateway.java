package it.polimi.ingsw.ui;

public interface UiGateway {

    int onGameStarted();

    int modelUpdate();

    int gameSelection();

    int gameInsertion();

    int onGameEnded();

}