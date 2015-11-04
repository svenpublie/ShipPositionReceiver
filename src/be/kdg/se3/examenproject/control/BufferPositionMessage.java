package be.kdg.se3.examenproject.control;

import be.kdg.se3.examenproject.dom.ShipPosition;
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
    private List<ShipPosition> shipPositions = new ArrayList<ShipPosition>();
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
     * @param shipPosition: the message that needs to be buffered
     */
    public void addPositionMessage(ShipPosition shipPosition) {
        shipPositions.add(shipPosition);
    }


    @Override
    public void clearBuffer() {
        shipPositions.clear();
    }

    public int getShipId() {
        return shipId;
    }

    public List<ShipPosition> getShipPositions() {
        return shipPositions;
    }
}
