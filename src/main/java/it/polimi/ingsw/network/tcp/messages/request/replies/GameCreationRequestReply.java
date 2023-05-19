package it.polimi.ingsw.network.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.network.tcp.messages.request.SingleSealable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameCreationRequestReply extends Reply implements SingleSealable<GameCreationError> {

    @Nullable
    private final GameCreationError gameCreationError;

    public GameCreationRequestReply(@Nullable GameCreationError gameCreationError) {
        this.gameCreationError = gameCreationError;
    }

    public GameCreationRequestReply(@NotNull SingleResult<GameCreationError> status) {
        this.gameCreationError = switch (status) {
            case SingleResult.Failure<GameCreationError> failure -> failure.error();
            case SingleResult.Success<GameCreationError> success -> null;
        };
    }

    public @Nullable GameCreationError getGameCreationError() {
        return gameCreationError;
    }

    @Override
    public String toString() {
        return "GameCreationRequestReply{" +
                "gameCreationError=" + gameCreationError +
                '}';
    }

    @Override
    public SingleResult<GameCreationError> seal() {
        return gameCreationError == null ? new SingleResult.Success<>() : new SingleResult.Failure<>(gameCreationError);
    }
}
