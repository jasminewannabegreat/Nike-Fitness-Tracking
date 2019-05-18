package demo.task;

import demo.model.*;
import demo.service.PositionService;
import demo.support.NavUtils;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class LocationSimulator implements Runnable{
    @Getter
    @Setter
    private long id; //thread id

    @Setter
    private PositionService positionService; //in running location service, when injecting bean, it is singleton, but here we have serverl thread

    //automicboolean means this is only be modified by one thread
    private AtomicBoolean cancel = new AtomicBoolean(); //if cancel == true, means the thread is cancelled so we do some thread cleaing thing
    private double speedInMps; //speed in mile per second

    private boolean shouldMove;

    private boolean exportPositionsToMessaging = true;
    private Integer reportInterval = 500; //milliseconds
    @Getter
    @Setter
    private PositionInfo positionInfo = null;
    @Getter
    @Setter
    private List<Leg> legs;
    private RunnerStatus runnerStatus = RunnerStatus.NONE;
    private String runningId;
    @Setter
    private Point startPoint; //it would help to restore location back to starting location
    private Date executionStartTime;

    private MedicalInfo medicalInfo;

    public LocationSimulator(GpsSimulatorRequest gpsSimulatorRequest){
        this.shouldMove = gpsSimulatorRequest.isMove();
        this.exportPositionsToMessaging = gpsSimulatorRequest.isExportPositionsToMessaging();
        this.setSpeed(gpsSimulatorRequest.getSpeed());
        this.reportInterval = gpsSimulatorRequest.getReportInterval();

        this.runningId = gpsSimulatorRequest.getRunningId();
        this.runnerStatus = gpsSimulatorRequest.getRunnerStatus();
        this.medicalInfo = gpsSimulatorRequest.getMedicalInfo();
    }

    public void setSpeed(double speed){
        this.speedInMps = speed;
    }

    public double getSpeed(){
        return this.speedInMps;
    }


    @Override
    public void run() {
        try{
            executionStartTime = new Date();
            if(cancel.get()){
                destroy();
                return;
            }
            while(!Thread.interrupted()){
                long startTime = new Date().getTime();
                if(positionInfo != null){
                    if(shouldMove){ //if reaching the final point, shouldMove = false, otherwise, false
                        moveRunningLocation();
                        positionInfo.setSpeed(speedInMps);
;                   }
                    else{
                        positionInfo.setSpeed(0.0);
                    }
                    positionInfo.setRunnerStatus(this.runnerStatus);
                    final MedicalInfo medicalInfoToUse; //send medical info of runners to see if he needs some supply: water/food
                    switch (this.runnerStatus){
                        case SUPPLY_NOW:
                        case SUPPLY_SOON:
                        case STOP_NOW:
                            medicalInfoToUse = this.medicalInfo; //only when these three cses, medical infor will be uploaded
                            break;
                            default:
                                medicalInfoToUse = null;
                    }
                    final CurrentPosition currentPosition = new CurrentPosition(
                            this.positionInfo.getRunningId(),
                            new Point(this.positionInfo.getPosition().getLatitude(), this.positionInfo.getPosition().getLongitude()),
                            this.positionInfo.getRunnerStatus(),
                            this.positionInfo.getSpeed(),
                            this.positionInfo.getLeg().getHeading(),
                            medicalInfoToUse
                    );

                    //a service to publish currentPosition to distribution service rest api
                    positionService.processPositionInfo(id, currentPosition, this.exportPositionsToMessaging);

                    sleep(startTime);
                }
            }
        }catch (InterruptedException ie){
            destroy();
            return;
        }
        destroy();
    }
    private void sleep(long startTime) throws InterruptedException{
        long endTime = new Date().getTime();

        long elapsedTime = endTime - startTime;

        long sleepTime = reportInterval - elapsedTime > 0 ? reportInterval - elapsedTime : 0;

        Thread.sleep(sleepTime);
    }

    // Set new position of running location based on current position and running speed
    //see if nex position should be on next leg or current leg
    private void moveRunningLocation(){
        double distance  = speedInMps * reportInterval / 1000.0; //interval is milliseconds, while speed is per sec
        double distanceFromStart = positionInfo.getDistanceFromStart() + distance;
        double excess = 0.0; //judge if already out of current leg

        for (int i = positionInfo.getLeg().getId(); i < legs.size(); i++){
            Leg currentLeg = legs.get(i);
            excess = distanceFromStart > currentLeg.getLength() ? distanceFromStart - currentLeg.getLength() : 0.0;

            if(Double.doubleToRawLongBits(excess) == 0){
                // this means new position falls within current leg
                positionInfo.setDistanceFromStart(distanceFromStart);
                positionInfo.setLeg(currentLeg);
                //Use the new position calcuation method in NavUtils
                Point newPosition = NavUtils.getPosition(currentLeg.getStartPosition(), distanceFromStart, currentLeg.getHeading());
                positionInfo.setPosition(newPosition);
                return;
            }
            distanceFromStart = excess;
        }

        setStartPosition(); //current position will be the start position of next leg
    }

    // Position running location at start of path
    public void setStartPosition(){
        positionInfo = new PositionInfo();
        positionInfo.setRunningId(this.runningId);
        Leg leg = legs.get(0);
        positionInfo.setLeg(leg);
        positionInfo.setPosition(leg.getStartPosition());
        positionInfo.setDistanceFromStart(0.0);
    }

    //only need to send one order to cancel one thread. if one is in the process of canceling, it will not work
    public synchronized void cancel(){
        this.cancel.set(true);
    }

    public void destroy(){
        positionInfo = null;
    }
}

//when 10 threads in executor, take all the threads and it will be list traversal list of all threads, cancel one after one, that's why we need synchronized