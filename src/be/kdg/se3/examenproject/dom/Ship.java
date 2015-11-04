package be.kdg.se3.examenproject.dom;

import be.kdg.se3.examenproject.calculator.ETACalculator;

import java.util.Date;
import java.util.List;

/**
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

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public Date getETA(List<ShipPosition> shipPositions) {
        if(shipPositions.size() >= 2) {
            return etaCalculator.calculateETA(shipPositions.get(shipPositions.size()-2), shipPositions.get(shipPositions.size()-1));
        }
        return null;
    }
}
