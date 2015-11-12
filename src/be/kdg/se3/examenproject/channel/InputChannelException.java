package be.kdg.se3.examenproject.channel;

import java.util.concurrent.TimeoutException;

/**
 * Exception for inputChannel classes
 * Created by Sven on 2/11/2015.
 */
public class InputChannelException extends Exception  {
    public InputChannelException(String message, Throwable cause) {
        super(message, cause);
    }
}
