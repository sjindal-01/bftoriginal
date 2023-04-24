package bftsmart.demo.v2vapp;

public class SellOrder {
    String clientID;
    int unitsoffered;
    double unitPrice;
    int xpos;
    int ypos;
    Location location;
    double originalPrice;

    public SellOrder(String clientID, int unitsoffered, double unitPrice, int xpos, int ypos, double originalPrice) {
        this.clientID = clientID;
        this.unitsoffered = unitsoffered;
        this.unitPrice = unitPrice;
        this.xpos = xpos;
        this.ypos = ypos;
        this.location = new Location(xpos, ypos);
        this.originalPrice = originalPrice;
    }
}
