package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.utils.model.TurnHelper;

/**
 * Enumeration representing the 4 possible player numbers.
 * It also finds the next player and previous player through a TurnHelper method
 */
public enum PlayerNumber {
    PLAYER_1, PLAYER_2, PLAYER_3, PLAYER_4;

    static public PlayerNumber fromInt(int playerCount) {
        return switch (playerCount) {
            case 1 -> PLAYER_1;
            case 2 -> PLAYER_2;
            case 3 -> PLAYER_3;
            case 4 -> PLAYER_4;
            default -> throw new IllegalArgumentException("playerCount is not between 1 and 4");
        };
    }

    /**
     * @param mode the game mode, to know how many players are in this game
     * @return the next player number
     */
    public PlayerNumber next(GameMode mode) {
        return TurnHelper.getNextPlayerNumber(this, mode);
    }
}