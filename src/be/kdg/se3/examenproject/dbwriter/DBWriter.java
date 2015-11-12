package be.kdg.se3.examenproject.dbwriter;

import be.kdg.se3.examenproject.model.ShipPosition;

/**
 * Interface to write to the database
 * Created by Sven on 3/11/2015.
 */
public interface DBWriter {
    void writeMessageToDatabase(ShipPosition shipPosition) throws DBWriterException;
}
