package it.polimi.ingsw.network.tcp.messages.request;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.RequestError;

public interface SingleSealable<S extends RequestError> {

    SingleResult<S> seal();

}
