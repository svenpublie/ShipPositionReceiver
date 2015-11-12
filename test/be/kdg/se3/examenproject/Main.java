package be.kdg.se3.examenproject;

import be.kdg.se3.examenproject.calculator.ETACalculator;
import be.kdg.se3.examenproject.channel.*;
import be.kdg.se3.examenproject.buffer.BufferExecutor;
import be.kdg.se3.examenproject.converter.JSONConverter;
import be.kdg.se3.examenproject.converter.XMLConverter;
import be.kdg.se3.examenproject.dbwriter.DBWriter;
import be.kdg.se3.examenproject.dbwriter.DBWriterString;
import be.kdg.se3.examenproject.incident.IncidentListenerImpl;
import be.kdg.se3.examenproject.processor.Processor;
import be.kdg.se3.examenproject.processor.ProcessorException;
import be.kdg.se3.examenproject.service.ShipProxyHandler;
import be.kdg.se3.proxy.ShipServiceProxy;
import com.rabbitmq.client.ConnectionFactory;

import java.util.*;

/**
 * Created by Sven on 2/11/2015.
 */
public class Main {
    public static void main(String[] args) throws ProcessorException {

        //Channels
        ConnectionFactory factory = new ConnectionFactory();
        InputChannel inputChannel = new XMLInputChannel(factory);
        InputChannel incidentChannel = new InputIncidentChannel(factory);
        OutputChannel outputChannel = new XMLOutputChannel(factory);

        //DBWriter
        DBWriter dbWriter = new DBWriterString();

        //Converters
        JSONConverter jsonConverter = new JSONConverter();
        XMLConverter xmlConverter = new XMLConverter();

        //Proxy
        int proxyHandlerTryToConnectLimit = 10;
        int proxyHandlerClearCacheLimit = 100;
        ShipProxyHandler shipProxyHandler = new ShipProxyHandler(proxyHandlerTryToConnectLimit, proxyHandlerClearCacheLimit);
        shipProxyHandler.setShipServiceProxy(new ShipServiceProxy());

        //Incidents
        IncidentListenerImpl incidentListenerImpl = new IncidentListenerImpl(shipProxyHandler, outputChannel, xmlConverter);

        //ETA
        ETACalculator etaCalculator = new ETACalculator();

        //Control
        long bufferLimitInSeconds = 200000;
        //Map for calulating ETA's, Key is shipId and Value is for calculating it every message (=TRUE) or only when entering a new zone (=FALSE)
        Map<Integer, Boolean> etaCalculatedShipIds = new HashMap<Integer, Boolean>();
        etaCalculatedShipIds.put(5498634, true);
        BufferExecutor bufferExecutor = new BufferExecutor(bufferLimitInSeconds, shipProxyHandler, etaCalculatedShipIds, incidentListenerImpl);
        incidentListenerImpl.setBufferExecutor(bufferExecutor);

        int sleepInterval = 2000;
        Processor processor = new Processor(inputChannel, incidentChannel, incidentListenerImpl, dbWriter, xmlConverter, bufferExecutor, sleepInterval);
        processor.start();

    }
}
