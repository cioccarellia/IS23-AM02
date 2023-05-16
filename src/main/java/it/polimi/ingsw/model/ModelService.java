package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.player.PlayerNumber;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

/**
 * Notifies the game of events happening as a result of the interactions between client and server.
 */
public interface ModelService {

    /**
     * The game
     */
    void onGameStarted();

    void onPlayerSelectionPhase(Set<Coordinate> coordinates);

    void onPlayerInsertionPhase(int column, List<Tile> tiles);

    void onPlayerCheckingPhase();

    void onNextTurn(PlayerNumber nextPlayerNumber);

    List<Pair<PlayerNumber, Integer>> onGameEnded();
}
