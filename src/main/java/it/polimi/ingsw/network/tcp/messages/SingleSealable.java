package it.polimi.ingsw.network.tcp.messages;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.RequestError;

import java.io.Serializable;

public interface SingleSealable<E extends RequestError & Serializable> {
    SingleResult<E> seal();
}