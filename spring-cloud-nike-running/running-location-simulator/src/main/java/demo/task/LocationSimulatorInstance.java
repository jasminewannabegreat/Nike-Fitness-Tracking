package demo.task;

import demo.task.LocationSimulator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Future;

@Data
@AllArgsConstructor
public class LocationSimulatorInstance {
    private long instanceId;
    private LocationSimulator locationSimulator;
    private Future<?> locationSimulatorTask; //this is async ,future will handle if this event is done
}
