package be.kdg.se3.examenproject;

import be.kdg.se3.examenproject.channel.*;
import be.kdg.se3.examenproject.control.BufferExecutor;
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

        //Control
        long bufferLimitInSeconds = 200000;
        BufferExecutor bufferExecutor = new BufferExecutor(bufferLimitInSeconds, shipProxyHandler);

        int sleepInterval = 2000;
        Processor processor = new Processor(inputChannel, incidentChannel, incidentListenerImpl, dbWriter, xmlConverter, bufferExecutor, sleepInterval);
        processor.start();

    }
}
