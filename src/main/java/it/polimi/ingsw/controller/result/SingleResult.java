package it.polimi.ingsw.controller.result;

import it.polimi.ingsw.controller.result.failures.RequestError;

/**
 * Sealed class representing the result of an operation executed by a method.
 * 
 * The {@code SingleResult} class is typed according to its content: it accepts 
 * only one type paratemer: {@code R}.
 * <ul>
 * <li>{@code R} is used to define the type of the value contained in 
 * an instance of {@code Failure}.
 * {@code R} must implement the {@link RequestError} interface;</li>
 * </ul>
 *
 * A {@code SingleResult} may be:
 * <ul>
 * <li>{@link Success}, indicating that the request has been successfully executed;</li>
 * <li>{@link Failure}, indicating that the request could not be successfully executed,
 * and that a specific error type is returned.</li>
 * </ul>
 * */
sealed public interface SingleResult<R extends RequestError> {
    /**
     * Represents a single, stateless successful result.
     * */
    record Success<R extends RequestError>() implements SingleResult<R> 
    {
    }

    /**
     * Represents a single, stateful failed result.
     * */
    record Failure<R extends RequestError>(R error) implements SingleResult<R> 
    {
    }
}