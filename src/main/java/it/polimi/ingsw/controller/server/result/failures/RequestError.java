package it.polimi.ingsw.controller.server.result.failures;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.TypedResult;

/**
 * Marker interface for a class representing a request error, to be
 * implemented as a type parameter for {@link SingleResult.Failure} or
 * {@link TypedResult.Failure}.
 */
public interface RequestError {
}