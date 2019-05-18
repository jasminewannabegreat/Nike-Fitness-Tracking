package demo.model;

import lombok.Data;

//first handler to handle request coming from api
@Data
public class GpsSimulatorRequest {
    private String runningId;
    private double speed;
    private boolean move = true;
    private boolean exportPositionsToMessaging = true; //if true, agree to share location to other services
    private Integer reportInterval = 500; //interval when reporting to servers
    private RunnerStatus runnerStatus = RunnerStatus.NONE;
    private String polyline; //Google Api use polyline to transfer geo location
    private MedicalInfo medicalInfo;
}
