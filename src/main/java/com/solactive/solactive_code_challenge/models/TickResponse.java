package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.models.dtos.IncommingTick;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;



@RestController
@RequestMapping("/")
public class TickResponse {
    @Value("${solactive.time_horizont}")
    private Long time_horizont;

    HashMap<String, Instrument> dataTicks;

    TickResponse() {
    }

    @PostMapping("/ticks")
    ResponseEntity<String> processTick(@RequestBody IncommingTick incommingTick) {
        // actual timestamp
        Long actualTimestamp = System.currentTimeMillis();
        if (actualTimestamp - incommingTick.getTimestamp() < time_horizont) {
            // incomming tick can be accepted
            if (dataTicks.containsKey(incommingTick.getInstrument())) {
                Instrument instrument = dataTicks.get(incommingTick.getInstrument());

            }

            return new ResponseEntity<>("Tick added.", HttpStatus.CREATED);
        } else {
            // tick cannot be accpted
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
    }

}
