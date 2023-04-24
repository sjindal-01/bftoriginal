package bftsmart.demo.v2vapp;

public class Vehicle {
    Location location;

    double efficiency;

    String id;

    public Vehicle(String id, Location location, double efficiency) {
        this.id = id;
        this.location = location;
        this.efficiency = efficiency;
    }
}
