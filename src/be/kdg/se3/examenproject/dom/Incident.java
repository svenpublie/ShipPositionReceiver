package be.kdg.se3.examenproject.dom;

/**
 * Created by Sven on 4/11/2015.
 */
public class Incident {
    private String shipid;
    private String incidentstatus;

    public Incident(String shipid, String incidentstatus) {
        this.shipid = shipid;
        this.incidentstatus = incidentstatus;
    }

    public String getShipid() {
        return shipid;
    }

    public void setShipid(String shipid) {
        this.shipid = shipid;
    }

    public String getIncidentstatus() {
        return incidentstatus;
    }

    public void setIncidentstatus(String incidentstatus) {
        this.incidentstatus = incidentstatus;
    }
}
