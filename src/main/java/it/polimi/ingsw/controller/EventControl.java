package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;

import java.util.List;
import java.util.Set;

/**
 * Notifies the game of events happening as a result of the interactions between client and server.
 */
public interface EventControl {

    /**
     * The game
     */
    void onGameStarted();

    void onPlayerSelectionPhase(Set<Coordinate> coordinates);

    void onPlayerInsertionPhase(int column, List<Tile> tiles);

    void onPlayerCheckingPhase();

    void onNextTurn(String nextUsername);

    void onGameEnded();
}
