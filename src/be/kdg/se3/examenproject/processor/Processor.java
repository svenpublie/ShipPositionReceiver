package be.kdg.se3.examenproject.processor;

import be.kdg.se3.examenproject.channel.InputChannel;
import be.kdg.se3.examenproject.channel.InputChannelException;
import be.kdg.se3.examenproject.buffer.BufferExecutor;
import be.kdg.se3.examenproject.converter.XMLConverter;
import be.kdg.se3.examenproject.converter.XMLConverterException;
import be.kdg.se3.examenproject.dbwriter.DBWriter;
import be.kdg.se3.examenproject.dbwriter.DBWriterException;
import be.kdg.se3.examenproject.model.ShipPosition;
import be.kdg.se3.examenproject.incident.IncidentListenerImpl;
import org.apache.log4j.Logger;


/**
 * Processes the application, reads, controls, buffers
 * Created by Sven on 2/11/2015.
 */
public class Processor {
    private InputChannel inputChannel;
    private InputChannel inputIncidentChannel;
    private DBWriter dbWriter;
    private XMLConverter xmlConverter;
    private BufferExecutor bufferExecutor;
    private IncidentListenerImpl incidentListenerImpl;

    private final int sleepInterval;
    boolean stopped;
    String positionMessage = "";
    String previousPositionMessage = "";

    String incidentMessage = "";
    String previousIncidentMessage = "";

    ShipPosition shipPosition = new ShipPosition();

    private final Logger logger = Logger.getLogger(this.getClass());

    public Processor(InputChannel inputChannel, InputChannel inputIncidentChannel, IncidentListenerImpl incidentListenerImpl, DBWriter dbWriter, XMLConverter xmlConverter, BufferExecutor bufferExecutor, int sleepInterval) {
        this.inputChannel = inputChannel;
        this.inputIncidentChannel = inputIncidentChannel;
        this.incidentListenerImpl = incidentListenerImpl;
        this.dbWriter = dbWriter;
        this.xmlConverter = xmlConverter;
        this.bufferExecutor = bufferExecutor;
        this.sleepInterval = sleepInterval;
    }

    /**
     * Starts the whole proces of the application
     * @throws ProcessorException
     */
    public void start() throws ProcessorException {
        try {
            stopped = false;
            inputChannel.init();
            inputIncidentChannel.init();
            while (!stopped) {
                try {
                    positionMessage = inputChannel.getNextMessage();
                    incidentMessage = inputIncidentChannel.getNextMessage();

                    if(positionMessage != null && !positionMessage.equals(previousPositionMessage)) {
                        shipPosition = xmlConverter.convertMessage(positionMessage);
                        dbWriter.writeMessageToDatabase(shipPosition);
                        bufferExecutor.putMessageInBuffer(shipPosition);
                        bufferExecutor.controlLastMessage();
                    }

                    if (incidentMessage != null && !incidentMessage.equals(previousIncidentMessage)) {
                        incidentListenerImpl.createIncident(incidentMessage);
                    }
                    Thread.sleep(sleepInterval);
                    previousPositionMessage = positionMessage;
                    previousIncidentMessage = incidentMessage;
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
