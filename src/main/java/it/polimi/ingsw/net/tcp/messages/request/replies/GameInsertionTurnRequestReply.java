package it.polimi.ingsw.net.tcp.messages.request.replies;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.net.tcp.messages.response.Response;

public class GameInsertionTurnRequestReply extends Response {

    private final SingleResult<BookshelfInsertionFailure> turnResult;

    public GameInsertionTurnRequestReply(SingleResult<BookshelfInsertionFailure> result) {
        this.turnResult = result;
    }

    public SingleResult<BookshelfInsertionFailure> getTurnResult() {
        return turnResult;
    }
}
