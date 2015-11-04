package be.kdg.se3.examenproject.control;

import be.kdg.se3.examenproject.dom.PositionMessage;
import be.kdg.se3.examenproject.dom.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * This class buffers the incoming position messages
 * Created by Sven on 3/11/2015.
 */
public class BufferPositionMessage implements Buffer {
    private double limit;
    private int shipId;
    private List<PositionMessage> positionMessages = new ArrayList<PositionMessage>();
    private Ship ship;

    public BufferPositionMessage(double limit) {
        this.limit = limit;
    }

    public BufferPositionMessage(double limit, int shipId, Ship ship) {
        this.limit = limit;
        this.shipId = shipId;
        this.ship = ship;
    }

    /**
     * Adds a position message to the list, clears it when limit is reached
     * @param positionMessage: the message that needs to be buffered
     */
    public void addPositionMessage(PositionMessage positionMessage) {
        if(positionMessages.size() == limit)
            positionMessages.clear();
        positionMessages.add(positionMessage);
    }


    @Override
    public void clearBuffer() {
        positionMessages.clear();
    }

    public Integer getShipId() {
        return shipId;
    }

    public List<PositionMessage> getPositionMessages() {
        return positionMessages;
    }
}
