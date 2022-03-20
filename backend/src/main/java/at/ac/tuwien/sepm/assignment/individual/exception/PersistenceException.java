package at.ac.tuwien.sepm.assignment.individual.exception;

/**
 * Exception type for exceptions in the persistence layer
 * Is used for wrapping database related errors
 */
public class PersistenceException extends RuntimeException {
    public PersistenceException() { super(); }
    public PersistenceException(String message) { super(message); }
    public PersistenceException(Throwable cause) { super(cause); }
    public PersistenceException(String message, Throwable cause) { super(message, cause); }
}
