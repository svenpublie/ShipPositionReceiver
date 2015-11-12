package be.kdg.se3.examenproject.buffer;

import be.kdg.se3.examenproject.channel.OutputChannelException;
import be.kdg.se3.examenproject.control.ControlViolation;
import be.kdg.se3.examenproject.converter.JSONConverter;
import be.kdg.se3.examenproject.converter.JSONConverterException;
import be.kdg.se3.examenproject.incident.ActionType;
import be.kdg.se3.examenproject.incident.IncidentListenerImpl;
import be.kdg.se3.examenproject.model.Ship;
import be.kdg.se3.examenproject.model.ShipPosition;
import be.kdg.se3.examenproject.service.ShipProxyHandler;
import be.kdg.se3.examenproject.service.ShipProxyHandlerException;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Creates a buffer for each new Ship. Puts the incoming shipPosition messages in this buffer
 * Created by Sven on 4/11/2015.
 */
public class BufferExecutor implements Buffer {
    private double bufferLimitInSeconds;
    private BufferPositionMessage bufferPositionMessage;
    private IncidentListenerImpl incidentListener;
    private List<BufferPositionMessage> bufferPositionMessages = new ArrayList<BufferPositionMessage>();
    private List<Integer> shipIdList = new ArrayList<Integer>();
    Map<Integer, Boolean> etaCalculatedShipIds = new HashMap<Integer, Boolean>();
    private ShipProxyHandler shipProxyHandler;
    private JSONConverter jsonConverter = new JSONConverter();
    private Ship ship = new Ship();
    private ControlViolation controlViolation;

    private List<String> extraControlZone = new ArrayList<String>();

    private final Logger logger = Logger.getLogger(this.getClass());

    public BufferExecutor(double bufferLimitInSeconds, ShipProxyHandler shipProxyHandler, Map<Integer, Boolean> etaCalculatedShipIds, IncidentListenerImpl incidentListener) {
        this.bufferLimitInSeconds = bufferLimitInSeconds;
        this.shipProxyHandler = shipProxyHandler;
        this.etaCalculatedShipIds = etaCalculatedShipIds;
        this.incidentListener = incidentListener;
        controlViolation = new ControlViolation(this.incidentListener);
    }

    /**
     * Puts the incoming message in the buffer
     * Creates a new buffer when there is a new ShipId
     * @param shipPosition, incoming shipPosition message
     * @throws BufferExecutorException
     * @throws ShipProxyHandlerException
     * @throws JSONConverterException
     */
    @Override
    public void putMessageInBuffer(ShipPosition shipPosition) throws BufferExecutorException, ShipProxyHandlerException, JSONConverterException, OutputChannelException {
        try {
            if(!controlShipBufferExists(shipPosition) && shipPosition != null) {
                bufferPositionMessage = new BufferPositionMessage(bufferLimitInSeconds, shipPosition.getShipId(), ship);
                bufferPositionMessage.addPositionMessage(shipPosition);

                ship = jsonConverter.convertMessageToShip(shipProxyHandler.getInfo(shipPosition.getShipId()));
                ship.setShipId(ship.getIMONumbuer());

                bufferPositionMessage.setShip(ship);
                shipIdList.add(shipPosition.getShipId());
                bufferPositionMessages.add(bufferPositionMessage);


            }
            else {
                for(BufferPositionMessage bufferPositionMessage : bufferPositionMessages) {
                    if (bufferPositionMessage.getShipId() == shipPosition.getShipId()) {
                        bufferPositionMessage.addPositionMessage(shipPosition);
                        if(etaCalculatedShipIds.containsKey(ship.getShipId()))
                            ship.setETA(bufferPositionMessage.getShipPositions(), etaCalculatedShipIds.get(shipPosition.getShipId()));
                    }
                }
                if(incidentListener.getActionType().equals(ActionType.ALLESCHEPENVOORANKER)) {
                    controlViolation.controlHeavyViolationOverall(bufferPositionMessages);
                } else if(incidentListener.getActionType().equals(ActionType.ALLESCHEPENINZONEVOORANKER)) {
                    controlViolation.controlHeavyViolationZone(bufferPositionMessages, extraControlZone);
                }
            }
        } catch(ShipProxyHandlerException e) {
            logger.error("Error while trying to connect to the proxy");
            throw new ShipProxyHandlerException("Error while trying to connect to the proxy", e);
        } catch (JSONConverterException e) {
            logger.error("Error while converting JSON to a Ship object");
            throw new JSONConverterException("Error while converting JSON to a Ship object", e);
        } catch (OutputChannelException e) {
            logger.error("Error outputchannel exception", e);
            throw new OutputChannelException("Error outputchannel exception", e);
        }
    }

    /**
     * Controls the time of the last incoming message of all the ships, if it exceeds the bufferLimitInSeconds,
     * the buffer of this ship will be cleared. Gets called from the Processor class.
     */
    @Override
    public void controlLastMessage() {
        for(BufferPositionMessage bufferPositionMessage : bufferPositionMessages) {
            int index = bufferPositionMessage.getShipPositions().size() - 1;
            Date date = new Date();
            if((date.getTime() - (bufferPositionMessage.getShipPositions().get(index).getTimestamp()).getTime() / 1000) > bufferLimitInSeconds) {
                //bufferPositionMessage.clearBuffer();
            }
        }
    }

    @Override
    public boolean controlShipBufferExists(ShipPosition shipPosition) throws BufferExecutorException {
        for (int i : shipIdList) {
            if(i == shipPosition.getShipId())
                return true;
            else
                return false;
        }
        return false;
    }

    /**
     * The list extraZoneControl is a list that keeps the zones where currently an incident is occurring
     * This method adds an incident zone to the list
     * @param shipId, is the ship ID from the incoming incident message
     */
    public void addExtraControlZone(int shipId) {
        for(BufferPositionMessage bufferPositionMessage: bufferPositionMessages) {
            int size = bufferPositionMessage.getShipPositions().size();
            if(bufferPositionMessage.getShipId() == shipId) {
                extraControlZone.add(bufferPositionMessage.getShipPositions().get(size-1).getcentraleId());
            }
        }
    }

    /**
     * Same as addExtraControlZone but deletes the zone instead of adding it
     * @param shipId
     */
    public void removeExtraControlZone(int shipId) {
        for(BufferPositionMessage bufferPositionMessage: bufferPositionMessages) {
            int size = bufferPositionMessage.getShipPositions().size();
            if(bufferPositionMessage.getShipId() == shipId) {
                extraControlZone.remove(bufferPositionMessage.getShipPositions().get(size-1).getcentraleId());
            }
        }
    }
}
