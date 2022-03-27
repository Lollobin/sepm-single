package at.ac.tuwien.sepm.assignment.individual.exception;

/**
 * Exception type for errors related to validating user input.
 * Is used for signaling that an input was incorrect.
 */
public class ValidationProcessException extends RuntimeException {
    public ValidationProcessException() {
        super();
    }

    public ValidationProcessException(String message) {
        super(message);
    }

    public ValidationProcessException(Throwable cause) {
        super(cause);
    }

    public ValidationProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
