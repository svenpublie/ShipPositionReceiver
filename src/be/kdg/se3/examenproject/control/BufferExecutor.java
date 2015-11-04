package be.kdg.se3.examenproject.control;

import be.kdg.se3.examenproject.converter.JSONConverter;
import be.kdg.se3.examenproject.converter.JSONConverterException;
import be.kdg.se3.examenproject.dom.ShipPosition;
import be.kdg.se3.examenproject.service.ShipProxyHandler;
import be.kdg.se3.examenproject.service.ShipProxyHandlerException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sven on 4/11/2015.
 */
public class BufferExecutor {
    private double bufferLimitInSeconds;
    private BufferPositionMessage bufferPositionMessage;
    private List<BufferPositionMessage> bufferPositionMessages = new ArrayList<BufferPositionMessage>();
    private List<Integer> shipIdList = new ArrayList<Integer>();
    private ShipProxyHandler shipProxyHandler;
    private JSONConverter jsonConverter = new JSONConverter();

    private final Logger logger = Logger.getLogger(this.getClass());

    public BufferExecutor(double bufferLimitInSeconds, ShipProxyHandler shipProxyHandler) {
        this.bufferLimitInSeconds = bufferLimitInSeconds;
        this.shipProxyHandler = shipProxyHandler;
    }

    public void putMessageInBuffer(ShipPosition shipPosition) throws ControlExecutorException, ShipProxyHandlerException, JSONConverterException {
        try {
            if(!controlShipBufferExists(shipPosition) && shipPosition != null) {
                bufferPositionMessage = new BufferPositionMessage(bufferLimitInSeconds, shipPosition.getShipId(), jsonConverter.convertMessageToShip(shipProxyHandler.getInfo(shipPosition.getShipId())));
                bufferPositionMessage.addPositionMessage(shipPosition);
                shipIdList.add(shipPosition.getShipId());
                bufferPositionMessages.add(bufferPositionMessage);
            }
            else {
                for(BufferPositionMessage bufferPositionMessage : bufferPositionMessages) {
                    if (bufferPositionMessage.getShipId() == shipPosition.getShipId()) {
                        bufferPositionMessage.addPositionMessage(shipPosition);
                    }
                }
            }
        } catch(ShipProxyHandlerException e) {
            logger.error("Error while trying to connect to the proxy");
            throw new ShipProxyHandlerException("Error while trying to connect to the proxy", e);
        } catch (JSONConverterException e) {
            logger.error("Error while converting JSON to a Ship object");
            throw new JSONConverterException("Error while converting JSON to a Ship object", e);
        }
    }

    public void controlLastMessage() {
        for(BufferPositionMessage bufferPositionMessage : bufferPositionMessages) {
            int index = bufferPositionMessage.getShipPositions().size() - 1;
            Date date = new Date();
            if((date.getTime() - (bufferPositionMessage.getShipPositions().get(index).getTimestamp()).getTime() / 1000) > bufferLimitInSeconds) {
                //bufferPositionMessage.clearBuffer();
            }
        }
    }

    public boolean controlShipBufferExists(ShipPosition shipPosition) throws ControlExecutorException {
        for (int i : shipIdList) {
            if(i == shipPosition.getShipId())
                return true;
            else
                return false;
        }
        return false;
    }



}
