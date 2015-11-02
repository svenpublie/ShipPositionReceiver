package be.kdg.se3.examenproject.channel;

import com.rabbitmq.client.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sven on 2/11/2015.
 */
public class XMLInputChannel implements InputChannel {
    private ConnectionFactory factory;
    private final static String QUEUE_NAME = "ships";
    private final static String HOST_NAME = "localhost";
    Connection connection;
    Channel channel;

    private final Logger logger = Logger.getLogger(this.getClass());

    public XMLInputChannel(ConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void init() throws InputChannelException, IOException, TimeoutException {
        factory.setHost(HOST_NAME);
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                throws IOException {
            String message = new String(body, "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        }
    };


    @Override
    public String getNextMessage() throws InputChannelException {
        return null;
    }

    @Override
    public void shutDown() throws InputChannelException {
        connection.abort();
    }
}
