package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerNumber;
import org.jetbrains.annotations.NotNull;

import static it.polimi.ingsw.model.player.PlayerNumber.*;

public class TurnHelper {
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static PlayerNumber getNextPlayerNumber(PlayerNumber currentNumber, @NotNull GameMode gameMode) {
        switch (gameMode) {
            case GAME_MODE_2_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PLAYER_2;

                    default -> PLAYER_1;
                };
            }
            case GAME_MODE_3_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PLAYER_2;
                    case PLAYER_2 -> PLAYER_3;
                    default -> PLAYER_1;
                };
            }
            case GAME_MODE_4_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PLAYER_2;
                    case PLAYER_2 -> PLAYER_3;
                    case PLAYER_3 -> PLAYER_4;
                    default -> PLAYER_1;
                };
            }
            default -> throw new IllegalStateException();
        }
    }

    public static PlayerNumber getPreviousPlayerNumber(PlayerNumber currentNumber, @NotNull GameMode gameMode) {
        switch (gameMode) {
            case GAME_MODE_2_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PLAYER_2;
                    default -> PLAYER_1;
                };
            }
            case GAME_MODE_3_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PLAYER_3;
                    case PLAYER_2 -> PLAYER_1;
                    default -> PLAYER_2;
                };
            }
            case GAME_MODE_4_PLAYERS -> {
                return switch (currentNumber) {
                    case PLAYER_1 -> PLAYER_4;
                    case PLAYER_2 -> PLAYER_1;
                    case PLAYER_3 -> PLAYER_2;
                    default -> PLAYER_3;
                };
            }
            default -> throw new IllegalStateException();
        }
    }

}
