package be.kdg.se3.examenproject.dbwriter;

import be.kdg.se3.examenproject.dom.ShipPosition;

/**
 * Created by Sven on 3/11/2015.
 */
public interface DBWriter {
    void writeMessageToDatabase(ShipPosition shipPosition) throws DBWriterException;
}
