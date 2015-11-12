package be.kdg.se3.examenproject.incident;

import be.kdg.se3.examenproject.channel.OutputChannel;
import be.kdg.se3.examenproject.control.BufferExecutor;
import be.kdg.se3.examenproject.converter.JSONConverter;
import be.kdg.se3.examenproject.converter.JSONConverterException;
import be.kdg.se3.examenproject.converter.XMLConverter;
import be.kdg.se3.examenproject.converter.XMLConverterException;
import be.kdg.se3.examenproject.model.IncidentReport;
import be.kdg.se3.examenproject.model.IncidentMessage;
import be.kdg.se3.examenproject.model.Ship;
import be.kdg.se3.examenproject.service.ShipProxyHandler;
import be.kdg.se3.examenproject.service.ShipProxyHandlerException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * This class will create an incident, define the right action and put a incidentReport back on the outputChannel
 * Created by Sven on 4/11/2015.
 */
public class IncidentListenerImpl {
    private final static String ZONE_VOOR_ANKER = "AlleSchepenInZoneVoorAnker";
    private final static String ALLE_VOOR_ANKER = "AlleSchepenVoorAnker";
    private final static String ALLES_NORMAAL = "AllesNormaal";
    private ShipProxyHandler shipProxyHandler;
    private OutputChannel outputChannel;
    private Ship ship = new Ship();
    private IncidentMessage incidentMessage;
    private XMLConverter xmlConverter;
    private BufferExecutor bufferExecutor;

    private ActionType actionType;

    private List<Integer> extraControlZoneShipIds = new ArrayList<Integer>();

    private JSONConverter jsonConverter = new JSONConverter();

    private final Logger logger = Logger.getLogger(this.getClass());

    public IncidentListenerImpl(ShipProxyHandler shipProxyHandler, OutputChannel outputChannel, XMLConverter xmlConverter) {
        this.shipProxyHandler = shipProxyHandler;
        this.outputChannel = outputChannel;
        this.xmlConverter = xmlConverter;
        this.actionType = ActionType.ALLESNORMAAL;
    }

    /**
     * Converts the XML incident message to a IncidentMessage object, gets the right action,
     * creates an Incident report and sends it to putIncidentOutPutChannel
     * @param message
     * @throws IOException
     */
    public void createIncident(String message) throws IOException {
        try {
            IncidentMessage incidentMessage = xmlConverter.convertIncident(message);
            System.out.println(incidentMessage.toString());
            int shipId = incidentMessage.getShipId();
            String typeOfIncident = incidentMessage.getIncidentStatus();

            this.incidentMessage = new IncidentMessage(shipId, typeOfIncident);
            String action = getTypeOfAction(this.incidentMessage);

            ship = getIncidentShipInfo(shipId);

            putIncidentOnOutputChannel(new IncidentReport(shipId, typeOfIncident, ship.getNumberOfPassengers(), ship.getdangereousCargo(), action));
        } catch (XMLConverterException e) {
            logger.error("Exception while converting XML to IncidentMessage", e);
        } catch (TimeoutException e) {
            logger.error("Timeout exception", e);
        }
    }

    /**
     * Defines the action that needs to be taken
     * @param incidentMessage, incoming incident message
     * @return String with the right action
     */
    public String getTypeOfAction(IncidentMessage incidentMessage) {
        try {
            String strShip = shipProxyHandler.getInfo(incidentMessage.getShipId());
            if (incidentMessage.getIncidentStatus().equalsIgnoreCase("schade")) {
                if(jsonConverter.convertMessageToShip(strShip).getdangereousCargo()){
                    this.actionType = ActionType.ALLESCHEPENVOORANKER;
                    return ALLE_VOOR_ANKER;
                }
                else {
                    extraControlZoneShipIds.add(incidentMessage.getShipId());
                    bufferExecutor.addExtraControlZone(incidentMessage.getShipId());
                    this.actionType = ActionType.ALLESCHEPENINZONEVOORANKER;
                    return ZONE_VOOR_ANKER;
                }
            } else if (incidentMessage.getIncidentStatus().equalsIgnoreCase("man over boord")) {
                this.actionType = ActionType.ALLESCHEPENVOORANKER;
                return ALLE_VOOR_ANKER;
            } else if (incidentMessage.getIncidentStatus().equalsIgnoreCase("alles normaal")) {
                if(extraControlZoneShipIds.contains(incidentMessage.getShipId())) {
                    bufferExecutor.removeExtraControlZone(incidentMessage.getShipId());
                    extraControlZoneShipIds.remove(incidentMessage.getShipId());
                }
                this.actionType = ActionType.ALLESNORMAAL;
                return ALLES_NORMAAL;
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
            System.out.println(incidentReport.toString());
        } catch (XMLConverterException e) {
            logger.error("Error while converting IncidentReport to XML");
        }
    }

    public void setBufferExecutor(BufferExecutor bufferExecutor) {
        this.bufferExecutor = bufferExecutor;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
