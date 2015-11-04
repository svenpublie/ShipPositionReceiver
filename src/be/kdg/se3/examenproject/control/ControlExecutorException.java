package be.kdg.se3.examenproject.control;

/**
 * Created by Sven on 4/11/2015.
 */
public class ControlExecutorException extends Exception {
    private final Exception innerException;

    public ControlExecutorException(String message, Exception cause) {
        super(message, cause);
        this.innerException = cause;
    }

    public Exception getInnerException() {
        return innerException;
    }
}
