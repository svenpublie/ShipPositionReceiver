package be.kdg.se3.examenproject;

import be.kdg.se3.examenproject.model.ShipPosition;
import be.kdg.se3.examenproject.calculator.ETACalculator;

import java.util.Date;

/**
 * Created by Sven on 4/11/2015.
 */
public class TestETAGenerator {
    public static void main(String[] args) throws InterruptedException {
        ShipPosition shipPositionFirst = new ShipPosition(2, "jaja", 20000, new Date());
        Thread.sleep(10000);
        ShipPosition shipPositionLast = new ShipPosition(2, "nene", 19998, new Date());

        System.out.println(shipPositionFirst.getTimestamp() + "1: Distance to quay: " + shipPositionFirst.getDistanceToQuay());
        System.out.println(shipPositionLast.getTimestamp() + "2: Distance to quay: " + shipPositionLast.getDistanceToQuay());

        ETACalculator etaCalculator = new ETACalculator();

        Date date2 = etaCalculator.calculateETA(shipPositionFirst, shipPositionLast);

        System.out.println(date2);
    }
}
