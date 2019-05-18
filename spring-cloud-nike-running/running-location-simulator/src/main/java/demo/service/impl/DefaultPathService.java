package demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.model.SimulatorInitLocation;
import demo.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;


//will read input stream
@Service
public class DefaultPathService implements PathService {

    @Autowired
    private ObjectMapper objectMapper; //jackson library to deserialization

    @Override
    public SimulatorInitLocation loadSimulatorInitLocations() {


        final InputStream is = this.getClass().getResourceAsStream("/init-locations.json");

        try {
            return objectMapper.readValue(is, SimulatorInitLocation.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
