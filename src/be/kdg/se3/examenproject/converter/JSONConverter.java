package be.kdg.se3.examenproject.converter;

import be.kdg.se3.examenproject.model.Cargo;
import be.kdg.se3.examenproject.model.Ship;
import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class converts JSON Messages to model class objects
 * Created by Sven on 3/11/2015.
 */
public class JSONConverter {
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * @param jsonString: the JSON-Message that needs to be converted
     * @return Ship: new Ship object
     * @throws JSONConverterException
     */
    public Ship convertMessageToShip(String jsonString) throws JSONConverterException {
        try {
            JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            String shipId = jsonObject.getString("IMO");
            boolean dangereousCargo = jsonObject.getBoolean("dangereousCargo");
            int numberOfPassengers = jsonObject.getInt("numberOfPassangers");

            String typeCargo;
            int amountCargo;

            JsonArray listCargo = jsonObject.getJsonArray("cargo");

            List<Cargo> listCargoConverted = new ArrayList<Cargo>();
            for (int i = 0; i < listCargo.size(); i++) {
                JsonObject cargo = listCargo.getJsonObject(i);

                typeCargo = cargo.getString("type");
                amountCargo = cargo.getInt("amount");

                listCargoConverted.add(new Cargo(typeCargo, amountCargo));
            }

            return new Ship(shipId, dangereousCargo, numberOfPassengers, listCargoConverted);

        } catch (Exception e) {
            String jsonConverterError = "Exception while converting a JSON-Message to a Ship object";
            logger.error(jsonConverterError, e);
            throw new JSONConverterException(jsonConverterError, e);
        }
    }
}
