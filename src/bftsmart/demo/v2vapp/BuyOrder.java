package bftsmart.demo.v2vapp;

public class BuyOrder {
    String cliendID;
    int units;
    int xpos;
    int ypos;
    Location location;
    double originalPrice;

    public BuyOrder(String clientID, int units, int xpos, int ypos, double originalPrice) {
        this.cliendID = clientID;
        this.units = units;
        this.xpos = xpos;
        this.ypos = ypos;
        this.location = new Location(xpos,ypos);
        this.originalPrice = originalPrice;
    }
}
