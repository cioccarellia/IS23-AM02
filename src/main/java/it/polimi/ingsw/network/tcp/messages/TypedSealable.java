package it.polimi.ingsw.network.tcp.messages;

import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.RequestError;
import it.polimi.ingsw.controller.server.result.types.RequestType;

import java.io.Serializable;

public interface TypedSealable<T extends RequestType & Serializable, E extends RequestError & Serializable> {
    TypedResult<T, E> seal();
}
