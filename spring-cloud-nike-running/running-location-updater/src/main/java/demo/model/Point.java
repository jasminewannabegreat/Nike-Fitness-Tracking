package demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC) //becasue we need initialization of point at first with json file
public class Point {
    private Double latitude;
    private Double longitude;
}
