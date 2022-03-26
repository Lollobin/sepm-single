package at.ac.tuwien.sepm.assignment.individual.exception;

/**
 * Exception type for errors related to validating user input.
 * Is used for signaling that an input was incorrect.
 */
public class ValidationException extends RuntimeException {
    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
