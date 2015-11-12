package be.kdg.se3.examenproject.control;

import be.kdg.se3.examenproject.model.ShipPosition;

/**
 * Interface for buffering
 * Created by Sven on 3/11/2015.
 */
public interface Buffer {
    public void addPositionMessage(ShipPosition shipPosition);
    public void clearBuffer();
}
