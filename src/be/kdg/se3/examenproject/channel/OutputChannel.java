package be.kdg.se3.examenproject.channel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * Interface for outputchannels
 * Created by Sven on 4/11/2015.
 */
public interface OutputChannel {
    void init() throws IOException, TimeoutException;
    void send(String message) throws IOException;
    void shutDown() throws IOException, TimeoutException;
}
