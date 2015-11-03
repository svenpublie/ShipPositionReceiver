package be.kdg.se3.examenproject.dbwriter;

/**
 * Used to wrap all errors that occur in a DBWriter.
 * Created by Sven on 3/11/2015.
 */
public class DBWriterException extends Exception {
    private final Exception innerException;

    public DBWriterException(String message, Exception cause) {
        super(message, cause);
        this.innerException = cause;
    }

    public Exception getInnerException() {
        return innerException;
    }
}
