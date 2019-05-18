package demo.service.impl;

import demo.model.CurrentPosition;
import demo.service.PositionService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DefaultPositionService implements PositionService {

    //a good habit is before making a RPC call to a service/endpoint/etc.., always print a log
    //this is the usual way to log
    //private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPositionService.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${com.example.running.location.distribution}")  //this is same as the application.yml file
    private String runningLocationDistribution;


    @Override
    public void processPositionInfo(long id, CurrentPosition currentPosition, boolean sendPositionToDistributionService) {
//        LOGGER.info("");
//        log.info("");this is provided by lombok, so it is convenient
//        String runningLocationDistribution = "http://123.45.32.1:9008"; hard-coded cannot be with address

        if(sendPositionToDistributionService){
            log.info(String.format("Thread %d Simulator is calling distribution REST API", Thread.currentThread().getId()));
            this.restTemplate.postForLocation(runningLocationDistribution+"/api/locations", currentPosition);
        }
    }
}
