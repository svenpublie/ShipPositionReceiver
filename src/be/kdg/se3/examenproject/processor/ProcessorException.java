package be.kdg.se3.examenproject.processor;

/**
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
