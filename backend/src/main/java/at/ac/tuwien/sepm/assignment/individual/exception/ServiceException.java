package at.ac.tuwien.sepm.assignment.individual.exception;

/**
 * Exception type for exceptions in the service layer
 */
public class ServiceException extends RuntimeException{
    public ServiceException() { super(); }
    public ServiceException(String message) { super(message); }
    public ServiceException(Throwable cause) { super(cause); }
    public ServiceException(String message, Throwable cause) { super(message, cause); }
}
