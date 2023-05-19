package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;

public class GameInsertionTurnResponse extends Response {
    final SingleResult<BookshelfInsertionFailure> turnResult;

    public GameInsertionTurnResponse(SingleResult<BookshelfInsertionFailure> turnResult) {
        this.turnResult = turnResult;
    }

}
