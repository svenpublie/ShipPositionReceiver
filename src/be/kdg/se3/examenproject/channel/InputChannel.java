package be.kdg.se3.examenproject.channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sven on 2/11/2015.
 */
public interface InputChannel {
    void init() throws InputChannelException, IOException, TimeoutException;
    String getNextMessage() throws InputChannelException;
    void shutDown() throws InputChannelException;
}
