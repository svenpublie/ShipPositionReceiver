package be.kdg.se3.examenproject.control;

/**
 * Exception thrown while buffering shipPositions
 * Created by Sven on 4/11/2015.
 */
public class BufferExecutorException extends Exception {
    private final Exception innerException;

    public BufferExecutorException(String message, Exception cause) {
        super(message, cause);
        this.innerException = cause;
    }

    public Exception getInnerException() {
        return innerException;
    }
}
