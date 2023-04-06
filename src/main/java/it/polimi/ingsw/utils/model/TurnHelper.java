package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerNumber;
import org.jetbrains.annotations.NotNull;

public class TurnHelper {
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static PlayerNumber getNextPlayerNumber(PlayerNumber currentNumber, @NotNull GameMode gameMode) {
        switch (gameMode) {
            case GAME_MODE_2_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PlayerNumber.PLAYER_2;
                    default -> PlayerNumber.PLAYER_1;
                };
            }
            case GAME_MODE_3_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PlayerNumber.PLAYER_2;
                    case PLAYER_2 -> PlayerNumber.PLAYER_3;
                    default -> PlayerNumber.PLAYER_1;
                };
            }
            case GAME_MODE_4_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PlayerNumber.PLAYER_2;
                    case PLAYER_2 -> PlayerNumber.PLAYER_3;
                    case PLAYER_3 -> PlayerNumber.PLAYER_4;
                    default -> PlayerNumber.PLAYER_1;
                };
            }
            default -> throw new IllegalStateException();
        }
    }

    public static PlayerNumber getPreviousPlayerNumber(PlayerNumber currentNumber, @NotNull GameMode gameMode) {
        switch (gameMode) {
            case GAME_MODE_2_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PlayerNumber.PLAYER_2;
                    default -> PlayerNumber.PLAYER_1;
                };
            }
            case GAME_MODE_3_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PlayerNumber.PLAYER_3;
                    case PLAYER_2 -> PlayerNumber.PLAYER_1;
                    default -> PlayerNumber.PLAYER_2;
                };
            }
            case GAME_MODE_4_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PlayerNumber.PLAYER_4;
                    case PLAYER_2 -> PlayerNumber.PLAYER_1;
                    case PLAYER_3 -> PlayerNumber.PLAYER_2;
                    default -> PlayerNumber.PLAYER_3;
                };
            }
            default -> throw new IllegalStateException();
        }
    }

}
