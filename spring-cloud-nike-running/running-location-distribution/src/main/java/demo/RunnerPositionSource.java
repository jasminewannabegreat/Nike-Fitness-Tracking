package demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@EnableBinding(Source.class)
public class RunnerPositionSource {

    @Autowired
    private MessageChannel output;

    @RequestMapping(path = "/api/locations", method = RequestMethod.POST) //same as the default simulator pathService
    public void locations(@RequestBody String positionInfo){
        //before sending/receiving position to other serivces, always log
        log.info("Receiving currentPositionInfo from Simulator: "+ positionInfo);
        //need to build a message channel, convert the message to the format channel, connect the channel to rabbitmq
        this.output.send(MessageBuilder.withPayload(positionInfo).build()); //first convert
    }
}

//message first simulated through simulator, using the defaultPositionService to send post request to distribution service
//when the request reached to /api/locations, it will bind source with using spring cloud stream, then sending to the queue
