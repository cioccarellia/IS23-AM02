package it.polimi.ingsw.controller.server.result;

import it.polimi.ingsw.controller.server.result.failures.RequestError;
import it.polimi.ingsw.controller.server.result.types.RequestType;

import java.io.Serializable;

/**
 * Sealed class representing the result of an operation executed by a method.
 * <p>
 * The {@code TypedResult} class is typed according to its content: it accepts
 * two type parameters: {@code T} and {@code R}.
 * <ul>
 * <li>{@code T} is used to define the type of the value contained in
 * an instance of {@code Success}.
 * {@code T} must implement the {@link RequestType} interface;</li>
 * <li>{@code R} is used to define the type of the value contained in
 * an instance of {@code Failure}.
 * {@code R} must implement the {@link RequestError} interface;</li>
 * </ul>
 * <p>
 * A {@code TypedResult} may be:
 * <ul>
 * <li>{@link Success}, indicating that the request has been successfully executed,
 * and that the {@code Success} instance contains its result data type;</li>
 * <li>{@link Failure}, indicating that the request could not be successfully executed,
 * and that a specific error type is returned.</li>
 * </ul>
 */
sealed public interface TypedResult<T extends RequestType & Serializable, R extends RequestError & Serializable> extends Serializable {
    /**
     * Represents a single, stateful successful result.
     */
    record Success
            <T extends RequestType & Serializable, R extends RequestError & Serializable>(T value)
            implements TypedResult<T, R>, Serializable {
    }

    /**
     * Represents a single, stateful failed result.
     */
    record Failure
            <T extends RequestType & Serializable, R extends RequestError & Serializable>(R error)
            implements TypedResult<T, R>, Serializable {
    }
}