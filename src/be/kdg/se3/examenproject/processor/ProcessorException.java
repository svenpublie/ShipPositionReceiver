package be.kdg.se3.examenproject.processor;

/**
 * Wraps the ProcessorExceptions
 * Created by Sven on 2/11/2015.
 */
public class ProcessorException extends Exception {
    private final Exception innerException;

    public ProcessorException(String message, Exception cause) {
        super(message, cause);
        this.innerException = cause;
    }

    public Exception getInnerException() {
        return innerException;
    }
}
