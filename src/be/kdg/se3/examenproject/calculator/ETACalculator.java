package be.kdg.se3.examenproject.calculator;

import be.kdg.se3.examenproject.model.ShipPosition;

import java.util.Date;

/**
 * This class calculates the ETA of a ship
 * Created by Sven on 4/11/2015.
 */
public class ETACalculator {
    long timeRequired;
    double deltaDistanceToQuay;
    double currentSpeed;
    long timeOfArrival;

    public ETACalculator() {

    }

    /**
     * Calculates the ETA of a ship
     * @param shipPositionFirst, the second last incoming message of the ship
     * @param shipPositionLast, the last incoming message of the ship
     * @return, the calculated ETA of the ship
     */
    public Date calculateETA(ShipPosition shipPositionFirst, ShipPosition shipPositionLast) {
        timeRequired = shipPositionLast.getTimestamp().getTime() - shipPositionFirst.getTimestamp().getTime();
        deltaDistanceToQuay = shipPositionFirst.getDistanceToQuay() - shipPositionLast.getDistanceToQuay();
        currentSpeed = deltaDistanceToQuay / timeRequired;

        timeOfArrival = (long)(shipPositionLast.getDistanceToQuay() / currentSpeed);
        Date date = new Date(shipPositionLast.getTimestamp().getTime() + timeOfArrival);

        return date;
    }
}
