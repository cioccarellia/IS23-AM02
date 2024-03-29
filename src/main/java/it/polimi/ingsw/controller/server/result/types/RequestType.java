package it.polimi.ingsw.controller.server.result.types;

import it.polimi.ingsw.controller.server.result.TypedResult;

import java.io.Serializable;

/**
 * Marker interface for a class representing a request type, to be
 * implemented as a type parameter for {@link TypedResult.Success}.
 */
public interface RequestType extends Serializable {
}