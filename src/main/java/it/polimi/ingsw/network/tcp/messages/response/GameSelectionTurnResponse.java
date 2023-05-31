package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.network.tcp.messages.TypedSealable;
import org.jetbrains.annotations.Nullable;

public class GameSelectionTurnResponse extends Response implements TypedSealable<TileSelectionSuccess, TileSelectionFailures> {


    @Nullable
    private final TileSelectionSuccess tileSelectionSuccess;

    @Nullable
    private final TileSelectionFailures tileSelectionFailures;

    public GameSelectionTurnResponse(TypedResult<TileSelectionSuccess, TileSelectionFailures> result) {
        tileSelectionSuccess = switch (result) {
            case TypedResult.Failure<TileSelectionSuccess, TileSelectionFailures> failure -> null;
            case TypedResult.Success<TileSelectionSuccess, TileSelectionFailures> success -> success.value();
        };

        tileSelectionFailures = switch (result) {
            case TypedResult.Failure<TileSelectionSuccess, TileSelectionFailures> failure -> failure.error();
            case TypedResult.Success<TileSelectionSuccess, TileSelectionFailures> success -> null;
        };
    }

    public @Nullable TileSelectionFailures getTileSelectionFailures() {
        return tileSelectionFailures;
    }


    @Override
    public TypedResult<TileSelectionSuccess, TileSelectionFailures> seal() {
        return tileSelectionFailures == null ?
                new TypedResult.Success<>(tileSelectionSuccess) :
                new TypedResult.Failure<>(tileSelectionFailures);
    }

    @Override
    public String toString() {
        return "GameSelectionTurnResponse{" +
                "tileSelectionSuccess=" + tileSelectionSuccess +
                ", tileSelectionFailures=" + tileSelectionFailures +
                '}';
    }
}
