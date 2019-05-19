package demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;



@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrentPositionDto {
    //Data transfer object
    //front-backend
    //repository - service - rest

    private String runningId;
    private Point location;
    private RunnerStatus runnerStatus = RunnerStatus.NONE;
    private Double speed;
    private Double heading;
}

//前端传来的数据, json, 先存入dto, 然后通过对数据的拆分, 选择性的把其中一部分存入后端
//我们可能要把这个dto拆分成不同的data entity, 用于储存