package it.polimi.ingsw.ui;

public interface UiGateway {

    int onGameStarted();

    // board + stato
    int modelBoardUpdate();

    // single player's bookshelf
    int modelPlayerBookshelfUpdate();

    // single player's token
    int modelPlayerTokenUpdate();

    int gameSelection();

    int gameInsertion();

    int onGameEnded();

}