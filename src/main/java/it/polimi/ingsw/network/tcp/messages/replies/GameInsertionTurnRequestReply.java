package it.polimi.ingsw.network.tcp.messages.replies;

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

    @Override
    public String toString() {
        return "GameInsertionTurnRequestReply{" +
                "turnResult=" + turnResult +
                '}';
    }
}
