package be.kdg.se3.examenproject.model;

/**
 * Model class for the cargo list used in the model class Ships
 * Created by Sven on 3/11/2015.
 */
public class Cargo {
    private String type;
    private int amount;

    public Cargo(String type, int amount) {
        this.amount = amount;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getAmount() {
        return amount;
    }
}
