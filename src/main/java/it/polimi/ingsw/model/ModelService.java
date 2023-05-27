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
     * Notifies the game that the game has started
     */
    void onGameStarted();

    /**
     * Notifies the game of the player selection phase with the specified coordinates.
     *
     * @param coordinates the coordinates selected by the player
     */
    void onPlayerSelectionPhase(Set<Coordinate> coordinates);

    /**
     * Notifies the game of the player insertion phase with the specified column and tiles.
     *
     * @param column the column where the tiles are being inserted
     * @param tiles  the tiles being inserted
     */
    void onPlayerInsertionPhase(int column, List<Tile> tiles);

    /**
     * Notifies the game that the current player's turn is ending.
     */
    void onPlayerTurnEnding();

    /**
     * Notifies the game that the next turn is starting for the specified player.
     *
     * @param nextPlayerUsername the username of the next player
     */
    void onNextTurn(String nextPlayerUsername);

    /**
     * Notifies the game that the next turn is forced for the specified player.
     *
     * @param nextPlayer the username of the player for the forced next turn
     */
    void onForcedNextTurn(String nextPlayer);

    /**
     * Checks if the game is currently in standby mode.
     *
     * @return true if the game is in standby mode, false otherwise
     */
    @CheckReturnValue
    boolean onStandby();

    /**
     * Resumes the game from the standby mode.
     *
     * @return true if the game was successfully resumed, false otherwise
     */
    @CheckReturnValue
    boolean onResume();

    /**
     * Notifies the game that the game has ended.
     */
    void onGameEnded();
}
