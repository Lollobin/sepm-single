package at.ac.tuwien.sepm.assignment.individual.exception;

/**
 * Exception type for errors related to validating user input
 * Is thrown by the validator classes
 */
public class ValidationException extends RuntimeException{
    public ValidationException() { super(); }
    public ValidationException(String message) { super(message); }
    public ValidationException(Throwable cause) { super(cause); }
    public ValidationException(String message, Throwable cause) { super(message, cause); }
}
