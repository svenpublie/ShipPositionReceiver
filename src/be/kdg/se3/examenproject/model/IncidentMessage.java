package be.kdg.se3.examenproject.model;

/**
 * Model class for the incoming incident messages
 * Created by Sven on 4/11/2015.
 */
public class IncidentMessage {
    private int shipId;
    private String incidentStatus;

    public IncidentMessage() {

    }

    public IncidentMessage(int shipId, String incidentStatus) {
        this.shipId = shipId;
        this.incidentStatus = incidentStatus;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(String incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public String toString() {
        return "\nINCOMING INCIDENT\nShip ID: " + this.shipId + "\nIncident status: " + this.incidentStatus;
    }
}
