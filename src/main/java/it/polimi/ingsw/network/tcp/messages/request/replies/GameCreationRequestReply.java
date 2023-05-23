package it.polimi.ingsw.network.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.network.tcp.messages.TypedSealable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameCreationRequestReply extends Reply implements TypedSealable<GameCreationSuccess, GameCreationError> {

    @Nullable
    private final GameCreationSuccess gameCreationValue;

    @Nullable
    private final GameCreationError gameCreationError;

    public GameCreationRequestReply(@Nullable GameCreationSuccess gameCreationSuccess, @Nullable GameCreationError gameCreationError) {
        this.gameCreationValue = gameCreationSuccess;
        this.gameCreationError = gameCreationError;
    }

    public GameCreationRequestReply(@NotNull TypedResult<GameCreationSuccess, GameCreationError> status) {
        this.gameCreationValue = switch (status) {
            case TypedResult.Failure<GameCreationSuccess, GameCreationError> failure -> null;
            case TypedResult.Success<GameCreationSuccess, GameCreationError> success -> success.value();
        };

        this.gameCreationError = switch (status) {
            case TypedResult.Failure<GameCreationSuccess, GameCreationError> failure -> failure.error();
            case TypedResult.Success<GameCreationSuccess, GameCreationError> success -> null;
        };
    }

    public @Nullable GameCreationError getGameCreationError() {
        return gameCreationError;
    }

    @Override
    public String toString() {
        return "GameCreationRequestReply{" +
                "gameCreationValue=" + gameCreationValue +
                ", gameCreationError=" + gameCreationError +
                '}';
    }

    @Override
    public TypedResult<GameCreationSuccess, GameCreationError> seal() {
        return gameCreationError == null ?
                new TypedResult.Success<>(gameCreationValue) :
                new TypedResult.Failure<>(gameCreationError);
    }
}
