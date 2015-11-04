package be.kdg.se3.examenproject.converter;

import be.kdg.se3.examenproject.dom.Incident;
import be.kdg.se3.examenproject.dom.PositionMessage;
import org.exolab.castor.xml.Unmarshaller;

import java.io.StringReader;

/**
 * Class to convert XML messages
 * Created by Sven on 2/11/2015.
 */
public class XMLConverter {

    /**
     * @param strMessage: the message in XML-Format
     * @return PositionMessage: the PositionMessage-object that has been converted.
     */
    public PositionMessage convertMessage(String strMessage) throws XMLConverterException {
        try {
            if (strMessage != null) {
                StringReader stringReader = new StringReader(strMessage);
                Unmarshaller unmarshaller = new Unmarshaller();
                return (PositionMessage) unmarshaller.unmarshal(PositionMessage.class, stringReader);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            throw  new XMLConverterException("Exception while converting a XML-String to a PositionMessage", e);
        }
    }

    public Incident convertIncident(String strMessage) throws XMLConverterException {
        try {
            if (strMessage != null) {
                StringReader stringReader = new StringReader(strMessage);
                Unmarshaller unmarshaller = new Unmarshaller();
                return (Incident) unmarshaller.unmarshal(Incident.class, stringReader);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            throw  new XMLConverterException("Exception while converting a XML-String to a Incident", e);
        }
    }

}
