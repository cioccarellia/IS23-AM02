package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.network.tcp.messages.SingleSealable;
import org.jetbrains.annotations.Nullable;

public class GameSelectionTurnResponse extends Response implements SingleSealable<TileSelectionFailures> {

    @Nullable
    private final TileSelectionFailures tileSelectionFailures;

    public GameSelectionTurnResponse(SingleResult<TileSelectionFailures> result) {
        tileSelectionFailures = switch (result) {
            case SingleResult.Failure<TileSelectionFailures> failure -> failure.error();
            case SingleResult.Success<TileSelectionFailures> success -> null;
        };
    }

    public @Nullable TileSelectionFailures getTileSelectionFailures() {
        return tileSelectionFailures;
    }

    @Override
    public String toString() {
        return "GameSelectionTurnResponse{" +
                "tileSelectionFailures=" + tileSelectionFailures +
                '}';
    }

    @Override
    public SingleResult<TileSelectionFailures> seal() {
        return tileSelectionFailures == null ?
                new SingleResult.Success<>() :
                new SingleResult.Failure<>(tileSelectionFailures);
    }
}
