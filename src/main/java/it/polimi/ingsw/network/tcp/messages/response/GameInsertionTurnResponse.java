package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.network.tcp.messages.SingleSealable;
import org.jetbrains.annotations.Nullable;

public class GameInsertionTurnResponse extends Response implements SingleSealable<BookshelfInsertionFailure> {

    @Nullable
    private final BookshelfInsertionFailure bookshelfInsertionFailure;

    public GameInsertionTurnResponse(SingleResult<BookshelfInsertionFailure> result) {
        bookshelfInsertionFailure = switch (result) {
            case SingleResult.Failure<BookshelfInsertionFailure> failure -> failure.error();
            case SingleResult.Success<BookshelfInsertionFailure> success -> null;
        };
    }

    public @Nullable BookshelfInsertionFailure getBookshelfInsertionFailure() {
        return bookshelfInsertionFailure;
    }

    @Override
    public String toString() {
        return "GameSelectionTurnResponse{" +
                "tileSelectionFailures=" + bookshelfInsertionFailure +
                '}';
    }

    @Override
    public SingleResult<BookshelfInsertionFailure> seal() {
        return bookshelfInsertionFailure == null ?
                new SingleResult.Success<>() :
                new SingleResult.Failure<>(bookshelfInsertionFailure);
    }
}
