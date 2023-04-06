package it.polimi.ingsw.controller.result;

import it.polimi.ingsw.controller.result.failures.RequestError;

/**
 * Sealed class for representing a return value following an operation,
 * typed according to its content.
 *
 * A {@code SingleResult} may be:
 * <ul>
 * <li>{@link Success}, indicating that the request has been successfully executed;</li>
 * <li>{@link Failure}, indicating that the request could not be successfully executed and that a
 * specific error type is returned.</li>
 * </ul>
 * */
sealed public interface SingleResult<R extends RequestError> {
    /**
     * Represents a single, successful request result
     * */
    record Success<R extends RequestError>() implements SingleResult<R> {
    }

    /**
     * Represents a single, failed request result
     * */
    record Failure<R extends RequestError>(R error) implements SingleResult<R> {
    }
}