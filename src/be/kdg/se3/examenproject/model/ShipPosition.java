package be.kdg.se3.examenproject.model;

import java.util.Date;

/**
 * Model class for the incoming ShipPosition Messages
 * Created by Sven on 3/11/2015.
 */

public class ShipPosition {
    private int shipId;
    private String centralId;
    private double distanceToQuay;
    private Date timestamp;

    public ShipPosition() {
    }

    public ShipPosition(int shipId, String centralId, double distanceToQuay, Date timestamp) {
        this.shipId = shipId;
        this.centralId = centralId;
        this.distanceToQuay = distanceToQuay;
        this.timestamp = timestamp;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getcentraleId() {
        return centralId;
    }

    public void setCentralId(String centralId) {
        this.centralId = centralId;
    }

    public Double getDistanceToQuay() {
        return distanceToQuay;
    }

    public void setDistanceToQuay(double distanceToQuay){
        this.distanceToQuay = distanceToQuay;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "INCOMING SHIP POSITION\nShipID: " + this.shipId + "\nCentraleID: " + this.centralId + "\nDistance to quay: " + this.distanceToQuay + "\nTimestamp: " + this.timestamp;
    }
}
