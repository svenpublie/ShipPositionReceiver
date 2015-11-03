package be.kdg.se3.examenproject.service;

/**
 * Created by Sven on 3/11/2015.
 */
public class ShipProxyHandlerException extends Exception {
    public ShipProxyHandlerException(String message) {
        super(message);
    }

    public ShipProxyHandlerException(String message, Exception cause) {
        super(message, cause);
    }
}
