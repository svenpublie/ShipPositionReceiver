package be.kdg.se3.examenproject.service;

import be.kdg.se3.proxy.ShipServiceProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * This class interacts with the ShipServiceProxy
 * Created by Sven on 3/11/2015.
 */
public class ShipProxyHandler extends ShipServiceProxy{
    private ShipServiceProxy shipServiceProxy;
    private final String URL = "www.services4se3.com/shipservice/";
    private String shipInfo = null;

    private int tryCounter;
    private int clearCacheCounter;

    Map<Integer, String> shipMap = new HashMap<Integer, String>();

    public ShipProxyHandler(int tryCounter, int clearCacheCounter) {
        this.tryCounter = tryCounter;
        this.clearCacheCounter = clearCacheCounter;
    }

    /**
     * @param shipId: the shipId of the PositionMessage
     * @return the return data of the shipServiceProxy in JSON format
     * @throws ShipProxyHandlerException: If it can't reach the shipServiceProxy or if there is an error during the loop, it will throw an Error.
     */
    public String getInfo(int shipId) throws ShipProxyHandlerException {
        try {
            int i = 0;
            while (shipInfo == null && tryCounter > i ) {
                if (shipMap.containsKey(shipId))
                    shipInfo = shipMap.get(shipId);
                else {
                    shipInfo = shipServiceProxy.get(URL + shipId);
                    shipMap.put(shipId, shipInfo);
                }
                i++;
                if(shipMap.size() > clearCacheCounter)
                    shipMap.clear();
            }
            return shipInfo;
        } catch (Exception e) {
            throw new ShipProxyHandlerException("Exception while connecting to the ShipServiceProxy", e);
        }
    }

    public void setShipServiceProxy(ShipServiceProxy shipServiceProxy) {
        this.shipServiceProxy = shipServiceProxy;
    }
}
