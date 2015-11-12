package be.kdg.se3.examenproject.converter;

import be.kdg.se3.examenproject.model.IncidentReport;
import be.kdg.se3.examenproject.model.IncidentMessage;
import be.kdg.se3.examenproject.model.ShipPosition;
import org.apache.log4j.Logger;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Class to convert XML messages to model class objects and vice versa
 * Created by Sven on 2/11/2015.
 */
public class XMLConverter {
    private final Logger logger = Logger.getLogger(this.getClass());
    /**
     * XML to model object
     * @param strMessage: the message in XML-Format
     * @return PositionMessage: the PositionMessage-object that has been converted.
     */
    public ShipPosition convertMessage(String strMessage) throws XMLConverterException {
        try {
            StringReader stringReader = new StringReader(strMessage);
            Unmarshaller unmarshaller = new Unmarshaller();
            return (ShipPosition) unmarshaller.unmarshal(ShipPosition.class, stringReader);
        } catch (Exception e) {
            logger.error("Exception while converting a XML-String to a PositionMessage");
            throw new XMLConverterException("Exception while converting a XML-String to a PositionMessage", e);
        }
    }

    /**
     * XML to model object
     * @param strMessage: the message in XML-Format
     * @return IncidentMessage: the IncidentMessage-object that has been converted.
     */
    public IncidentMessage convertIncident(String strMessage) throws XMLConverterException {
        try {
            StringReader stringReader = new StringReader(strMessage);
            return (IncidentMessage) Unmarshaller.unmarshal(IncidentMessage.class, stringReader);

        } catch (Exception e) {
            logger.error("Exception while converting a XML-String to an IncidentReport");
            throw new XMLConverterException("Exception while converting a XML-String to a IncidentMessage", e);
        }
    }

    /**
     * Model objext to XML
     * @param incidentReport, model object of IncidentReport
     * @return XML string message
     */
    public String convertIncidentToXML(IncidentReport incidentReport) throws XMLConverterException {
        try {
            Writer writer = new StringWriter();
            Marshaller marshaller = new Marshaller();
            marshaller.marshal(incidentReport, writer);
            logger.info("IncidentReport converted to XML");
            return writer.toString();
        } catch (Exception e) {
            logger.error("Exception while converting a incidentReport to a XML-String");
            throw new XMLConverterException("Exception while converting a incidentReport to a XML-String", e);
        }
    }
}