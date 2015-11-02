package be.kdg.se3.examenproject.processor;

import be.kdg.se3.examenproject.channel.InputChannel;
import be.kdg.se3.examenproject.channel.InputChannelException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sven on 2/11/2015.
 */
public class Processor {
    private InputChannel inputChannel;
    private final int timeout = 2000;
    boolean stopped;
    String message;

    private final Logger logger = Logger.getLogger(this.getClass());

    public Processor(InputChannel inputChannel) {
        this.inputChannel = inputChannel;
    }

    public void start() throws ProcessorException {
        try {
            stopped = false;
            inputChannel.init();
            while (!stopped) {
            try {
                message = inputChannel.getNextMessage();
                System.out.println(message);
                Thread.sleep(timeout);
            } catch (InputChannelException e) {
                logger.error("Exception from the input channel", e);
            }
        }
            inputChannel.shutDown();
        } catch (Exception e) {
            String processorError = "Fatal exception while executing the processor";
            logger.fatal(processorError, e);
            throw new ProcessorException(processorError, e);
        }

    }

    public void stop() {
        stopped = true;
    }
}
