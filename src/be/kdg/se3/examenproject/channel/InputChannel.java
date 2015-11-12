package be.kdg.se3.examenproject.channel;

import java.util.concurrent.TimeoutException;

/**
 * Interface to communicate with an inputChannel
 * Created by Sven on 2/11/2015.
 */
public interface InputChannel {
    /**
     * Initializes the input channel and starts a connection with it
     * @throws InputChannelException
     */
    void init() throws InputChannelException;

    /**
     * Reads the next message of the queue
     * @return message from queue in XML format
     * @throws InputChannelException
     */
    String getNextMessage() throws InputChannelException;

    /**
     * Closes the connection with the queue
     * @throws InputChannelException
     * @throws TimeoutException
     */
    void shutDown() throws InputChannelException;
}
