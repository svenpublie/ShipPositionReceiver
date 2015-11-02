package be.kdg.se3.examenproject;

import be.kdg.se3.examenproject.channel.InputChannel;
import be.kdg.se3.examenproject.channel.XMLInputChannel;
import be.kdg.se3.examenproject.processor.Processor;
import be.kdg.se3.examenproject.processor.ProcessorException;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by Sven on 2/11/2015.
 */
public class Main {
    public static void main(String[] args) throws ProcessorException {
        ConnectionFactory factory = new ConnectionFactory();
        InputChannel inputChannel = new XMLInputChannel(factory);


        Processor processor = new Processor(inputChannel);
        processor.start();

    }
}
