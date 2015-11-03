package be.kdg.se3.examenproject.processor;

import be.kdg.se3.examenproject.channel.InputChannel;
import be.kdg.se3.examenproject.channel.InputChannelException;
import be.kdg.se3.examenproject.converter.XMLConverter;
import be.kdg.se3.examenproject.converter.XMLConverterException;
import be.kdg.se3.examenproject.dbwriter.DBWriter;
import be.kdg.se3.examenproject.dbwriter.DBWriterException;
import be.kdg.se3.examenproject.dom.PositionMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sven on 2/11/2015.
 */
public class Processor {
    private InputChannel inputChannel;
    private DBWriter dbWriter;
    private XMLConverter xmlConverter;

    private final int sleepinterval;
    boolean stopped;
    String message;

    private final Logger logger = Logger.getLogger(this.getClass());

    public Processor(InputChannel inputChannel, DBWriter dbWriter, XMLConverter xmlConverter, int sleepInterval) {
        this.inputChannel = inputChannel;
        this.dbWriter = dbWriter;
        this.xmlConverter = xmlConverter;
        this.sleepinterval = sleepInterval;
    }

    public void start() throws ProcessorException {
        try {
            stopped = false;
            inputChannel.init();
            while (!stopped) {
                try {
                    message = inputChannel.getNextMessage();
                    PositionMessage positionMessage = xmlConverter.convertMessage(message);
                    dbWriter.writeMessageToDatabase(positionMessage);
                    //System.out.println(message);
                    Thread.sleep(sleepinterval);
                } catch (InputChannelException e) {
                    logger.error("Exception from the input channel", e);
                } catch (XMLConverterException e) {
                    logger.error("Exception while converting XML", e);
                } catch (DBWriterException e) {
                    logger.error("Exception while executing the database writer", e);
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
