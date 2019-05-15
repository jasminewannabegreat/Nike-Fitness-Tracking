package demo.service.impl;

import demo.domain.Location;
import demo.domain.LocationRepository;
import demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//so that locationservice will become a bean after adding a Service annotation
public class LocationServiceImpl implements LocationService {

    //springdata jpa has already wrapped locationrepository into a bean, so we can directly inject an interface
    //only repository can interact with database

    private LocationRepository locationRepository;

    //using the constrctor is a recommended way to inject a java bean
    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository){

        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> saveRunningLocations(List<Location> runningLocations) {
        return locationRepository.saveAll(runningLocations);
    }

    @Override
    public void deleteAll() {
        locationRepository.deleteAll();
    }

    @Override
    public Page<Location> findByRunnerMovementType(String movementType, Pageable pageable) {
        return locationRepository.findByRunnerMovementType(Location.RunnerMovementType.valueOf(movementType), pageable);
    }

    @Override
    public Page<Location> findByRunningId(String runningId, Pageable pageable) {
        return locationRepository.findByUnitInfoRunningId(runningId,pageable);
    }
}
