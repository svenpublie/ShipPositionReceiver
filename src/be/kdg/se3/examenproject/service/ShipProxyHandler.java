package be.kdg.se3.examenproject.service;

import be.kdg.se3.proxy.ShipServiceProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * This class interacts with the ShipServiceProxy
 * Created by Sven on 3/11/2015.
 */
public class ShipProxyHandler {
    private ShipServiceProxy shipServiceProxy;
    private final String URL = "www.services4se3.com/shipservice/";
    private String shipInfo;

    Map<Integer, String> shipMap = new HashMap<Integer, String>();

    /**
     * @param shipId: the shipId of the PositionMessage
     * @return the return data of the shipServiceProxy in JSON format
     * @throws ShipProxyHandlerException: If it can't reach the shipServiceProxy or if there is an error during the loop, it will throw an Error.
     */
    public String getInfo(int shipId) throws ShipProxyHandlerException {
        try {
            if (shipMap.containsKey(shipId))
                shipInfo = shipMap.get(shipId);
            else {
                shipInfo = shipServiceProxy.get(URL + shipId);
                shipMap.put(shipId, shipInfo);
            }
            return shipInfo;
        } catch (Exception e) {
            throw new ShipProxyHandlerException("Exception while connecting to the ShipServiceProxy", e);
        }
    }
}
