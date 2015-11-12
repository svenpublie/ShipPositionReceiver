package be.kdg.se3.examenproject.incident;

/**
 * Wraps the Incident listener excepetions
 * Created by Sven on 4/11/2015.
 */
public class IncidentListerenImplException extends Exception {
    private final Exception innerException;

    public IncidentListerenImplException(String message, Exception cause) {
        super(message, cause);
        this.innerException = cause;
    }

    public Exception getInnerException() {
        return innerException;
    }
}
