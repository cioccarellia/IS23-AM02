package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.utils.model.TurnHelper;

/**
 * Enumeration representing the 4 possible player numbers.
 * It also finds the next player and previous player through a TurnHelper method
 */
public enum PlayerNumber {
    PLAYER_1,
    PLAYER_2,
    PLAYER_3,
    PLAYER_4;

    static public PlayerNumber fromInt(int playerCount) {
        return switch (playerCount) {
            case 1 -> PLAYER_1;
            case 2 -> PLAYER_2;
            case 3 -> PLAYER_3;
            case 4 -> PLAYER_4;
            default -> throw new IllegalArgumentException("playerCount is not between 1 and 4");
        };
    }

    public PlayerNumber next(GameMode mode) {
        return TurnHelper.getNextPlayerNumber(this, mode);
    }

    public PlayerNumber previous(GameMode mode) {
        return TurnHelper.getPreviousPlayerNumber(this, mode);
    }

    public PlayerNumber next(PlayerNumber currentPlayer){
        switch (currentPlayer){
            case PLAYER_1 -> {return PLAYER_2;}
            case PLAYER_2 -> {return PLAYER_3;}
            case PLAYER_3 -> {return PLAYER_4;}
            case PLAYER_4 -> {return PLAYER_1;}
            default -> {return null;}
        }
    }
}