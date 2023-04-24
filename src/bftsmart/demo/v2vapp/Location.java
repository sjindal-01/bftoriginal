package bftsmart.demo.v2vapp;

public class Location {
    int xpos;
    int ypos;

    public Location(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public double distance(Location location1) {
        double dist = Math.sqrt(Math.pow((xpos - location1.xpos),2) + Math.pow((ypos - location1.ypos),2));
        return dist;
    }
}
