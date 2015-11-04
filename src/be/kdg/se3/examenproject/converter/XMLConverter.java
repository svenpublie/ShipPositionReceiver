package be.kdg.se3.examenproject.converter;

import be.kdg.se3.examenproject.dom.IncidentReport;
import be.kdg.se3.examenproject.dom.IncidentMessage;
import be.kdg.se3.examenproject.dom.ShipPosition;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Class to convert XML messages
 * Created by Sven on 2/11/2015.
 */
public class XMLConverter {

    /**
     * @param strMessage: the message in XML-Format
     * @return PositionMessage: the PositionMessage-object that has been converted.
     */
    public ShipPosition convertMessage(String strMessage) throws XMLConverterException {
        try {
            if (strMessage != null) {
                StringReader stringReader = new StringReader(strMessage);
                Unmarshaller unmarshaller = new Unmarshaller();
                return (ShipPosition) unmarshaller.unmarshal(ShipPosition.class, stringReader);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new XMLConverterException("Exception while converting a XML-String to a PositionMessage", e);
        }
    }

    public IncidentMessage convertIncident(String strMessage) throws XMLConverterException {
        try {
            if (strMessage != null) {
                StringReader stringReader = new StringReader(strMessage);
                Unmarshaller unmarshaller = new Unmarshaller(); // volgens mij moet ge zelfs geen instantie maken, gwn Unmarshaller.unmarshal(string)
                //return (IncidentMessage) unmarshaller.unmarshal(IncidentMessage.class, stringReader);
                return (IncidentMessage) Unmarshaller.unmarshal(IncidentMessage.class, stringReader);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new XMLConverterException("Exception while converting a XML-String to an IncidentReport", e);
        }
    }

    public String convertIncidentToXML(IncidentReport incidentReport) throws XMLConverterException {
        try {
            Writer writer = new StringWriter();
            Marshaller marshaller = new Marshaller();
            marshaller.marshal(incidentReport, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new XMLConverterException("Exception while converting a incidentReport to a XML-String", e);
        }
    }
}