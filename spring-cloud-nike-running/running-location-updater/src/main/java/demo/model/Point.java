package demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //becasue we need initialization of point at first with json file
@NoArgsConstructor
public class Point {
    private Double latitude;
    private Double longitude;
}
