package at.ac.tuwien.sepm.assignment.individual.exception;

/**
 * Exception type for exceptions in the persistence layer.
 * Is used for signaling that a record was not found.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
