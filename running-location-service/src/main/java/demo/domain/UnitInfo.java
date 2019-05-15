package demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Embeddable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Embeddable
//UnitInfo in the location marked as embedded, here has to be embeddable
public class UnitInfo {
    private final String runningId;
    private String bandMake;
    private String customerName;
    private String unitNumber;

    public UnitInfo(){
        this.runningId = "";
    }

    public UnitInfo(String runningId){
        this.runningId = runningId;
    }

    public UnitInfo(String runningId, String bandMake, String customerName, String unitNumber){
        this.runningId = runningId;
        this.bandMake = bandMake;
        this.customerName = customerName;
        this.unitNumber = unitNumber;
    }
    //this is to test how lombok works, without expliciting writing getter and setter function, lombok will generate the getter/setter for you
//    public void test(){
//        UnitInfo unitInfo = new UnitInfo();
//        unitInfo.setBandMake("Apple");
//    }

}
