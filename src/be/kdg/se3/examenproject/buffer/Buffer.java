package be.kdg.se3.examenproject.buffer;

import be.kdg.se3.examenproject.channel.OutputChannelException;
import be.kdg.se3.examenproject.converter.JSONConverterException;
import be.kdg.se3.examenproject.model.ShipPosition;
import be.kdg.se3.examenproject.service.ShipProxyHandlerException;

/**
 * Interface to create and fill buffers
 * Created by publiesven on 12/11/2015.
 */
public interface Buffer {
    /**
     * puts the shipPositio message in the buffer of the matching Ship
     * @param shipPosition
     * @throws BufferExecutorException
     * @throws ShipProxyHandlerException
     * @throws JSONConverterException
     * @throws OutputChannelException
     */
    void putMessageInBuffer(ShipPosition shipPosition) throws BufferExecutorException, ShipProxyHandlerException, JSONConverterException, OutputChannelException;

    /**
     * Will clear the Ship buffer when tthe timelimit from the last message until the current time is reached
     */
    void controlLastMessage();

    /**
     * Checks if the ship already has a buffer
     * @param shipPosition incoming ship message
     * @return false when no buffer exists for this ship, true when there is already a buffer for this ship
     * @throws BufferExecutorException
     */
    boolean controlShipBufferExists(ShipPosition shipPosition) throws BufferExecutorException;
}
