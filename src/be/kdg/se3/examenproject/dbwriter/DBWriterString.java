package be.kdg.se3.examenproject.dbwriter;

import be.kdg.se3.examenproject.dom.ShipPosition;

/**
 * This class is responsible for writing a PositionMessage
 * to the standard system output.
 * Created by Sven on 3/11/2015.
 */
public class DBWriterString implements DBWriter {

    @Override
    public void writeMessageToDatabase(ShipPosition shipPosition) throws DBWriterException {
        try {
            System.out.println("\n" + shipPosition);
        } catch (Exception e) {
            throw new DBWriterException("Exception while executing the Database writer", e);
        }
    }
}
