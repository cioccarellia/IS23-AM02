package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerNumber;
import org.jetbrains.annotations.NotNull;

public class TurnHelper {
    public PlayerNumber getNextNumber(PlayerNumber currentNumber, @NotNull GameMode gameMode) {
        switch (gameMode) {
            case GAME_MODE_2_PLAYERS -> {
                return (currentNumber == PlayerNumber.PLAYER_1) ? PlayerNumber.PLAYER_2 : PlayerNumber.PLAYER_1;
            }
            case GAME_MODE_3_PLAYERS -> {
                if (currentNumber == PlayerNumber.PLAYER_1) {
                    return PlayerNumber.PLAYER_2;
                } else if (currentNumber == PlayerNumber.PLAYER_2) {
                    return PlayerNumber.PLAYER_3;
                } else {
                    return PlayerNumber.PLAYER_1;
                }
            }
            case GAME_MODE_4_PLAYERS -> {
                if (currentNumber == PlayerNumber.PLAYER_1) {
                    return PlayerNumber.PLAYER_2;
                } else if (currentNumber == PlayerNumber.PLAYER_2) {
                    return PlayerNumber.PLAYER_3;
                } else if (currentNumber == PlayerNumber.PLAYER_3) {
                    return PlayerNumber.PLAYER_4;
                } else {
                    return PlayerNumber.PLAYER_1;
                }
            }
            default -> throw new IllegalStateException();
        }
    }
}
