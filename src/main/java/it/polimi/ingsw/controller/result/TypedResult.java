package it.polimi.ingsw.controller.result;

/**
 * Sealed class for representing a return value following an operation,
 * typed according to its content.
 *
 * A {@code SingleResult} may be:
 * <ul>
 * <li>{@link Success}, indicating that the request has been successfully executed, containing its
 * result data type;</li>
 * <li>{@link Failure}, indicating that the request could not be successfully executed and that a
 * specific error type is returned.</li>
 * </ul>
 * */
sealed public interface TypedResult<T extends RequestType, R extends RequestError> {
    /**
     * Represents a single, successful request result
     * */
    record Success<T extends RequestType, R extends RequestError>(T response) implements TypedResult<T, R> {
    }

    /**
     * Represents a single, failed request result
     * */
    record Failure<T extends RequestType, R extends RequestError>(R error) implements TypedResult<T, R> {
    }
}