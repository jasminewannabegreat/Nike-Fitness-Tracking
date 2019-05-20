package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.model.CurrentPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;

@MessageEndpoint
@Slf4j
@EnableBinding(Sink.class)
public class RunningLocationUpdaterSink {
    //this is just the consume end, so this is not a api

    @Autowired
    private SimpMessagingTemplate template;


    @Autowired
    private ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = Sink.INPUT) //this is tell rabbitmq, after getting in, use what method to handle message
    public void updateLocation(String input) throws IOException {
        log.info("Location input in updater: "+input);
        CurrentPosition payload = this.objectMapper.readValue(input, CurrentPosition.class);
        this.template.convertAndSend("/topic/locations", payload);
    }
}
