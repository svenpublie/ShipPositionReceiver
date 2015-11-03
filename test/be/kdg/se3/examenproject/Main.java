package be.kdg.se3.examenproject;

import be.kdg.se3.examenproject.channel.InputChannel;
import be.kdg.se3.examenproject.channel.XMLInputChannel;
import be.kdg.se3.examenproject.control.BufferPositionMessage;
import be.kdg.se3.examenproject.converter.JSONConverter;
import be.kdg.se3.examenproject.converter.XMLConverter;
import be.kdg.se3.examenproject.dbwriter.DBWriter;
import be.kdg.se3.examenproject.dbwriter.DBWriterString;
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

        //DBWriter
        DBWriter dbWriter = new DBWriterString();

        //Converters
        JSONConverter jsonConverter = new JSONConverter();
        XMLConverter xmlConverter = new XMLConverter();

        //Buffers
        int bufferLimitCameraMessage = 200;
        BufferPositionMessage bufferPositionMessage = new BufferPositionMessage(bufferLimitCameraMessage);

        //Proxy
        int proxyHandlerTryToConnectLimit = 10;
        int proxyHandlerClearCacheLimit = 100;
        ShipProxyHandler shipProxyHandler = new ShipProxyHandler(proxyHandlerTryToConnectLimit, proxyHandlerClearCacheLimit);
        shipProxyHandler.setShipServiceProxy(new ShipServiceProxy());

        int sleepInterval = 200;
        Processor processor = new Processor(inputChannel, dbWriter, xmlConverter, sleepInterval);
        processor.start();

    }
}
