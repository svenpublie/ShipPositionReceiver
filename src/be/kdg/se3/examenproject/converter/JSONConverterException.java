package be.kdg.se3.examenproject.converter;

/**
 * Used to wrap all errors that occur in JSON-Converter.
 * Created by Sven on 3/11/2015.
 */
public class JSONConverterException extends Exception {
    private final Exception innerException;

    public JSONConverterException(String message, Exception cause) {
        super(message, cause);
        this.innerException = cause;
    }

    public Exception getInnerException() {
        return innerException;
    }
}
