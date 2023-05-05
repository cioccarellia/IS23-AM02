package it.polimi.ingsw.networkProtocol.tcp.messages.response;

import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.BookshelfInsertionFailure;

public class GameInsertionTurnResponse extends Response {
    SingleResult<BookshelfInsertionFailure> turnResult;

    public GameInsertionTurnResponse(SingleResult<BookshelfInsertionFailure> turnResult) {
        this.turnResult = turnResult;
    }

}
