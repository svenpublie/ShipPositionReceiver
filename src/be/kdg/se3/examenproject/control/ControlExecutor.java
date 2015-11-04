package be.kdg.se3.examenproject.control;

import be.kdg.se3.examenproject.converter.JSONConverter;
import be.kdg.se3.examenproject.converter.JSONConverterException;
import be.kdg.se3.examenproject.dom.PositionMessage;
import be.kdg.se3.examenproject.dom.Ship;
import be.kdg.se3.examenproject.service.ShipProxyHandler;
import be.kdg.se3.examenproject.service.ShipProxyHandlerException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sven on 4/11/2015.
 */
public class ControlExecutor {
    private double bufferLimitInSeconds;
    private BufferPositionMessage bufferPositionMessage;
    private List<BufferPositionMessage> bufferPositionMessages = new ArrayList<BufferPositionMessage>();
    private List<Integer> shipIdList = new ArrayList<Integer>();
    private ShipProxyHandler shipProxyHandler;
    private JSONConverter jsonConverter = new JSONConverter();

    private final Logger logger = Logger.getLogger(this.getClass());

    public ControlExecutor(double bufferLimitInSeconds, ShipProxyHandler shipProxyHandler) {
        this.bufferLimitInSeconds = bufferLimitInSeconds;
        this.shipProxyHandler = shipProxyHandler;
    }

    public void putMessageInBuffer(PositionMessage positionMessage) throws ControlExecutorException, ShipProxyHandlerException, JSONConverterException {
        try {
            if(!controlShipBufferExists(positionMessage) && positionMessage != null) {
                bufferPositionMessage = new BufferPositionMessage(bufferLimitInSeconds, positionMessage.getShipid(), jsonConverter.convertMessageToShip(shipProxyHandler.getInfo(positionMessage.getShipid())));
                bufferPositionMessage.addPositionMessage(positionMessage);
                shipIdList.add(positionMessage.getShipid());
                bufferPositionMessages.add(bufferPositionMessage);
            }
            else {
                for(BufferPositionMessage bufferPositionMessage : bufferPositionMessages) {
                    if (bufferPositionMessage.getShipId() == positionMessage.getShipid()) {
                        bufferPositionMessage.addPositionMessage(positionMessage);
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
            int length = bufferPositionMessage.getPositionMessages().size();
            Date date = new Date();

            if((date.getTime() - (bufferPositionMessage.getPositionMessages().get(length-1).getTimestamp()).getTime() / 1000) > bufferLimitInSeconds) {
                bufferPositionMessage.clearBuffer();
            }
        }
    }

    public boolean controlShipBufferExists(PositionMessage positionMessage) throws ControlExecutorException {
        for (Integer i : shipIdList) {
            if(i == positionMessage.getShipid())
                return true;
            else
                return false;
        }
        return false;
    }



}
