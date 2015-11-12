package be.kdg.se3.examenproject.channel;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * Initializes an incident report queue and puts incident reports on this queue
 * Created by Sven on 4/11/2015.
 */
public class XMLOutputChannel implements OutputChannel {
    private final static String QUEUE_NAME = "INCIDENT_REPORT_QUEUE";
    ConnectionFactory factory;
    Connection connection;
    Channel channel;

    public XMLOutputChannel(ConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void init() throws IOException, TimeoutException {
        try {
            factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (OutputChannelException e) {
            throw new OutputChannelException("Error while trying to initialize the send queue", e);
        }
    }

    @Override
    public void send(String message) throws IOException {
        try {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        } catch (OutputChannelException e) {
            throw new OutputChannelException("Error while trying to put a message on the queue", e);
        }
    }

    @Override
    public void shutDown() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
