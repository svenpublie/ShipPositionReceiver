package be.kdg.se3.examenproject.control;

import be.kdg.se3.examenproject.model.BufferPositionMessage;
import be.kdg.se3.examenproject.model.IncidentReport;
import be.kdg.se3.examenproject.model.Ship;
import be.kdg.se3.examenproject.incident.IncidentListenerImpl;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * This class controls if there is a violation by a certain ship and sends it to the incidentListener that puts it on the outputChannel
 * Created by publiesven on 12/11/2015.
 */
public class ControlViolation {
    IncidentListenerImpl incidentListener;
    private final static String VIOLATION = "ZWARE OVERTREDING";

    private final Logger logger = Logger.getLogger(this.getClass());

    public ControlViolation(IncidentListenerImpl incidentListener) {
        this.incidentListener = incidentListener;
    }

    /**
     *
     * @param bufferPositionMessages, current ships with buffered postitionMessages
     * @param zones, zones with a current incident
     */
    public void controlHeavyViolationZone(List<BufferPositionMessage> bufferPositionMessages, List<String> zones) {
        for(BufferPositionMessage bufferPositionMessage : bufferPositionMessages) {
            int size = bufferPositionMessage.getShipPositions().size();
            if(bufferPositionMessage.getShipPositions().size() >= 2) {
                for(String zone : zones) {
                    if(bufferPositionMessage.getShipPositions().get(size-2).getDistanceToQuay().byteValue() != bufferPositionMessage.getShipPositions().get(size-1).getDistanceToQuay().byteValue()) {
                        if(zone.equalsIgnoreCase(bufferPositionMessage.getShipPositions().get(-1).getcentraleId())){
                            putViolationOnChannel(bufferPositionMessage.getShip());
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bufferPositionMessages, current ships with buffered postitionMessages
     */
    public void controlHeavyViolationOverall(List<BufferPositionMessage> bufferPositionMessages) {
        for(BufferPositionMessage bufferPositionMessage : bufferPositionMessages) {
            int size = bufferPositionMessage.getShipPositions().size();
            if(bufferPositionMessage.getShipPositions().size() >= 2) {
                if(bufferPositionMessage.getShipPositions().get(size-2).getDistanceToQuay().byteValue() != bufferPositionMessage.getShipPositions().get(size-1).getDistanceToQuay().byteValue()) {
                    putViolationOnChannel(bufferPositionMessage.getShip());
                }
            }
        }
    }

    private void putViolationOnChannel(Ship ship) {
        IncidentReport incidentReport = new IncidentReport(ship.getShipId(), "", ship.getNumberOfPassengers(), ship.getdangereousCargo(), VIOLATION);
        try {
            incidentListener.putIncidentOnOutputChannel(incidentReport);
        } catch (IOException e) {
            logger.error("IO Error while putting incident on outputchannel" + e);
        } catch (TimeoutException e) {
            logger.error("Timeout Error while putting incident on outputchannel" + e);
        }
    }
}
