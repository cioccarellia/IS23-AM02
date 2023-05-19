package it.polimi.ingsw.network.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.network.tcp.messages.request.SingleSealable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameConnectionRequestReply extends Reply implements SingleSealable<GameConnectionError> {

    @Nullable
    final private GameConnectionError gameConnectionError;

    public GameConnectionRequestReply(@Nullable GameConnectionError gameConnectionError) {
        this.gameConnectionError = gameConnectionError;
    }

    public GameConnectionRequestReply(@NotNull SingleResult<GameConnectionError> status) {
        this.gameConnectionError = switch (status) {
            case SingleResult.Failure<GameConnectionError> failure -> failure.error();
            case SingleResult.Success<GameConnectionError> success -> null;
        };
    }

    public @Nullable GameConnectionError getGameConnectionError() {
        return gameConnectionError;
    }

    @Override
    public SingleResult<GameConnectionError> seal() {
        return gameConnectionError == null ? new SingleResult.Success<>() : new SingleResult.Failure<>(gameConnectionError);
    }
}
