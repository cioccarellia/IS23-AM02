package it.polimi.ingsw.network.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;

public class GameInsertionTurnRequestReply extends Reply {

    private final SingleResult<BookshelfInsertionFailure> turnResult;

    public GameInsertionTurnRequestReply(SingleResult<BookshelfInsertionFailure> result) {
        this.turnResult = result;
    }

    public SingleResult<BookshelfInsertionFailure> getTurnResult() {
        return turnResult;
    }
}
