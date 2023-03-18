package it.polimi.ingsw.model.player;

public enum PlayerNumber {
    PLAYER_1,
    PLAYER_2,
    PLAYER_3,
    PLAYER_4;

    static public PlayerNumber fromInt(int playerCount) {
        switch (playerCount) {
            case 1: return PLAYER_1;
            case 2: return PLAYER_2;
            case 3: return PLAYER_3;
            case 4: return PLAYER_4;
            default: throw new IllegalArgumentException("playerCount is not between 1 and 4");
        }
    }
}