package demo.model;

import lombok.Data;

//Google Map route is dividend by small routes, it is called Leg
@Data
public class Leg {

    private int id;
    private Point startPosition;
    private Point endPosition;
    private double length;
    private double heading;

    public Leg(){

    }
}
