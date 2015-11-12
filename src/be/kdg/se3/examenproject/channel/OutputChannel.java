package be.kdg.se3.examenproject.channel;

/**
 * Interface to put messages on a queue
 * Created by Sven on 4/11/2015.
 */
public interface OutputChannel {
    /**
     * Initializes an outputchannel and starts a connection with it
     * @throws OutputChannelException
     */
    void init() throws OutputChannelException;

    /**
     * Puts a message on the queue
     * @param message: the message that needs to be put on the queue
     * @throws OutputChannelException
     */
    void send(String message) throws OutputChannelException;

    /**
     * Closes the connection with the outut queue
     * @throws OutputChannelException
     */
    void shutDown() throws OutputChannelException;
}
