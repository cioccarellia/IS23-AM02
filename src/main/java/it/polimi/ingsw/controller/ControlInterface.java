package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;

import java.util.List;
import java.util.Set;

public interface ControlInterface {

    void onGameStarted();

    void onPlayerQuit(String username);

    void onPlayerSelectionPhase(Set<Coordinate> coordinates);

    void onPlayerInsertionPhase(int column, List<Tile> tiles);

    void onPlayerCheckingPhase();

    void onNextTurn(String nextUsername);

    void onGameEnded();
}
