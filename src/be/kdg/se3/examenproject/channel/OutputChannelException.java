package be.kdg.se3.examenproject.channel;

import java.io.IOException;

/**
 * Interface for outputchannel classes
 * Created by Sven on 4/11/2015.
 */
public class OutputChannelException extends IOException {
    public OutputChannelException(String message, Throwable cause) {
        super(message, cause);
    }
}
