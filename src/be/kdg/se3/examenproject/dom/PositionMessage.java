package be.kdg.se3.examenproject.dom;

import be.kdg.se3.examenproject.channel.InputChannel;

import java.util.Date;

/**
 * Created by Sven on 3/11/2015.
 */
public class PositionMessage {
    private int shipId;
    private int centraleId;
    private double distanceToQuade;
    private Date timestamp;

    public PositionMessage() {
    }

    public PositionMessage (int shipId, int centraleId, double distanceToQuade, Date timestamp) {
        this.shipId = shipId;
        this.centraleId = centraleId;
        this.distanceToQuade = distanceToQuade;
        this.timestamp = timestamp;
    }

    public Integer getShipId() {
        return shipId;
    }

    public Integer getcentraleId() {
        return centraleId;
    }

    public Double getDistanceToQuade() {
        return distanceToQuade;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
