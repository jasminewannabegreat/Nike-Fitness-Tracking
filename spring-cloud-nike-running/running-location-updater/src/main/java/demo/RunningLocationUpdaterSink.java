package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.IOException;

@Slf4j
@EnableBinding(Sink.class)
public class RunningLocationUpdaterSink {
    //this is just the consume end, so this is not a api

    @ServiceActivator(inputChannel = Sink.INPUT) //this is tell rabbitmq, after getting in, use what method to handle message
    public void updateLocation(String input) throws IOException {
        log.info("Location input in updater: "+input);

    }
}
