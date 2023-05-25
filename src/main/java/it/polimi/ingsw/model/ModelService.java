package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.CheckReturnValue;

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

    void onNextTurn(String nextPlayerUsername);

    void onForcedNextTurn(String nextPlayer);

    @CheckReturnValue
    boolean onStandby();

    @CheckReturnValue
    boolean onResume();

    void onGameEnded();
}
