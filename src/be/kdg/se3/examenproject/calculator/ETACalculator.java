package be.kdg.se3.examenproject.calculator;

import be.kdg.se3.examenproject.dom.ShipPosition;

import java.util.Date;

/**
 * Created by Sven on 4/11/2015.
 */
public class ETACalculator {
    long timeRequired;
    double deltaDistanceToQuay;
    double currentSpeed;
    long timeOfArrival;

    public ETACalculator() {

    }

    public Date calculateETA(ShipPosition shipPositionFirst, ShipPosition shipPositionLast) {
        timeRequired = shipPositionLast.getTimestamp().getTime() - shipPositionFirst.getTimestamp().getTime();
        deltaDistanceToQuay = shipPositionFirst.getDistanceToQuay() - shipPositionLast.getDistanceToQuay();
        currentSpeed = deltaDistanceToQuay / timeRequired;

        timeOfArrival = (long)(shipPositionLast.getDistanceToQuay() / currentSpeed);
        Date date = new Date(shipPositionLast.getTimestamp().getTime() + timeOfArrival);

        return date;
    }
}
