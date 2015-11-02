package be.kdg.se3.examenproject.converter;

/**
 * Created by Sven on 2/11/2015.
 * Used to wrap all errors that occur in XML-Converter.
 */
public class XMLConverterException extends Exception {
    private final Exception innerException;

    public XMLConverterException(String message, Exception cause) {
        super(message, cause);
        this.innerException = cause;
    }

    public Exception getInnerException() {
        return innerException;
    }
}
