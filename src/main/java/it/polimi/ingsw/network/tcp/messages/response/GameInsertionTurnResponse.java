package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.network.tcp.messages.TypedSealable;
import org.jetbrains.annotations.Nullable;

public class GameInsertionTurnResponse extends Response implements TypedSealable<TileInsertionSuccess, BookshelfInsertionFailure> {

    @Nullable
    private final TileInsertionSuccess tileInsertionSuccess;

    @Nullable
    private final BookshelfInsertionFailure bookshelfInsertionFailure;

    public GameInsertionTurnResponse(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> result) {
        tileInsertionSuccess = switch (result) {
            case TypedResult.Failure<TileInsertionSuccess, BookshelfInsertionFailure> failure -> null;
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success -> success.value();
        };

        bookshelfInsertionFailure = switch (result) {
            case TypedResult.Failure<TileInsertionSuccess, BookshelfInsertionFailure> failure -> failure.error();
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success -> null;
        };
    }

    public @Nullable BookshelfInsertionFailure getBookshelfInsertionFailure() {
        return bookshelfInsertionFailure;
    }

    @Override
    public TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> seal() {
        return bookshelfInsertionFailure == null ?
                new TypedResult.Success<>(tileInsertionSuccess) :
                new TypedResult.Failure<>(bookshelfInsertionFailure);
    }

    @Override
    public String toString() {
        return "GameInsertionTurnResponse{" +
                "tileInsertionSuccess=" + tileInsertionSuccess +
                ", bookshelfInsertionFailure=" + bookshelfInsertionFailure +
                '}';
    }
}
