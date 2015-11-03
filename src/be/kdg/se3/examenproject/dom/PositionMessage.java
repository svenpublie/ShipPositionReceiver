package be.kdg.se3.examenproject.dom;

import java.util.Date;

/**
 * Created by Sven on 3/11/2015.
 */

//@XmlRootElement(name="shipposition")
public class PositionMessage {
    private int shipid;
    private String centralid;
    private double distancetoquade;
    private Date timestamp;

    public PositionMessage() {
    }

    public PositionMessage(int shipid, String centralid, double distancetoquade, Date timestamp) {
        this.shipid = shipid;
        this.centralid = centralid;
        this.distancetoquade = distancetoquade;
        this.timestamp = timestamp;
    }

    public Integer getShipid() {
        return shipid;
    }

    //@XmlElement(name="shipid")
    public void setShipid(int shipid) {
        this.shipid = shipid;
    }

    public String getcentraleId() {
        return centralid;
    }

    //@XmlElement(name="centralid")
    public void setCentralid(String centralid) {
        this.centralid = centralid;
    }

    public Double getDistancetoquade() {
        return distancetoquade;
    }

    //@XmlElement(name="distance")
    public void setDistancetoquade(double distancetoquade){
        this.distancetoquade = distancetoquade;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    //@XmlElement(name="timestamp")
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "ShipID: " + this.shipid + "\nCentraleID: " + this.centralid + "\nDistance to quade: " + this.distancetoquade + "\nTimestamp: " + this.timestamp;
    }
}
