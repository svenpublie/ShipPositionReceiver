package be.kdg.se3.examenproject.model;

/**
 * Model class for the outgoing incident reports
 * Created by Sven on 4/11/2015.
 */
public class IncidentReport {
    private int shipId;
    private String incidentType;
    private int numberOfpassengers;
    private boolean dangerousCargo;
    private String action;

    public IncidentReport(int shipId, String incidentType, int numberOfpassengers, boolean dangerousCargo, String action) {
        this.shipId = shipId;
        this.incidentType = incidentType;
        this.numberOfpassengers = numberOfpassengers;
        this.dangerousCargo = dangerousCargo;
        this.action = action;
    }

    public Integer getShipid() {
        return shipId;
    }

    public void setShipid(int shipid) {
        this.shipId = shipid;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public Integer getNumberOfpassengers() {
        return numberOfpassengers;
    }

    public void setNumberOfpassengers(int numberOfpassengers) {
        this.numberOfpassengers = numberOfpassengers;
    }

    public boolean getDangerousCargo() {
        return dangerousCargo;
    }

    public void setDangerousCargo(boolean dangerousCargo) {
        this.dangerousCargo = dangerousCargo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String toString() {
        return "\nSENDING INCIDENT OUT\nShip ID: " + this.shipId + "\nIncident type: " + this.incidentType + "\nNumberOfPassengers: " + this.numberOfpassengers + "\nDangarous cargo: " + this.dangerousCargo + "\nAction type: " + this.action;
    }
}
