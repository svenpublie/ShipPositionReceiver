package be.kdg.se3.examenproject.dom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Sven on 3/11/2015.
 */

@XmlRootElement(name="shipposition")
public class PositionMessage {
    private int shipId;
    private String centralId;
    private double distanceToQuade;
    private Date timestamp;

    public PositionMessage() {
    }

    public PositionMessage(int shipId, String centralId, double distanceToQuade, Date timestamp) {
        this.shipId = shipId;
        this.centralId = centralId;
        this.distanceToQuade = distanceToQuade;
        this.timestamp = timestamp;
    }

    public Integer getShipId() {
        return shipId;
    }

    @XmlElement(name="shipid")
    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getcentraleId() {
        return centralId;
    }

    @XmlElement(name="centralid")
    public void setCentralId(String centralId) {
        this.centralId = centralId;
    }

    public Double getDistanceToQuade() {
        return distanceToQuade;
    }

    @XmlElement(name="distance")
    public void setDistanceToQuade(double distanceToQuade){
        this.distanceToQuade = distanceToQuade;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @XmlElement(name="timestamp")
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
