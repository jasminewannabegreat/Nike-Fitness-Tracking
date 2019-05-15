package demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Embeddable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Embeddable
public class MedicalInfo {

    private long bfr; //blood flow restrition
    private long fmi; //first medical index

    //for the Jackson library sake,
    public MedicalInfo(){}

    public MedicalInfo(long bfr, long fmi){
        this.bfr = bfr;
        this.fmi = fmi;
    }
}
