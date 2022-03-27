package at.ac.tuwien.sepm.assignment.individual.exception;

/**
 * Exception type for errors related to validating user input.
 * Is used for signaling that an input was correct but cannot be accepted due to the state of the database.
 */
public class ValidationConflictException extends RuntimeException {
    public ValidationConflictException() {
        super();
    }

    public ValidationConflictException(String message) {
        super(message);
    }

    public ValidationConflictException(Throwable cause) {
        super(cause);
    }

    public ValidationConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
