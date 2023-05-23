package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.game.Game;

import java.io.Serializable;

public interface UiGateway extends Runnable, Serializable {

    /**
     * Called when the game is started
     */
    void onGameCreated();

    /**
     * Called to notify an update in the game data model
     */
    void modelUpdate(Game game);

    /**
     * Called to notify that it's the current player's turn to select
     */
    void gameSelection();

    /**
     * Called to notify that it's the current player's turn to insert
     */
    void gameInsertion();

    /**
     * Called when the game ends
     */
    void onGameEnded();
}