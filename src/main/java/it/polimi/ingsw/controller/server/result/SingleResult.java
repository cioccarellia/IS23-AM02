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
sealed abstract public class SingleResult<T extends RequestError & Serializable> implements Serializable {
    @Override
    public String toString() {
        return "SingleResult{}";
    }

    /**
     * Represents a single, stateless successful result.
     */
    public static final class Success<F extends RequestError & Serializable> extends SingleResult<F> implements Serializable {
        @Override
        public String toString() {
            return "Success{}";
        }
    }

    /**
     * Represents a single, stateful failed result.
     */
    public static final class Failure<F extends RequestError & Serializable> extends SingleResult<F> implements Serializable {
        private final F error;
        public Failure(F error) {
            this.error = error;
        }

        public F error() {
            return error;
        }

        @Override
        public String toString() {
            return "Failure{" +
                    "error=" + error +
                    '}';
        }
    }
}