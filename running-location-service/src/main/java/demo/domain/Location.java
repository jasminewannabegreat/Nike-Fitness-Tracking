package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//Object model stored in relational model, object has to be defined as an entity so that it can be identified in the database
//1-many, many - 1, many to many, entity to entity
//map columns to one object

//Json to object and Object to JSON(JSON library ie. Jackson library)
//Json serialized and deserialized
//JSON <-> Object model <-> ORM -> Relational Model
@JsonInclude(JsonInclude.Include.NON_NULL) //JSON from front end, only when non-null, it will be mapped to relational database
@Entity
@Table(name = "LOCATIONS")
@Data //data annotation is from lombok library,so to generate getter and setter
public class Location {

    public enum GpsStatus{
        EXCELLENT, OK, UNRELIABLE, BAD, NOFIX, UNKNOWN;
    }

    public enum RunnerMovementType{
        STOPPED, IN_MOTION;
    }
    @Id
    @GeneratedValue
    private long id; //mark id as the id in the relational database and use the auto generated value

    @Embedded //will flatten unit info
    @AttributeOverride(name = "bandMake", column = @Column(name = "unit_band_make"))
    //map the unit info's bandMake properties to relational database's column - unit_band_make
    private UnitInfo unitInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fmi", column = @Column(name = "medical_fmi")),
            @AttributeOverride(name = "bfr", column = @Column(name = "medical_bfr"))
    })
    private  MedicalInfo medicalInfo;

    private double latitude;

    private double longitude;

    private String heading;

    private double gpsSpeed;

    private GpsStatus gpsStatus;

    private double odometer;

    private double totalRunningTime;

    private double totalIdleTime;

    private double totalCalorieBurnt;

    private String address;

    private Date timestamp = new Date();

    private String gearProvider;

    private RunnerMovementType runnerMovementType;

    private String serviceType;

    //Jackson library forces to explicit write the non-parameter constructor
    public Location () {this.unitInfo = null;}
    public Location (UnitInfo unitInfo) {this.unitInfo = unitInfo;}

    //telling which constructor should be used to convert Json to location object
    //传过来的json如果有个property叫"runningId"的话,就用传进来的这个runningId新建一个unitInfo的instance
      // when json pass into service, use this constructor
    @JsonCreator
    public Location (@JsonProperty("runningId") String rid ) {this.unitInfo = new UnitInfo(rid);}



    public String getRunningId(){
        ////这里没有implement getter/setter,但是因为使用了lombok, 自动generate getter, setter
        return this.unitInfo == null? null:this.unitInfo.getRunningId();
    }

}

////接RunningUploadRestController:
//// 这个data model 需要干两件事情:
//// 第一, 前端发来的json文件可以translate成data model
//// 第二, 此data model再向数据库存的时候, data model 能transfer成数据库所需要的data entity
//// 所以需要要有支持者两部转化的对应function
////

