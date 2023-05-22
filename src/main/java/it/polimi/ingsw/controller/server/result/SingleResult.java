package it.polimi.ingsw.controller.server.result;

import it.polimi.ingsw.controller.server.result.failures.RequestError;

import java.io.Serializable;

/**
 * Sealed class representing the result of an operation executed by a method.
 * <p>
 * The {@code SingleResult} class is typed according to its content: it accepts
 * only one type parameter: {@code R}.
 * <ul>
 * <li>{@code R} is used to define the type of the value contained in
 * an instance of {@code Failure}.
 * {@code R} must implement the {@link RequestError} interface;</li>
 * </ul>
 * <p>
 * A {@code SingleResult} may be:
 * <ul>
 * <li>{@link Success}, indicating that the request has been successfully executed;</li>
 * <li>{@link Failure}, indicating that the request could not be successfully executed,
 * and that a specific error type is returned.</li>
 * </ul>
 */
sealed public interface SingleResult<R extends RequestError & Serializable> extends Serializable {
    /**
     * Represents a single, stateless successful result.
     */
    record Success<R extends RequestError & Serializable>() implements SingleResult<R>, Serializable {
    }

    /**
     * Represents a single, stateful failed result.
     */
    record Failure<R extends RequestError & Serializable>(R error) implements SingleResult<R>, Serializable {
    }
}