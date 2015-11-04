package be.kdg.se3.examenproject.incident;

import be.kdg.se3.examenproject.channel.OutputChannel;
import be.kdg.se3.examenproject.converter.JSONConverter;
import be.kdg.se3.examenproject.converter.JSONConverterException;
import be.kdg.se3.examenproject.converter.XMLConverter;
import be.kdg.se3.examenproject.converter.XMLConverterException;
import be.kdg.se3.examenproject.dom.IncidentReport;
import be.kdg.se3.examenproject.dom.IncidentMessage;
import be.kdg.se3.examenproject.dom.Ship;
import be.kdg.se3.examenproject.service.ShipProxyHandler;
import be.kdg.se3.examenproject.service.ShipProxyHandlerException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sven on 4/11/2015.
 */
public class IncidentListenerImpl {
    private final static String ZONE_VOOR_ANKER = "AlleSchepenInZoneVoorAnker";
    private final static String ALLE_VOOR_ANKER = "AlleSchepenVoorAnker";
    private ShipProxyHandler shipProxyHandler;
    private OutputChannel outputChannel;
    private Ship ship = new Ship();
    private IncidentMessage incidentMessage;
    private XMLConverter xmlConverter;

    private JSONConverter jsonConverter = new JSONConverter();

    private final Logger logger = Logger.getLogger(this.getClass());

    public IncidentListenerImpl(ShipProxyHandler shipProxyHandler, OutputChannel outputChannel, XMLConverter xmlConverter) {
        this.shipProxyHandler = shipProxyHandler;
        this.outputChannel = outputChannel;
        this.xmlConverter = xmlConverter;
    }

    public void createIncident(String message) throws IOException {
        try {
            IncidentMessage incidentMessage1 = xmlConverter.convertIncident(message);
            int shipId = incidentMessage1.getShipId();
            String typeOfIncident = incidentMessage1.getIncidentStatus();

            incidentMessage = new IncidentMessage(shipId, typeOfIncident);
            String action = getTypeOfAction(incidentMessage);

            ship = getIncidentShipInfo(shipId);
            //IncidentReport incident = new IncidentReport(shipId, typeOfIncident, ship.getNumberOfPassengers(), ship.getdangereousCargo(), action);

            putIncidentOnOutputChannel(new IncidentReport(shipId, typeOfIncident, ship.getNumberOfPassengers(), ship.getdangereousCargo(), action));
        } catch (XMLConverterException e) {
            logger.error("Exception while converting XML to IncidentMessage", e);
        } catch (TimeoutException e) {
            logger.error("Timeout exception", e);
        }
    }

    public String getTypeOfAction(IncidentMessage incidentMessage) {
        try {
            String strShip = shipProxyHandler.getInfo(incidentMessage.getShipId());
            if (incidentMessage.getIncidentStatus().equalsIgnoreCase("schade")) {
                if(jsonConverter.convertMessageToShip(strShip).getdangereousCargo())
                    return ALLE_VOOR_ANKER;
                else
                    return ZONE_VOOR_ANKER;
            } else if (incidentMessage.getIncidentStatus().equalsIgnoreCase("man overboord")) {
                return ALLE_VOOR_ANKER;
            }
        } catch (ShipProxyHandlerException e) {
            logger.error("Error while trying to connect with the proxy service");
        } catch (JSONConverterException e) {
            logger.error("Error while trying to convert JSON to Ship");
        }
        return "";
    }

    public Ship getIncidentShipInfo(int shipId) {
        try {
            return jsonConverter.convertMessageToShip(shipProxyHandler.getInfo(shipId));
        } catch (ShipProxyHandlerException e) {
            logger.error("Error while trying to connect with the proxy service");
        } catch (JSONConverterException e) {
            logger.error("Error while trying to convert JSON to Ship");
        }
        return null;
    }

    public void putIncidentOnOutputChannel (IncidentReport incidentReport) throws IOException, TimeoutException{
        try {
            String message = xmlConverter.convertIncidentToXML(incidentReport);
            outputChannel.init();
            outputChannel.send(message);
            outputChannel.shutDown();
        } catch (XMLConverterException e) {
            logger.error("Error while converting IncidentReport to XML");
        }
    }
}
