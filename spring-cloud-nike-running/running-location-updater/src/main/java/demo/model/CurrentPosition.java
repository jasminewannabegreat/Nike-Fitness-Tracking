package demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class CurrentPosition {
    private String runningId;

    private Point location;
    private RunnerStatus runnerStatus = RunnerStatus.NONE;
    private Double speed;
    private Double heading;
    private MedicalInfo medicalInfo;
    private SupplyLocation supplyLocation;
}
