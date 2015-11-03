package be.kdg.se3.examenproject.dbwriter;

import be.kdg.se3.examenproject.dom.PositionMessage;

/**
 * This class is responsible for writing a PositionMessage
 * to the standard system output.
 * Created by Sven on 3/11/2015.
 */
public class DBWriterString {
    public void writeMessageToDatabase(PositionMessage positionMessage) throws DBWriterException {
        try {
            System.out.println("\n" + positionMessage);
        } catch (Exception e) {
            throw new DBWriterException("Exception while executing the Database writer", e);
        }
    }
}
