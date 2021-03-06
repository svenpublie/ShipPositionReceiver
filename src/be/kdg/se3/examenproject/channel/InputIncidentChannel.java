package be.kdg.se3.examenproject.channel;

import com.rabbitmq.client.*;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Class initializes a queue and reads the incoming incidents of the message broker
 * Created by Sven on 4/11/2015.
 */
public class InputIncidentChannel implements InputChannel {
    private ConnectionFactory factory;
    private final static String QUEUE_NAME = "INCIDENT_QUEUE";
    private final static String HOST_NAME = "localhost";
    private boolean sent = false;

    String message;
    Connection connection;
    Channel channel;

    private final Logger logger = Logger.getLogger(this.getClass());

    public InputIncidentChannel(ConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void init() throws InputChannelException {
        factory.setHost(HOST_NAME);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (Exception e) {
            logger.error("Error while initializing the incident input channel", e);
            throw new InputChannelException("Error while initializing the incident input channel", e);
        }
    }

    Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            message = new String(body, "UTF-8");
        }
    };


    @Override
    public String getNextMessage() throws InputChannelException {

        return message;
    }

    @Override
    public void shutDown() throws InputChannelException {
        try {
            connection.close();
        } catch (IOException e) {
            logger.error("Error while closing the InputIncidentChannel", e);
            throw new InputChannelException("Error while closing the InputChannel", e);
        }
    }
}
