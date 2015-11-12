package be.kdg.se3.examenproject.buffer;

import be.kdg.se3.examenproject.model.Ship;
import be.kdg.se3.examenproject.model.ShipPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the model to buffer the shipPosition messages
 * Created by Sven on 3/11/2015.
 */
public class BufferPositionMessage {
    private double limit;
    private int shipId;
    private List<ShipPosition> shipPositions = new ArrayList<ShipPosition>();
    private Ship ship;

    public BufferPositionMessage(double limit, int shipId, Ship ship) {
        this.limit = limit;
        this.shipId = shipId;
        this.ship = ship;
    }

    /**
     * Adds a position message to the list
     * @param shipPosition: the message that needs to be buffered
     */
    public void addPositionMessage(ShipPosition shipPosition) {
        shipPositions.add(shipPosition);
    }

    public int getShipId() {
        return shipId;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public List<ShipPosition> getShipPositions() {
        return shipPositions;
    }

    public Ship getShip() {
        return ship;
    }
}
