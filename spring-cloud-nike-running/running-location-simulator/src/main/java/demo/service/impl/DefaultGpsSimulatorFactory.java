package demo.service.impl;

import demo.model.GpsSimulatorRequest;
import demo.model.Leg;
import demo.model.Point;
import demo.service.GpsSimulatorFactory;
import demo.service.PositionService;
import demo.support.NavUtils;
import demo.task.LocationSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DefaultGpsSimulatorFactory implements GpsSimulatorFactory {
    @Autowired
    private PositionService positionService;

    private final AtomicLong instanceCounter = new AtomicLong();

    @Override
    public LocationSimulator prepareGpsSimulator(GpsSimulatorRequest gpsSimulatorRequest) {
        final LocationSimulator locationSimulator = new LocationSimulator(gpsSimulatorRequest); //becasue locationSimulator has no bean annotation, so directly new one
        locationSimulator.setPositionService(positionService); //positionService has a setter
        locationSimulator.setId(this.instanceCounter.incrementAndGet()); //auto generate a incrementing id for a thread

        final List<Point> points = NavUtils.decodePolyline(gpsSimulatorRequest.getPolyline());
        locationSimulator.setStartPoint(points.iterator().next());

        return prepareGpsSimulator(locationSimulator, points);
    }

    @Override
    public LocationSimulator prepareGpsSimulator(LocationSimulator locationSimulator, List<Point> points) {
        locationSimulator.setPositionInfo(null);
        final List<Leg> legs = createLegsList(points);
        locationSimulator.setLegs(legs);
        locationSimulator.setStartPosition();
        return locationSimulator;
    }

    private List<Leg> createLegsList(List<Point> points) {
        final List<Leg> legs = new ArrayList<>();
        for (int i = 0; i < (points.size() - 1); i++) {
            Leg leg = new Leg();
            leg.setId(i);
            leg.setStartPosition(points.get(i));
            leg.setEndPosition(points.get(i + 1)); //this is how to convert point to leg
            Double length = NavUtils.getDistance(points.get(i), points.get(i + 1));
            leg.setLength(length);
            Double heading = NavUtils.getBearing(points.get(i), points.get(i + 1));
            leg.setHeading(heading);
            legs.add(leg);
        }

        return legs;
    }
}
