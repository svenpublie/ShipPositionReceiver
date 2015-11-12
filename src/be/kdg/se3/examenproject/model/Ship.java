package be.kdg.se3.examenproject.model;

import be.kdg.se3.examenproject.calculator.ETACalculator;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Model class for the incoming data of the ProxyService
 * Created by Sven on 3/11/2015.
 */
public class Ship {
    private ETACalculator etaCalculator;
    private int shipId;
    private String IMONumbuer;
    private boolean dangereousCargo;
    private int numberOfPassengers;
    private List<Cargo> cargo;
    private Date ETA;

    private final Logger logger = Logger.getLogger(this.getClass());

    public Ship () {
        etaCalculator = new ETACalculator();
    }

    public Ship(String IMONumbuer, boolean dangereousCargo, int numberOfPassengers, List<Cargo> cargo) {
        etaCalculator = new ETACalculator();
        this.IMONumbuer = IMONumbuer;
        this.dangereousCargo = dangereousCargo;
        this.numberOfPassengers = numberOfPassengers;
        this.cargo = cargo;
    }

    public boolean getdangereousCargo() {
        return dangereousCargo;
    }

    public String getIMONumbuer() {
        return IMONumbuer;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = Integer.parseInt(shipId.substring(3));
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    /**
     * @param shipPositions, list of received positionMessages of this ship
     * @param calculateEachMessage, boolean TRUE = calculate a new ETA every received message, FALSE = only calculate ETA when entering a new zone (new centralId)
     * if calculateEachMessage is false this setter only sets a new ETA when the centralId's of the 2 last shipPositionMessages are different
     */
    public void setETA(List<ShipPosition> shipPositions, boolean calculateEachMessage) {
        if(shipPositions.size() >= 2 && calculateEachMessage) {
            this.ETA = etaCalculator.calculateETA(shipPositions.get(shipPositions.size()-2), shipPositions.get(shipPositions.size()-1));
        }
        else if(shipPositions.size() >= 2 && (!shipPositions.get(shipPositions.size()-2).getcentraleId().equals(shipPositions.get(shipPositions.size()-1).getcentraleId()))) {
            this.ETA = etaCalculator.calculateETA(shipPositions.get(shipPositions.size()-2), shipPositions.get(shipPositions.size()-1));
        }
        logger.info("New ETA calculated for ship" + getShipId());
    }

    public Date getETA() {
        return ETA;
    }
}
