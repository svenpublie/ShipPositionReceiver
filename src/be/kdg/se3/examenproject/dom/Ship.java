package be.kdg.se3.examenproject.dom;

import java.util.List;

/**
 * Created by Sven on 3/11/2015.
 */
public class Ship {
    private int shipId;
    private String IMONumbuer;
    private boolean dangereousCargo;
    private int numberOfPassengers;
    private List<Cargo> cargo;

    public Ship () {

    }

    public Ship(String IMONumbuer, boolean dangereousCargo, int numberOfPassengers, List<Cargo> cargo) {
        this.IMONumbuer = IMONumbuer;
        this.dangereousCargo = dangereousCargo;
        this.numberOfPassengers = numberOfPassengers;
        this.cargo = cargo;
    }

    public boolean getdangereousCargo() {
        return dangereousCargo;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
}
