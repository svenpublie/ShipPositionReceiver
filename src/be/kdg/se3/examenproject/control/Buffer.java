package be.kdg.se3.examenproject.control;

import be.kdg.se3.examenproject.dom.PositionMessage;

/**
 * Created by Sven on 3/11/2015.
 */
public interface Buffer {
    public void addPositionMessage(PositionMessage positionMessage);
    public void clearBuffer();
}
