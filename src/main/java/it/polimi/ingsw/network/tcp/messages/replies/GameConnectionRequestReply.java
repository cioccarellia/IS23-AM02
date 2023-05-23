package it.polimi.ingsw.network.tcp.messages.replies;

import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.network.tcp.messages.TypedSealable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameConnectionRequestReply extends Reply implements TypedSealable<GameConnectionSuccess, GameConnectionError> {

    @Nullable
    private final GameConnectionSuccess gameConnectionValue;

    @Nullable
    final private GameConnectionError gameConnectionError;

    public GameConnectionRequestReply(@Nullable GameConnectionSuccess gameConnectionValue, @Nullable GameConnectionError gameConnectionError) {
        this.gameConnectionValue = gameConnectionValue;
        this.gameConnectionError = gameConnectionError;
    }

    @Override
    public String toString() {
        return "GameConnectionRequestReply{" +
                "gameConnectionValue=" + gameConnectionValue +
                ", gameConnectionError=" + gameConnectionError +
                '}';
    }

    public GameConnectionRequestReply(@NotNull TypedResult<GameConnectionSuccess,GameConnectionError> status) {
        this.gameConnectionValue = switch (status) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> null;
            case TypedResult.Success<GameConnectionSuccess, GameConnectionError> success -> success.value();
        };

        this.gameConnectionError = switch (status) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> failure.error();
            case TypedResult.Success<GameConnectionSuccess, GameConnectionError> success -> null;
        };
    }

    public @Nullable GameConnectionError getGameConnectionError() {
        return gameConnectionError;
    }

    @Override
    public TypedResult<GameConnectionSuccess, GameConnectionError> seal() {
        return gameConnectionError == null ?
                new TypedResult.Success<>(gameConnectionValue) :
                new TypedResult.Failure<>(gameConnectionError);
    }
}
